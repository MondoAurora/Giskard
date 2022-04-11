package me.giskard.sandbox.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.giskard.sandbox.utils.GSBSwingUtils;

public class BookReaderSwing implements BookReaderConsts {
	enum GuiCommand {
		RebuildText// , Test1
	}

	enum TextCommand {
		Save, ReplacePara, ProcImg, Rescan
	}

	private final Map<Integer, Action> HOTKEYS = new HashMap<>();

	private void addHotkey(Integer key, Enum<?> cmd) {
		HOTKEYS.put(key, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if ( cmd instanceof TextTag ) {
						applyTextTag((TextTag) cmd);
					} else if ( cmd instanceof TextCommand ) {
						applyTextCommand((TextCommand) cmd);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
	}

	BookReader book;
	Vector<String> volumeNames = new Vector<>();
	String volumeId;
	String volumeText;

	File dirImg;

	class PageListModel extends AbstractListModel<String> {
		private static final long serialVersionUID = 1L;

		@Override
		public int getSize() {
			return pageCount;
		}

		@Override
		public String getElementAt(int index) {
			return BookReaderUtils.getPageId(index);
		}

		public void update() {
			fireContentsChanged(this, 0, pageCount);
		}
	};

	PageListModel lmPages = new PageListModel();

	int pageCount;
	private String pageId;

	JFrame frm;

	JPanel pnlMain;
	JList<String> lstVolumes;
	JList<String> lstPages;
	BookReaderPageImage pageImage;

	JTabbedPane tpResult;
	JTextArea taVolumeText;
	JEditorPane jepVolume;
//	WebView jfxWebView;

//	ActionListener alCmd = new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			applyGuiCommand(GuiCommand.valueOf(e.getActionCommand()));
//		}
//	};

	ActionListener alCmdDispatcher = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispatchCommand(e.getActionCommand());
		}
	};

	private void dispatchCommand(String cmd) {
		try {
			TextCommand tc = TextCommand.valueOf(cmd);
			if ( null != tc ) {
				applyTextCommand(tc);
				return;
			}

			GuiCommand gc = GuiCommand.valueOf(cmd);
			if ( null != gc ) {
				applyGuiCommand(gc);
				return;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

	};

	public BookReaderSwing(BookReader br) {

		addHotkey(KeyEvent.VK_1, TextTag.h1);
		addHotkey(KeyEvent.VK_2, TextTag.h2);
		addHotkey(KeyEvent.VK_3, TextTag.h3);
		addHotkey(KeyEvent.VK_4, TextTag.h4);

		addHotkey(KeyEvent.VK_O, TextTag.ol);
		addHotkey(KeyEvent.VK_U, TextTag.ul);
		addHotkey(KeyEvent.VK_T, TextTag.table);

		addHotkey(KeyEvent.VK_S, TextCommand.Save);
		addHotkey(KeyEvent.VK_I, TextCommand.ProcImg);
		addHotkey(KeyEvent.VK_R, TextCommand.Rescan);

		book = br;

		book.getVolumeNames(volumeNames);

		lstVolumes = new JList<String>(volumeNames);
		lstVolumes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstVolumes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if ( !e.getValueIsAdjusting() ) {
					selectVolume(lstVolumes.getSelectedIndex());
				}
			}
		});

		pnlMain = new JPanel(new BorderLayout());

		JSplitPane splMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnlMain.add(splMain, BorderLayout.CENTER);

		lstPages = new JList<String>(lmPages);
		ListSelectionModel tsm = lstPages.getSelectionModel();
		tsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tsm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if ( !e.getValueIsAdjusting() ) {
					selectPage();
				}
			}
		});

		JPanel pnlLeft = new JPanel(new BorderLayout());
		pnlLeft.add(GSBSwingUtils.wrapComp(lstVolumes, "Volumes", true), BorderLayout.NORTH);
		pnlLeft.add(GSBSwingUtils.wrapComp(lstPages, "Pages", true), BorderLayout.CENTER);

		JPanel pnlCmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (GuiCommand gc : GuiCommand.values()) {
			GSBSwingUtils.addActionComp(gc, alCmdDispatcher, pnlCmds);
		}
		pnlLeft.add(pnlCmds, BorderLayout.SOUTH);

		splMain.setLeftComponent(pnlLeft);

		JSplitPane splPage = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splMain.setRightComponent(splPage);
		pageImage = new BookReaderPageImage();
		splPage.setLeftComponent(GSBSwingUtils.wrapComp(pageImage, "Page Image", true));

		taVolumeText = new JTextArea();
		taVolumeText.setLineWrap(true);
		taVolumeText.setWrapStyleWord(true);

		InputMap im = taVolumeText.getInputMap();
		ActionMap am = taVolumeText.getActionMap();
		for (Map.Entry<Integer, Action> e : HOTKEYS.entrySet()) {
			String keyStrokeAndKey = "control " + e.getKey();
			KeyStroke keyStroke = KeyStroke.getKeyStroke(e.getKey(), InputEvent.CTRL_DOWN_MASK);
			im.put(keyStroke, keyStrokeAndKey);
			am.put(keyStrokeAndKey, e.getValue());
		}

		jepVolume = new JEditorPane();

//		JFXPanel jfxPanel = new JFXPanel();
//		Platform.runLater(() -> {
//			jfxWebView = new WebView();
//			jfxPanel.setScene(new Scene(jfxWebView));
//		});

		tpResult = new JTabbedPane();
		tpResult.add("Volume Text", new JScrollPane(taVolumeText));
		tpResult.add("Volume Html", new JScrollPane(jepVolume));
//		tpResult.add("Volume Html", jfxPanel);

		JPanel pnlTxts = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (TextCommand tc : TextCommand.values()) {
			GSBSwingUtils.addActionComp(tc, alCmdDispatcher, pnlTxts);
		}

		JPanel pnlR = new JPanel(new BorderLayout());
		pnlR.add(tpResult, BorderLayout.CENTER);
		pnlR.add(pnlTxts, BorderLayout.SOUTH);

		splPage.setRightComponent(pnlR);

		splMain.setResizeWeight(0);
		splMain.setDividerLocation(200);

		splPage.setResizeWeight(0.5);
		splPage.setDividerLocation(0.5);

		lstVolumes.setSelectedIndex(0);
	}

	private void applyGuiCommand(GuiCommand gc) {
		String msg = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;

		System.out.println("\n\nExecuting " + gc);

		try {
			switch ( gc ) {
			case RebuildText:
//				book.getDir(ReadStep.PageContents, selectedVolume, true);
				updateVolumeText();
				break;
			default:
				msg = gc.name();
				break;
			}
		} catch (Exception e) {
			msg = e.toString();
			e.printStackTrace();
			msgType = JOptionPane.ERROR_MESSAGE;
		}

		if ( null != msg ) {
			JOptionPane.showMessageDialog(pnlMain, msg, "BookReader Action", msgType);
		}
	}

	private void updateVolumeText() {
		volumeText = "???";

		try {
			File f = book.getFile(EXT_HTML, ReadStep.PageContents, volumeId);
			if ( !f.isFile() ) {
				f = book.getMergedContent(volumeId);
			}
			volumeText = new String(Files.readAllBytes(f.toPath()));

			URL location = f.toURI().toURL();
			jepVolume.setPage(location);

//			String location = f.toURI().toURL().toExternalForm();
//			Platform.runLater(() -> {
//					jfxWebView.getEngine().load(location);
//			});
		} catch (Throwable e) {
			e.printStackTrace();
		}

		taVolumeText.setText(volumeText);
		taVolumeText.setCaretPosition(0);
	}

	private void updateTitle() {
		if ( null != frm ) {
			frm.setTitle("Book: " + book.bookName + " / " + volumeId);
		}
	}

	private void selectVolume(int i) {
		volumeId = volumeNames.get(i);
		updateTitle();

		updateVolumeText();

		pageCount = book.getPageCount(volumeId);
		lmPages.update();
		if ( 0 == lstPages.getSelectedIndex() ) {
			selectPage();
		} else {
			lstPages.setSelectedIndex(0);
		}
	}

	private void selectPage() {
		pageId = lstPages.getSelectedValue();

		File fImg = book.getPageImage(volumeId, pageId);

		try {
			Icon i = (null == fImg) ? null : new ImageIcon(ImageIO.read(fImg));
			pageImage.setIcon(i);

			String montruId = PREF_PAGE + "-" + pageId.substring(pageId.length() - 4);
			int idx = volumeText.indexOf(montruId);
			if ( -1 != idx ) {
//				taVolumeText.setCaretPosition(volumeText.length() - 1);
				int h = taVolumeText.getHeight();
				taVolumeText.scrollRectToVisible(new Rectangle(0, h - 20, 10, h - 10));
				taVolumeText.setCaretPosition(idx);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayFrame() {
		if ( null == frm ) {
			frm = new JFrame();

			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frm.getContentPane().add(pnlMain);
			frm.pack();
		}

		updateTitle();
		frm.setVisible(true);
	}

	private void applyTextTag(TextTag rt) {
		int ss = taVolumeText.getSelectionStart();
		int se = taVolumeText.getSelectionEnd();
		String st = taVolumeText.getSelectedText();
		String txtNew = rt.apply(st);

		taVolumeText.replaceRange(txtNew, ss, se);

		volumeText = taVolumeText.getText();
	}

	private void applyTextCommand(TextCommand tc) throws Exception {
		volumeText = taVolumeText.getText();

		switch ( tc ) {
		case Save:
			book.update(ReadStep.PageContents, volumeId, volumeText);
			jepVolume.setText(volumeText);
			break;
		case ReplacePara:
			TextTag rt = (TextTag) JOptionPane.showInputDialog(pnlMain, "Select Tag", "Replace Paragraph",
					JOptionPane.QUESTION_MESSAGE, null, TextTag.values(), null);

			if ( null != rt ) {
				applyTextTag(rt);
			}
			break;
		case ProcImg:
			int rc = pageImage.getRctCount();
			for (int i = 0; i < rc; ++i) {
				File fImg = book.getChildImage(volumeId, pageId, null);

				String sel = taVolumeText.getSelectedText();

				Rectangle rct = pageImage.storeSelImg(fImg, i);
				String strRect = MessageFormat.format(FMT_AREA, rct.x, rct.y, rct.width, rct.height);

				String imgName = fImg.getName();
				String strTxt = "<figure id=\"" + imgName + "\">\n" + "  <img src=\""
						+ URLEncoder.encode(fImg.getParentFile().getName(), "UTF-8").replace("+", "%20") + "/" + imgName + "\" "
						+ strRect + " >\n" + "  <figcaption>" + ((null == sel) ? "" : sel) + "</figcaption>\n" + "</figure>\n\n";

				if ( null == sel ) {
					taVolumeText.insert(strTxt, taVolumeText.getCaretPosition());
				} else {
					taVolumeText.replaceRange(strTxt, taVolumeText.getSelectionStart(), taVolumeText.getSelectionEnd());
				}
			}

			volumeText = taVolumeText.getText();

			break;
		case Rescan:
			break;
		}
	}

}
