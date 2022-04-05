package me.giskard.sandbox.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import me.giskard.sandbox.utils.GSBSwingUtils;

public class BookReaderSwing implements BookReaderConsts {
	enum GuiCommand {
		RebuildText// , Test1
	}

	enum TextCommand {
		Save, ReplacePara, ProcImg, ScanTxt
	}

	BookReader book;
	Vector<String> volumes = new Vector<>();

	String selectedVolume;
	Map<BookData, Object> volumeData = new HashMap<>();

	ArrayList<Map<BookData, Object>> pages = new ArrayList<>();
	File dirImg;

	AbstractTableModel tmPages = new AbstractTableModel() {
		private static final long serialVersionUID = 1L;

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch ( columnIndex ) {
			case 0:
				return "Page " + (rowIndex + 1);
			default:
				return null;
			}
		}

		@Override
		public int getRowCount() {
			return pages.size();
		}

		@Override
		public int getColumnCount() {
			return 1;
		}

		public String getColumnName(int column) {
			return "Page";
		};
	};

	JFrame frm;

	JPanel pnlMain;
	JList<String> lstVolumes;
	JTable tblPages;
//	JTextArea taPageText;
	BookReaderPageImage pageImage;
	JTextArea taVolumeText;
	String strVolume;

	ActionListener alCmd = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			doAction(GuiCommand.valueOf(e.getActionCommand()));
		}
	};

	ActionListener alTxt = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				strVolume = taVolumeText.getText();

				switch ( TextCommand.valueOf(e.getActionCommand()) ) {
				case Save:
					File f = (File) volumeData.get(BookData.VolumeFile);
					if ( f.exists() ) {
						Files.move(f.toPath(),
								new File(f.getParentFile(), f.getName().replace(EXT_HTML, "." + System.currentTimeMillis() + EXT_HTML))
										.toPath());
					}

					Files.write(f.toPath(), strVolume.getBytes());
					break;
				case ReplacePara:
					TextTag rt = (TextTag) JOptionPane.showInputDialog(pnlMain, "Select Tag", "Replace Paragraph",
							JOptionPane.QUESTION_MESSAGE, null, TextTag.values(), null);

					if ( null != rt ) {
						int ss = taVolumeText.getSelectionStart();
						int se = taVolumeText.getSelectionEnd();
						String st = taVolumeText.getSelectedText();
						String txtNew = rt.apply(st);

						taVolumeText.replaceRange(txtNew, ss, se);

						strVolume = taVolumeText.getText();
					}
					break;
				case ProcImg:
					int rc = pageImage.getRctCount();
					for (int i = 0; i < rc; ++i) {
						String imgName = PREF_IMG + "-" + pageId;
						Pattern ptImgName = Pattern.compile(imgName + "-(\\d+).*");

						int imgIdx = 0;
						for (String fn : dirImg.list()) {
							if ( fn.startsWith(imgName) ) {
								Matcher m = ptImgName.matcher(fn);
								if ( m.matches() ) {
									int idx = Integer.parseInt(m.group(1));
									if ( idx > imgIdx ) {
										imgIdx = idx;
									}
								}
							}
						}

						imgName += String.format("-%04d.png", imgIdx + 1);

						String sel = taVolumeText.getSelectedText();

						Rectangle rct = pageImage.storeSelImg(new File(dirImg, imgName), i);
						String strRect = MessageFormat.format(FMT_AREA, rct.x, rct.y, rct.width, rct.height);

						String strTxt = "<figure id=\"" + imgName + "\">\n" + "  <img src=\""
								+ URLEncoder.encode(dirImg.getName(), "UTF-8").replace("+", "%20") + "/" + imgName + "\" " + strRect
								+ " >\n" + "  <figcaption>" + ((null == sel) ? "" : sel) + "</figcaption>\n" + "</figure>\n\n";

						if ( null == sel ) {
							taVolumeText.insert(strTxt, taVolumeText.getCaretPosition());
						} else {
							taVolumeText.replaceRange(strTxt, taVolumeText.getSelectionStart(), taVolumeText.getSelectionEnd());
						}
					}

					strVolume = taVolumeText.getText();

					break;
				case ScanTxt:
					break;
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

	};
	private String pageId;

	public BookReaderSwing(BookReader br) {
		book = br;

		volumeData.put(BookData.VolumePages, pages);

		book.getFileNames(volumes);

		lstVolumes = new JList<>(volumes);
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

		JPanel pnlCmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (GuiCommand gc : GuiCommand.values()) {
			GSBSwingUtils.addActionComp(gc, alCmd, pnlCmds);
		}
//		pnlMain.add(pnlCmds, BorderLayout.SOUTH);

		JSplitPane splMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pnlMain.add(splMain, BorderLayout.CENTER);

		tblPages = new JTable(tmPages);
		ListSelectionModel tsm = tblPages.getSelectionModel();
		tsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tsm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if ( !e.getValueIsAdjusting() ) {
					try {
						selectPage(tblPages.getSelectedRow());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		JPanel pnlLeft = new JPanel(new BorderLayout());
		pnlLeft.add(GSBSwingUtils.wrapComp(lstVolumes, "Volumes", true), BorderLayout.NORTH);
		pnlLeft.add(GSBSwingUtils.wrapComp(tblPages, "Pages", true), BorderLayout.CENTER);
		pnlLeft.add(pnlCmds, BorderLayout.SOUTH);

		splMain.setLeftComponent(pnlLeft);

//		JSplitPane splVolume = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//		splMain.setRightComponent(splVolume);

		JSplitPane splPage = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splMain.setRightComponent(splPage);
		pageImage = new BookReaderPageImage();
		splPage.setLeftComponent(GSBSwingUtils.wrapComp(pageImage, "Page Image", true));
//		taPageText = new JTextArea();
//		taPageText.setEditable(false);
//		splPage.setRightComponent(GSBSwingUtils.wrapComp(taPageText, "Page Text", true));

//		splVolume.setLeftComponent(splPage);

		taVolumeText = new JTextArea();
		taVolumeText.setLineWrap(true);
		taVolumeText.setWrapStyleWord(true);
//		taVolumeText.setEditable(false);
//		splVolume.setRightComponent(GSBSwingUtils.wrapComp(taVolumeText, "Volume Text", true));
		JScrollPane cmp = (JScrollPane) GSBSwingUtils.wrapComp(taVolumeText, "Volume Text", true);
		cmp.getViewport().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JViewport viewport = (JViewport) e.getSource();
				Rectangle viewRect = viewport.getViewRect();

				Point p = viewRect.getLocation();
				int startIndex = taVolumeText.viewToModel(p);

//				System.out.println("Area idx " + startIndex);
			}
		});

		JPanel pnlTxts = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (TextCommand tc : TextCommand.values()) {
			GSBSwingUtils.addActionComp(tc, alTxt, pnlTxts);
		}

		JPanel pnlR = new JPanel(new BorderLayout());
		pnlR.add(cmp, BorderLayout.CENTER);
		pnlR.add(pnlTxts, BorderLayout.SOUTH);

		splPage.setRightComponent(pnlR);

		splMain.setResizeWeight(0);
		splMain.setDividerLocation(200);

//		splVolume.setResizeWeight(0.3);
//		splVolume.setDividerLocation(0.3);
		splPage.setResizeWeight(0.5);
		splPage.setDividerLocation(0.5);

		lstVolumes.setSelectedIndex(0);
	}

	private void doAction(GuiCommand gc) {
		String msg = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;

		System.out.println("\n\nExecuting " + gc);

		try {
			switch ( gc ) {
			case RebuildText:
				book.getDir(ReadStep.PageContents, selectedVolume, true);
				updateVolumeText();
				break;
			/*
			 * case Test1: ImageIcon ii = (ImageIcon) pageImage.getIcon();
			 * 
			 * Rectangle r = pageImage.rctSel; BufferedImage clip = ((BufferedImage)
			 * ii.getImage()).getSubimage(r.x, r.y, r.width, r.height);
			 * 
			 * ImageIO.write(clip, "png", new File("tmp.png")); break;
			 */
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
		strVolume = "???";

		try {
			strVolume = new String(Files.readAllBytes(((File) volumeData.get(BookData.VolumeText)).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		taVolumeText.setText(strVolume);
		taVolumeText.setCaretPosition(0);
	}

	private void updateTitle() {
		if ( null != frm ) {
			frm.setTitle("Book: " + book.bookName + " / " + selectedVolume);
		}
	}

	private void selectVolume(int i) {
		selectedVolume = volumes.get(i);

		try {
			book.getVolumeInfo(i, volumeData);
			updateVolumeText();

			File f = (File) volumeData.get(BookData.VolumeFile);
			dirImg = new File(f.getParent(), f.getName().replace(EXT_HTML, ""));
			if ( !dirImg.exists() ) {
				dirImg.mkdir();
			}

			tmPages.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}

		updateTitle();
		tblPages.setRowSelectionInterval(0, 0);
	}

	private void selectPage(int selectedRow) throws Exception {
		if ( (0 <= selectedRow) && (selectedRow < pages.size()) ) {
			Map<BookData, Object> pd = pages.get(selectedRow);

			Object o;

			o = pd.get(BookData.PageText);
			if ( o instanceof File ) {
				o = new String(Files.readAllBytes(((File) o).toPath()));
				pd.put(BookData.PageText, o);
			}
//			taPageText.setText((String) o);
//			taPageText.setCaretPosition(0);

			o = pd.get(BookData.PageImage);
			if ( o instanceof File ) {
				o = new ImageIcon(ImageIO.read((File) o));
				pd.put(BookData.PageImage, o);
			}
			pageImage.rctSel = null;
			pageImage.setIcon((Icon) o);

			pageImage.findImages();

			pageId = (String) pd.get(BookData.PageId);

			int idx = strVolume.indexOf(PREF_PAGE + "-" + pageId);
			if ( -1 != idx ) {
				taVolumeText.setCaretPosition(idx);
			}
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

}
