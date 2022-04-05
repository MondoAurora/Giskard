package me.giskard.sandbox.book;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.sun.javafx.geom.Line2D;

public class BookReaderPageImage extends JLabel {
	ArrayList<Rectangle> rctArr = new ArrayList<>();
	int selIdx;
	Rectangle rctSel;
	Rectangle rctCur = new Rectangle();

	int mode = Cursor.DEFAULT_CURSOR;
	int mx, my, mh, mw;
	Point grabPoint;

	int[][] CURSOR_MODES = { { Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR },
			{ Cursor.W_RESIZE_CURSOR, Cursor.HAND_CURSOR, Cursor.E_RESIZE_CURSOR },
			{ Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR } };

	MouseMotionListener mml = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e) {
			if ( (null != grabPoint) || rctArr.isEmpty() ) {
				return;
			}

			Point pt = e.getPoint();

			rctCur.setBounds(pt.x - 5, pt.y - 5, 10, 10);

			mx = my = mh = mw = 0;

			Rectangle rctHover = null;

			for (Rectangle rct : rctArr) {
				if ( rct.intersects(rctCur) ) {
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

					rctHover = rct;
					break;
				}
			}

			if ( rctHover == null ) {
				if ( rctSel != null ) {
					rctSel = null;
					resetMode();
				}
			} else {
				if ( rctSel != rctHover ) {
					rctSel = rctHover;
				}
				optUpdateMode();
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
				grabPoint.x -= (2 * rctSel.width);
				rctSel.x -= rctSel.width;
				rctSel.width = -rctSel.width;
				mx = (0 == mx) ? mw : 0;
				mw = -mw;

				dirChg = true;
			}

			rctSel.y += (my * dy);
			rctSel.height += (mh * dy);

			if ( 0 > rctSel.height ) {
				grabPoint.y -= (2 * rctSel.height);
				rctSel.y -= rctSel.height;
				rctSel.height = -rctSel.height;
				mh = -mh;
				my = (0 == my) ? 1 : 0;

				dirChg = true;
			}
			
			for ( int i = rctArr.size(); i --> 0; ) {
				Rectangle r = rctArr.get(i);
				
				if ( rctSel != r ) {
					if ( rctSel.intersects(r)) {
						rctArr.remove(i);
						
						if ( e.isShiftDown() ) {
							rctSel.add(r);
							rctSel = null;
							grabPoint = null;
							break;
						}
					}
				}
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
			Point ePt = e.getPoint();

			if ( null == rctSel ) {
				rctSel = new Rectangle(ePt.x, ePt.y, 1, 1);
				rctArr.add(rctSel);
				mh = mw = 1;
				optUpdateMode();
			} else {
				if ( e.isAltDown() ) {
					int idx = rctArr.indexOf(rctSel);
					Rectangle rctNew;

					if ( e.isShiftDown() ) {
						int h = rctSel.height;
						int y = e.getY();
						rctSel.height = y - rctSel.y;
						
						rctNew = new Rectangle(rctSel.x, y + 5, rctSel.width, h - rctSel.height - 5);
					} else {
						int w = rctSel.width;
						int x = e.getX();
						rctSel.width = x - rctSel.x;
						
						rctNew = new Rectangle(x + 5, rctSel.y, w - rctSel.width - 5, rctSel.height);
					}
					
					rctArr.add(idx+1, rctNew);
				}
			}
			grabPoint = new Point(e.getPoint());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if ( e.isControlDown() && (null != rctSel) ) {
				rctArr.remove(rctSel);
				rctArr.add(0, rctSel);
			}
			
			resetMode();
		};

		public void mouseEntered(MouseEvent e) {
		};

		public void mouseExited(MouseEvent e) {
			resetMode();
		};
	};

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private int imgWidth;
	private int imgHeight;
	private boolean hasAlphaChannel;
	private int pixelLength;
	private byte[] pixels;

	public BookReaderPageImage() {
		addMouseListener(ml);
		addMouseMotionListener(mml);
	}

	@Override
	public void setIcon(Icon icon) {
		super.setIcon(icon);

		ImageIcon i = (ImageIcon) icon;

		if ( null != i ) {
			image = (BufferedImage) i.getImage();
			imgWidth = i.getIconWidth();
			imgHeight = i.getIconHeight();
			hasAlphaChannel = (image.getAlphaRaster() != null);

			pixelLength = hasAlphaChannel ? 4 : 3;

			pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

			findImages();

//			rctArr.clear();
		}
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
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);

		Color c = g.getColor();

		int i = 0;

		for (Rectangle rct : rctArr) {
			if ( rct == rctSel ) {
				g.setColor(Color.red);

				int r = 8;

				int x = rct.x - r / 2;
				int y = rct.y - r / 2;
				int xx = x + rct.width;
				int yy = y + rct.height;

				g2.drawOval(x, y, r, r);
				g2.drawOval(x, yy, r, r);
				g2.drawOval(xx, y, r, r);
				g2.drawOval(xx, yy, r, r);
			} else {
				g.setColor(Color.blue);
			}

			g2.draw(rct);

			g2.drawString(Integer.toString(++i), rct.x + rct.width + 5, rct.y + rct.height - 5);
		}

		g.setColor(c);
	}

	public int getRctCount() {
		return rctArr.size();
	}

	public Rectangle storeSelImg(File fTarget, int idx) throws IOException {
		ImageIcon ii = (ImageIcon) getIcon();

		Rectangle r = rctArr.get(idx);
		BufferedImage clip = ((BufferedImage) ii.getImage()).getSubimage(r.x, r.y, r.width, r.height);
		String type = fTarget.getName();
		type = type.substring(type.lastIndexOf(".") + 1);
		ImageIO.write(clip, type, fTarget);

		rctSel = null;
		repaint();

		return r;
	}

	static int PX_OFF = -1;

	public void findImages() {
		rctArr.clear();

		int limBlack = 150;
		int limCount = 10;

		int x;

		Rectangle r = new Rectangle();
		for (int row = 0; row < imgHeight; ++row) {
			x = PX_OFF;

			for (int col = 0; col < imgWidth; ++col) {
				int px = getGray(col, row);

				if ( px < limBlack ) {
					if ( PX_OFF == x ) {
						x = col;
					}
				} else if ( PX_OFF != x ) {
					if ( (col - x) > limCount ) {
						r.setLocation(x, row - 1);
						r.setSize(col - x, 2);

						boolean newRect = true;
						for (Rectangle rct : rctArr) {
							if ( rct.intersects(r) ) {
								rct.add(r);
								newRect = false;
								break;
							}
						}

						if ( newRect ) {
							rctArr.add(new Rectangle(x, row, col - x, 1));
						}
					}

					x = PX_OFF;
				}
			}
		}

		for (int ri = rctArr.size(); ri-- > 0;) {
			Rectangle rr = rctArr.get(ri);

			for (int ii = 0; ii < ri; ++ii) {
				Rectangle r2 = rctArr.get(ii);

				if ( r2.intersects(rr) ) {
					r2.add(rr);
					rctArr.remove(ri);
					rr = null;
					break;
				}
			}
			
			if ( (null != rr) && (rr.height < 30) ) {
				rctArr.remove(ri);
			}
		}

	}

	int getGray(int x, int y) {
		int pos = (y * pixelLength * imgWidth) + (x * pixelLength);

		int b = pixels[pos++] & 0xFF; // blue
		int g = pixels[pos++] & 0xFF; // green
		int r = pixels[pos++] & 0xFF; // red

		int gray = (b + g + r) / 3;

		if ( hasAlphaChannel ) {
			int a = pixels[pos++] & 0xFF; // alpha
			gray = (gray * a) / 255;
		}

		return gray;
	}

}