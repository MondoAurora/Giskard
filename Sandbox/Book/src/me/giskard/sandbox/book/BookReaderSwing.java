package me.giskard.sandbox.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import me.giskard.GiskardUtils;
import me.giskard.GiskardUtilsConsts.GisUtilsCommandCenter;
import me.giskard.GiskardUtilsSwing;
import me.giskard.GiskardUtilsSwingCommandCenter;

public class BookReaderSwing implements BookReaderConsts {
	enum GuiCommand {
		RebuildText// , Test1
	}

	enum TextCommand {
		Save, ReplacePara, ProcImg, MapImg,
	}

	BookReader book;
	Vector<String> volumeNames = new Vector<>();
	String volumeId;
	String volumeText;

	int pageCount;
	private String pageId;

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

	JFrame frm;

	JPanel pnlMain;
	JList<String> lstVolumes;
	JList<String> lstPages;
	BookReaderPageImage pageImage;

	JTabbedPane tpResult;
	JTextArea taVolumeText;
	JEditorPane jepVolume;

	GisUtilsCommandCenter cc = new GisUtilsCommandCenter() {
		@Override
		public <RetType> RetType execCmd(Enum<?> cmd, Object... params) throws Exception {
			if ( cmd instanceof TextCommand ) {
				applyTextCommand((TextCommand) cmd);
			} else if ( cmd instanceof GuiCommand ) {
				applyGuiCommand((GuiCommand) cmd);
			} else if ( cmd instanceof TextTag ) {
				applyTextTag((TextTag) cmd);
			}

			return null;
		}
	};

	GiskardUtilsSwingCommandCenter scc = new GiskardUtilsSwingCommandCenter(cc);

	public BookReaderSwing(BookReader br) {

		scc.addHotkey(KeyEvent.VK_1, TextTag.h1);
		scc.addHotkey(KeyEvent.VK_2, TextTag.h2);
		scc.addHotkey(KeyEvent.VK_3, TextTag.h3);
		scc.addHotkey(KeyEvent.VK_4, TextTag.h4);
		scc.addHotkey(KeyEvent.VK_5, TextTag.h5);

		scc.addHotkey(KeyEvent.VK_O, TextTag.ol);
		scc.addHotkey(KeyEvent.VK_U, TextTag.ul);
		scc.addHotkey(KeyEvent.VK_T, TextTag.table);

		scc.addHotkey(KeyEvent.VK_S, TextCommand.Save);
		scc.addHotkey(KeyEvent.VK_I, TextCommand.ProcImg);
		scc.addHotkey(KeyEvent.VK_M, TextCommand.MapImg);

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
		pnlLeft.add(GiskardUtilsSwing.wrapComp(lstVolumes, "Volumes", true), BorderLayout.NORTH);
		pnlLeft.add(GiskardUtilsSwing.wrapComp(lstPages, "Pages", true), BorderLayout.CENTER);

		JPanel pnlCmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (GuiCommand gc : GuiCommand.values()) {
			GiskardUtilsSwing.addActionComp(gc, scc, pnlCmds);
		}
		pnlLeft.add(pnlCmds, BorderLayout.SOUTH);

		splMain.setLeftComponent(pnlLeft);

		JSplitPane splPage = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splMain.setRightComponent(splPage);
		pageImage = new BookReaderPageImage(new ImageEventProcessor() {
			@Override
			public void textScanned(String txt) {
				if ( !GiskardUtils.isEmpty(txt) ) {
					int cp = taVolumeText.getCaretPosition();

					taVolumeText.insert(txt.trim(), cp);
					volumeText = taVolumeText.getText();
				}
			}
		});
		splPage.setLeftComponent(GiskardUtilsSwing.wrapComp(pageImage, "Page Image", true));

		taVolumeText = new JTextArea();
		taVolumeText.setLineWrap(true);
		taVolumeText.setWrapStyleWord(true);

		scc.setHotkeys(taVolumeText, InputEvent.CTRL_DOWN_MASK);

		jepVolume = new JEditorPane();

		tpResult = new JTabbedPane();
		tpResult.add("Volume Text", new JScrollPane(taVolumeText));
		tpResult.add("Volume Html", new JScrollPane(jepVolume));

		JPanel pnlTxts = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (TextCommand tc : TextCommand.values()) {
			GiskardUtilsSwing.addActionComp(tc, scc, pnlTxts);
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
				book.getMergedContent(volumeId);
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
			volumeText = new String(Files.readAllBytes(f.toPath()));

			URL location = f.toURI().toURL();
			jepVolume.setPage(location);

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
				try {
					Rectangle rct = taVolumeText.modelToView(idx);
					if ( null != rct ) {
						int h = taVolumeText.getParent().getHeight();
						taVolumeText.scrollRectToVisible(new Rectangle(0, rct.y + h - 20, 10, 10));
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				taVolumeText.setCaretPosition(idx);
			}
		} catch (IOException e) {
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
		String imgName;
		int rc;
		String strTxt = null;
		String sel = taVolumeText.getSelectedText();

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
			rc = pageImage.getRctCount();
			for (int i = 0; i < rc; ++i) {
				File fImg = book.getChildImage(volumeId, pageId, null);

				Rectangle rct = pageImage.storeSelImg(fImg, i);
				String strRect = MessageFormat.format(FMT_AREA, rct.x, rct.y, rct.width, rct.height);

				imgName = fImg.getName();
				strTxt = "<figure id=\"" + imgName + "\">\n" + "  <img src=\""
						+ BookReaderUtils.toHtmlRef(fImg.getParentFile().getName()) + "/" + BookReaderUtils.toHtmlRef(imgName)
						+ "\" " + strRect + " >\n" + "  <figcaption>" + ((null == sel) ? "" : sel) + "</figcaption>\n"
						+ "</figure>\n\n";
				
				if ( null == sel ) {
					taVolumeText.insert(strTxt, taVolumeText.getCaretPosition());
				} else {
					taVolumeText.replaceRange(strTxt, taVolumeText.getSelectionStart(), taVolumeText.getSelectionEnd());
				}
			}
			break;
		case MapImg:
			File fImgPage = book.getPageImage(volumeId, pageId);
			imgName = PREF_IMG + "-" + pageId + EXT_PNG;
			File dirVol = book.getFile(null, ReadStep.PageContents, volumeId);
			File fPageClone = new File(dirVol, imgName);

			if ( !fPageClone.exists() ) {
				Files.copy(fImgPage.toPath(), fPageClone.toPath());
			}

			strTxt = "\n" + "<div id=\"" + imgName + "\" style=\"position:relative;\">\n" + "  <img src=\""
					+ BookReaderUtils.toHtmlRef(dirVol.getName()) + "/" + BookReaderUtils.toHtmlRef(imgName)
					+ "\" style=\"position:absolute;top:0;left:0;\">";

			rc = pageImage.getRctCount();
			for (int i = 0; i < rc; ++i) {
				Rectangle rct = pageImage.getRct(i);
				String txt = pageImage.scanRct(rct);
				txt = GiskardUtils.isEmpty(txt) ? "???" : txt.trim();
				strTxt += "\n   <div style=\"position:absolute;background:white;top:" + rct.y + "px;left:" + rct.x
						+ "px;height:" + rct.height + "px;width:" + rct.width + "px;border:double;text-align:right;\">" + txt + "</div>";
			}

			strTxt += "\n</div>\n";
			
			if ( null == sel ) {
				taVolumeText.insert(strTxt, taVolumeText.getCaretPosition());
			} else {
				taVolumeText.replaceRange(strTxt, taVolumeText.getSelectionStart(), taVolumeText.getSelectionEnd());
			}

			break;
		}

//		if ( null != strTxt ) {
//			if ( null == sel ) {
//				taVolumeText.insert(strTxt, taVolumeText.getCaretPosition());
//			} else {
//				taVolumeText.replaceRange(strTxt, taVolumeText.getSelectionStart(), taVolumeText.getSelectionEnd());
//			}
//			volumeText = taVolumeText.getText();
//		}
	}
}
