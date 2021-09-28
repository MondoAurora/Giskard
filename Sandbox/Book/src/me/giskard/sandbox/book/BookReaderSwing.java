package me.giskard.sandbox.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import me.giskard.sandbox.utils.GSBSwingUtils;

public class BookReaderSwing implements BookReaderConsts {
	enum GuiCommand {
		RebuildText, Test1, Test2
	}

	BookReader book;
	Vector<String> volumes = new Vector<>();

	String selectedVolume;
	Map<BookData, Object> volumeData = new HashMap<>();

	ArrayList<Map<BookData, Object>> pages = new ArrayList<>();

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

	class PageView extends JLabel {
		Map<Rectangle, Color> rects = new HashMap<>();
				
		Rectangle rctSel;
		Rectangle rctCur = new Rectangle();
		
		Rectangle rctImg;

		int mode = Cursor.DEFAULT_CURSOR;
		int mx, my, mh, mw;
		Point grabPoint;

		int[][] CURSOR_MODES = { { Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR },
				{ Cursor.W_RESIZE_CURSOR, Cursor.HAND_CURSOR, Cursor.E_RESIZE_CURSOR },
				{ Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR } };

		MouseMotionListener mml = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if ( null != grabPoint ) {
					return;
				}
				
				Point pt = e.getPoint();

				rctCur.setBounds(pt.x - 5, pt.y - 5, 10, 10);

				mx = my = mh = mw = 0;
				rctSel = null;
				
				for ( Rectangle rct : rects.keySet()) {
					if ( rct.intersects(rctCur) ) {
						rctSel = rct;
						
						if ( rct.contains(rctCur) ) {
							mx = 1;
							my = 1;
						} else {
							if ( rctCur.contains(pt.x, rct.y) ) {
								my = 1;
								mh = -1;
							} else if ( rctCur.contains(pt.x, rct.y + rct.height) ) {
								mh = 1;
							}

							if ( rctCur.contains(rct.x, pt.y) ) {
								mx = 1;
								mw = -1;
							} else if ( rctCur.contains(rct.x + rct.width, pt.y) ) {
								mw = 1;
							}
						}
						optUpdateMode();
						break;
					}
				}

				if ( null == rctSel ) {
					resetMode();
				}
			};

			@Override
			public void mouseDragged(MouseEvent e) {
				if ( null == grabPoint ) {
					return;
				}
				
				Point pt = e.getPoint();
				boolean dirChg = false;

				int dx = pt.x - grabPoint.x;
				int dy = pt.y - grabPoint.y;
				grabPoint.setLocation(pt);

				rctSel.x += (mx * dx);
				rctSel.width += (mw * dx);

				if ( 0 > rctSel.width ) {
					grabPoint.x -= (2*rctSel.width);
					rctSel.x -= rctSel.width;
					rctSel.width = -rctSel.width;
					mx = (0 == mx) ? mw : 0;
					mw = -mw;

					dirChg = true;
				}
				
				rctSel.y += (my * dy);
				rctSel.height += (mh * dy);
								
				if ( 0 > rctSel.height ) {
					grabPoint.y -= (2*rctSel.height);
					rctSel.y -= rctSel.height;
					rctSel.height = -rctSel.height;
					mh = -mh;
					my = (0 == my) ? 1 : 0;

					dirChg = true;
				}
				
				if ( dirChg ) {
					optUpdateMode();
				}

				System.out.println(rctSel);

				repaint();
			}
		};

		MouseListener ml = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				grabPoint = new Point(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				resetMode();
			};

			public void mouseEntered(MouseEvent e) {
			};

			public void mouseExited(MouseEvent e) {
				resetMode();
			};
		};

		private static final long serialVersionUID = 1L;

		public PageView() {
			addMouseListener(ml);
			addMouseMotionListener(mml);
			
			rctImg = new Rectangle(10, 10, 100, 100);
			rects.put(rctImg, Color.red);
			rects.put(new Rectangle(20, 20, 200, 50), Color.green);
		}
		
		void resetMode() {
			grabPoint = null;
			optUpdateMode(Cursor.DEFAULT_CURSOR);
		}
		
		void optUpdateMode() {
			int m = CURSOR_MODES[mh + 1][mw + 1];
			optUpdateMode(m);
		}
		
		void optUpdateMode(int m) {
			if ( mode != m ) {
				mode = m;
				setCursor(Cursor.getPredefinedCursor(mode));
				repaint();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			Color c = g.getColor();
			
			for ( Map.Entry<Rectangle, Color> re : rects.entrySet() ) {
				Rectangle rct = re.getKey();
				
				g.setColor(re.getValue());
				g2.draw(rct);
				
				if ( rct == rctSel ) {
					int r = 8;
					
					int x = rct.x - r/2;
					int y = rct.y - r/2;
					int xx = x + rct.width;
					int yy = y + rct.height;
					
					g2.drawOval(x, y, r, r);
					g2.drawOval(x, yy, r, r);
					g2.drawOval(xx, y, r, r);
					g2.drawOval(xx, yy, r, r);
				}
			}			
			
			g.setColor(c);
		}

	}

	JFrame frm;

	JPanel pnlMain;
	JComboBox<String> cbVolumes;
	JTable tblPages;
	JTextArea taPageText;
	PageView lblPageImage;
	JTextArea taVolumeText;

	ActionListener alCmd = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			doAction(GuiCommand.valueOf(e.getActionCommand()));
		}
	};

	public BookReaderSwing(BookReader br) {
		book = br;

		volumeData.put(BookData.VolumePages, pages);

		book.getFileNames(volumes);

		cbVolumes = new JComboBox<>(volumes);
		cbVolumes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectVolume(cbVolumes.getSelectedIndex());
			}
		});

		pnlMain = new JPanel(new BorderLayout());

		JPanel pnlCmds = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for (GuiCommand gc : GuiCommand.values()) {
			GSBSwingUtils.addActionComp(gc, alCmd, pnlCmds);
		}
		pnlMain.add(pnlCmds, BorderLayout.SOUTH);

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
		pnlLeft.add(GSBSwingUtils.wrapComp(cbVolumes, "Volumes", false), BorderLayout.NORTH);
		pnlLeft.add(GSBSwingUtils.wrapComp(tblPages, "Pages", true), BorderLayout.CENTER);

		splMain.setLeftComponent(pnlLeft);

		JSplitPane splVolume = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splMain.setRightComponent(splVolume);

		JSplitPane splPage = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		lblPageImage = new PageView();
		splPage.setLeftComponent(GSBSwingUtils.wrapComp(lblPageImage, "Page Image", true));
		taPageText = new JTextArea();
		taPageText.setEditable(false);
		splPage.setRightComponent(GSBSwingUtils.wrapComp(taPageText, "Page Text", true));

		splVolume.setLeftComponent(splPage);

		taVolumeText = new JTextArea();
		taVolumeText.setEditable(false);
		splVolume.setRightComponent(GSBSwingUtils.wrapComp(taVolumeText, "Volume Text", true));

		splMain.setResizeWeight(0);
		splMain.setDividerLocation(100);

		splVolume.setResizeWeight(0.3);
		splVolume.setDividerLocation(0.3);
		splPage.setResizeWeight(0.5);
		splPage.setDividerLocation(0.5);

		selectVolume(0);
	}

	private void doAction(GuiCommand gc) {
		String msg = null;
		int msgType = JOptionPane.INFORMATION_MESSAGE;

		System.out.println("\n\nExecuting " + gc);

		try {
			switch ( gc ) {
			case RebuildText:
				book.getDir(ReadStep.MergePageText, selectedVolume, true);
				updateVolumeText();
				break;
			case Test1:
				ImageIcon ii = (ImageIcon) lblPageImage.getIcon();
				
				
				Rectangle r = lblPageImage.rctImg;
				BufferedImage clip = ((BufferedImage) ii.getImage()).getSubimage(r.x, r.y, r.width, r.height);
				
				ImageIO.write(clip, "png", new File("tmp.png"));
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
		String content = "???";

		try {
			content = new String(Files.readAllBytes(((File) volumeData.get(BookData.VolumeText)).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		taVolumeText.setText(content);
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
			taPageText.setText((String) o);
			taPageText.setCaretPosition(0);

			o = pd.get(BookData.PageImage);
			if ( o instanceof File ) {
				o = new ImageIcon(ImageIO.read((File) o));
				pd.put(BookData.PageImage, o);
			}
			lblPageImage.setIcon((Icon) o);

//			System.out.println("Page " + selectedRow + " selected: " + pd);
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
