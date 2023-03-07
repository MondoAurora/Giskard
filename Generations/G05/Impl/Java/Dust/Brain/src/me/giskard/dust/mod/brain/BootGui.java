package me.giskard.dust.mod.brain;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BootGui implements DustBrainConsts, DustBrainBootstrap {
	static class MainPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		// Creating the listener for the panel:
		final MouseAdapter ma = new MouseAdapter() {
			private JLabel selectedLabel = null; // Clicked label.
			private Point selectedLabelLocation = null; // Location of label in panel when it was clicked.
			private Point panelClickPoint = null; // Panel's click point.

			// Selection of label occurs upon pressing on the panel:
			@Override
			public void mousePressed(final MouseEvent e) {

				final Component pressedComp = findComponentAt(e.getX(), e.getY());

				if ( pressedComp != null && pressedComp instanceof JLabel ) {
					selectedLabel = (JLabel) pressedComp;
					selectedLabelLocation = selectedLabel.getLocation();
					panelClickPoint = e.getPoint();

					setComponentZOrder(selectedLabel, 0);
					selectedLabel.repaint();
				} else {
					selectedLabel = null;
					selectedLabelLocation = null;
					panelClickPoint = null;
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if ( null != selectedLabel ) {
					selectedLabel = null;
					selectedLabelLocation = null;
					panelClickPoint = null;

//					invalidate();
//					repaint();
				}
			};

			// Moving of selected label occurs upon dragging in the panel:
			@Override
			public void mouseDragged(final MouseEvent e) {
				if ( selectedLabel != null && selectedLabelLocation != null && panelClickPoint != null ) {

					Point newPanelClickPoint = e.getPoint();
					int newX = selectedLabelLocation.x + (newPanelClickPoint.x - panelClickPoint.x);
					int newY = selectedLabelLocation.y + (newPanelClickPoint.y - panelClickPoint.y);

					selectedLabel.setLocation(newX, newY);
					
					invalidate();
					repaint();

				}
			}
		};

		public MainPanel() {
			super(null);

			addMouseMotionListener(ma);
			addMouseListener(ma);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;
			
			Rectangle rctF = new Rectangle();
			Rectangle rctT = new Rectangle();
			
			for ( Conn c : conns ) {
				JLabel lbTo = labels.get(c.hTo);				
				if ( null != lbTo ) {
					labels.get(c.hFrom).getBounds(rctF);
					lbTo.getBounds(rctT);
					g2d.drawLine((int)rctF.getCenterX(), (int)rctF.getCenterY(), (int)rctT.getCenterX(), (int)rctT.getCenterY());
				}
			}
		}
	}

	static class Conn {
		final MindHandle hType;
		final MindHandle hFrom;
		final MindHandle hTo;

		public Conn(MindHandle hType, MindHandle hFrom, MindHandle hTo) {
			this.hType = hType;
			this.hFrom = hFrom;
			this.hTo = hTo;
		}
	}

	static Map<MindHandle, JLabel> labels = new HashMap<>();
	static Set<Conn> conns = new HashSet<>();

	public static void showGui(DustBrainBase.BootConn bootConn) {
		Map<MindHandle, Color> bordercolors = new HashMap<>();

		bordercolors.put(BootToken.typType.getHandle(), Color.RED);
		bordercolors.put(BootToken.typTag.getHandle(), Color.GREEN);
		bordercolors.put(BootToken.typMember.getHandle(), Color.BLUE);

		MainPanel dragP = new MainPanel();

		Iterable<MindHandle> localKnowledge = bootConn.bootCtx.access(MindAccess.Peek, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, KEY_ITER, KEY_KEYS, null);

		int y = 0;
		int x = 0;

		Font f = dragP.getFont();
		FontRenderContext frc = new FontRenderContext(null, true, false);

		int pw = 1000;
		int ph = 800;
		
		MindHandle hh = BootToken.memKnowledgeHandle.getHandle();

		for (MindHandle mh : localKnowledge) {
			if ( hh == mh ) {
				continue;
			}
			String txt = mh.toString();
			if ( txt.startsWith("BH(")) {
				continue;
			}
			Rectangle rct = f.getStringBounds(txt, frc).getBounds();
			rct.width += 10;
			rct.height += 6;

			final JLabel lbl = new JLabel(txt, JLabel.CENTER);
			lbl.setBounds(rct);
			lbl.setOpaque(true);

			labels.put(mh, lbl);

			int nextx = rct.width + 5;

			if ( pw < x + nextx ) {
				x = 0;
				y += (rct.height + 5);
			}

			lbl.setLocation(x, y);

			x += nextx;

			KnowledgeItem ki = bootConn.bootCtx.access(MindAccess.Peek, BootToken.memContextLocalKnowledge.getHandle(), MindColl.Map, mh, null, null);
			MindHandle th = ki.access(MindAccess.Peek, BootToken.memKnowledgeType.getHandle(), MindColl.One, null, null, null);

			Iterable<BrainHandle> members = ki.access(MindAccess.Peek, null, MindColl.Map, KEY_ITER, null, null);

			for (BrainHandle hMem : members) {
				if ( hh == hMem ) {
					continue;
				}

				Iterable<?> values = ki.access(MindAccess.Peek, hMem, MindColl.Map, KEY_ITER, null, null);
				for (Object val : values) {
					if ( val instanceof MindHandle ) {
						conns.add(new Conn(hMem, mh, (MindHandle) val));
					}
				}
			}

			Color col = bordercolors.getOrDefault(th, Color.GRAY);
			lbl.setBorder(new LineBorder(col, 2)); // Set a border for clarity.

			dragP.add(lbl);
		}
		
		for ( Iterator<Conn> ci = conns.iterator(); ci.hasNext(); ) {
			Conn c = ci.next();
			
			if ( !labels.containsKey(c.hTo) ) {
				ci.remove();
			}
		}

		// Creating and showing the main frame:
		final JFrame frame = new JFrame(BootGui.class.getSimpleName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// The size of the content pane adds some extra room for moving the labels:
		final Dimension paneSize = new Dimension(pw, ph);
		frame.getContentPane().setPreferredSize(paneSize);
		frame.getContentPane().add(dragP);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
