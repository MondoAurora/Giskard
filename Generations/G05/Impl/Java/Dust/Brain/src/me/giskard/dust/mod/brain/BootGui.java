package me.giskard.dust.mod.brain;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import me.giskard.mind.GiskardMind;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BootGui extends JFrame implements DustBrainConsts, DustBrainBootstrap {
	private static final long serialVersionUID = 1L;

	private static final Object CP_HANDLE = new Object();

	enum Direction {
		From, To, Both
	}

	enum GuiCmd {
		Reset(false, "Reset content"), Layout(false, "Layout visible items"), Filter(true, "Filter graph"), Conn(true, "Display only selected connections"),

		;

		public final boolean toggle;
		public final String tooltip;

		private GuiCmd(boolean toggle, String tooltip) {
			this.toggle = toggle;
			this.tooltip = tooltip;
		}
	}

	class GraphPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		final MouseAdapter ma = new MouseAdapter() {
			private Point ptMouseDown = null;
			private Set<Direction> sd = new HashSet<>();
			Map<JComponent, Point> lblLoc = new HashMap<>();

			@Override
			public void mousePressed(final MouseEvent e) {

				final Component cpClick = findComponentAt(e.getX(), e.getY());

				if ( cpClick != null && cpClick instanceof JLabel ) {
					MindHandle h = getHandle(cpClick);
					if ( h != hFocus ) {
						setFocus(h);
					}
					lblLoc.put(lblFocused, new Point(lblFocused.getLocation()));
					for (JLabel lb : lbSel) {
						lblLoc.put(lb, new Point(lb.getLocation()));
					}
					ptMouseDown = new Point(e.getPoint());

					setComponentZOrder(lblFocused, 0);
					lblFocused.repaint();

				} else {
					if ( null != hFocus ) {
						setFocus(null);
					}
					lblLoc.clear();
					ptMouseDown = null;
				}
			}

			public void mouseReleased(MouseEvent e) {
				if ( null != ptMouseDown ) {
					lblLoc.clear();
					ptMouseDown = null;
				}
			};

			Rectangle moveComp(JComponent comp, Point ptMouse, Rectangle bounds) {
				Point pt = lblLoc.get(comp);

				if ( null != pt ) {
					int newX = pt.x + (ptMouse.x - ptMouseDown.x);
					int newY = pt.y + (ptMouse.y - ptMouseDown.y);

					comp.setLocation(newX, newY);

					if ( null == bounds ) {
						bounds = new Rectangle(comp.getBounds());
					} else {
						bounds.add(comp.getBounds());
					}
				}

				return bounds;
			}

			@Override
			public void mouseDragged(final MouseEvent e) {
				if ( null != ptMouseDown ) {

					Point ptMouse = e.getPoint();
					Rectangle rctBounds = moveComp(lblFocused, ptMouse, null);

					if ( !lbSel.isEmpty() ) {
						sd.clear();

						if ( e.isShiftDown() ) {
							sd.add(Direction.From);
						}
						if ( e.isControlDown() ) {
							sd.add(Direction.To);
						}

						if ( !sd.isEmpty() ) {
							for (JLabel lb : lbSel) {
								MindHandle h = getHandle(lb);
								if ( sd.contains(hSel.get(h)) ) {
									moveComp(lb, ptMouse, rctBounds);
								}
							}
						}
					}

//					GiskardMind.dump("Bounds", rctBounds);

					int lw = rctBounds.x + rctBounds.width;
					int lh = rctBounds.y + rctBounds.height;

					if ( (lw > getWidth()) || (lh > getHeight()) ) {
						setPreferredSize(new Dimension(lw, lh));
						scpGraph.revalidate();
//						JScrollBar sb = scpGraph.getHorizontalScrollBar();
						scpGraph.repaint();
//						scpGraph.getVerticalScrollBar().repaint();
					}

					invalidate();
					repaint();
				}
			}
		};

		Border def_border;
		Map<MindHandle, Border> borders = new HashMap<>();

		Color cDef;
		Color cFocus;
		Color cSelFrom;
		Color cSelTo;

		Map<MindHandle, JLabel> labels = new HashMap<>();

		JLabel lblFocused = null;
		Set<JLabel> lbSel = new HashSet<>();

		Set<MindHandle> dispConnTypes = new HashSet<>();
		
		MindHandle hh = GiskardUtils.getHandle(BootToken.memKnowledgeHandle);

		public GraphPanel() {
			super(null);

			addMouseMotionListener(ma);
			addMouseListener(ma);

			def_border = new LineBorder(Color.GRAY, 2);
			borders.put(GiskardUtils.getHandle(BootToken.typType), new LineBorder(Color.RED, 2));
			borders.put(GiskardUtils.getHandle(BootToken.typTag), new LineBorder(Color.GREEN, 2));
			borders.put(GiskardUtils.getHandle(BootToken.typMember), new LineBorder(Color.BLUE, 2));

			cDef = new JLabel().getBackground();
			cFocus = Color.ORANGE;
			cSelFrom = Color.PINK;
			cSelTo = Color.GREEN;

			loadHandles();
		}

		public void loadHandles(KnowledgeItem unit, Iterable<MindHandle> localKnowledge ) {
			Font f = getFont();
			FontRenderContext frc = new FontRenderContext(null, true, false);

			for (MindHandle mh : localKnowledge) {
				if ( (hh == mh) || labels.containsKey(mh) ) {
					continue;
				}
				
				String txt = mh.toString();
				if ( txt.startsWith("BH(") ) {
					continue;
				}
				
				if ( !names.add(txt)) {
					GiskardMind.dump("Duplicate name", txt);
				}
				
				Rectangle rct = f.getStringBounds(txt, frc).getBounds();
				rct.width += 10;
				rct.height += 6;

				final JLabel lbl = new JLabel(txt, JLabel.CENTER);
				lbl.putClientProperty(CP_HANDLE, mh);
				lbl.setBounds(rct);
				lbl.setOpaque(true);

				labels.put(mh, lbl);

				KnowledgeItem ki = unit.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, mh, null, null);
				MindHandle th = ki.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeType), MindColl.One, null, null, null);
				lbl.setBorder(borders.getOrDefault(th, def_border)); // Set a border for clarity.

				Iterable<DustBrainHandle> members = ki.access(MindAccess.Peek, null, MindColl.Map, KEY_ITER, null, null);
				for (DustBrainHandle hMem : members) {
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

				add(lbl);
			}
		}
		
		Set<String> names = new TreeSet<>();

		public void loadHandles() {
			Iterable<MindHandle> localKnowledge = brain.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, KEY_ITER, KEY_KEYS, null);

			names.clear();
			
			loadHandles(brain, localKnowledge);
			
			localKnowledge = brain.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memBrainUnits), MindColl.Map, KEY_ITER, null, null);

			for (MindHandle mh : localKnowledge) {
				KnowledgeItem item = DustBrainBootUtils.resolveBrainHandle(brain, mh);
				
				Iterable<MindHandle> li = item.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, KEY_ITER, KEY_KEYS, null);
				
				if ( null != li ) {
					loadHandles(item, li);
				}
			}

			for (Iterator<Conn> ci = conns.iterator(); ci.hasNext();) {
				Conn c = ci.next();

				if ( !labels.containsKey(c.hTo) ) {
					ci.remove();
				}
			}
		}

		public void updateSelection() {
			lblFocused = (null == hFocus) ? null : labels.get(hFocus);
			lbSel.clear();
			for (MindHandle h : hSel.keySet()) {
				lbSel.add(labels.get(h));
			}

			for (Component c : getComponents()) {
				Color bc = cDef;

				if ( c == lblFocused ) {
					bc = cFocus;
				} else if ( lbSel.contains(c) ) {
					MindHandle h = getHandle(c);
					bc = hSel.get(h) == Direction.From ? cSelFrom : cSelTo;
				}

				c.setBackground(bc);
			}
		}

		public void updateFilter() {
			if ( cmds.get(GuiCmd.Filter).isSelected() ) {
				for (Map.Entry<MindHandle, JLabel> lb : pnlGraph.labels.entrySet()) {
					lb.getValue().setVisible(lmItems.contains(lb.getKey()));
				}
			} else {
				for (JLabel lb : pnlGraph.labels.values()) {
					lb.setVisible(true);
				}
			}
		}

		public void layoutLabels() {
			int y = 0;
			int x = 0;

			int pw = scpGraph.getWidth() - 20;

			Rectangle rct = new Rectangle();

			for (Component comp : getComponents()) {
				if ( !comp.isVisible() ) {
					continue;
				}

				comp.getBounds(rct);

				int nextx = rct.width + 5;

				if ( pw < x + nextx ) {
					x = 0;
					y += (rct.height + 5);
				}

				comp.setLocation(x, y);

				x += nextx;
			}

			setPreferredSize(new Dimension(pw, y + 40));

			rct.setBounds(0, 0, 10, 10);
			scpGraph.scrollRectToVisible(rct);

			scpGraph.revalidate();
			scpGraph.repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			Rectangle rctF = new Rectangle();
			Rectangle rctT = new Rectangle();

			boolean filter = cmds.get(GuiCmd.Conn).isSelected();

			for (Conn c : conns) {
				if ( filter && !dispConnTypes.contains(c.hType) ) {
					continue;
				}
				JLabel lbTo = labels.get(c.hTo);
				if ( !lbTo.isVisible() ) {
					continue;
				}
				if ( null != lbTo ) {
					JLabel lbFrom = labels.get(c.hFrom);
					if ( !lbFrom.isVisible() ) {
						continue;
					}
					lbFrom.getBounds(rctF);
					lbTo.getBounds(rctT);
					g2d.drawLine((int) rctF.getCenterX(), (int) rctF.getCenterY(), (int) rctT.getCenterX(), (int) rctT.getCenterY());
				}
			}
		}
	}

	class PropertyPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		class MultiValue {
			MindColl coll;

			Number count;
			KnowledgeItem item;
			DustBrainHandle hMem;

			MultiValue(KnowledgeItem item, DustBrainHandle hMem, BootToken bt) {
				this.item = item;
				this.hMem = hMem;
				switch ( bt ) {
				case tagCollArr:
					coll = MindColl.Arr;
					break;
				case tagCollSet:
					coll = MindColl.Set;
					break;
				case tagCollMap:
					coll = MindColl.Map;
					break;
				default:
					coll = MindColl.One;
					break;
				}
				count = item.access(MindAccess.Peek, hMem, coll, KEY_SIZE, null, null);
			}

			@Override
			public String toString() {
				return coll.wrapSize(count);
			}

			public void loadModel(DefaultTableModel tmMultiVal) {
				Iterable it = item.access(MindAccess.Peek, hMem, coll, KEY_ITER, KEY_KEYS, null);

				tmMultiVal.setRowCount(0);
				int idx = 0;
				Object[] val = new Object[2];
				
				for ( Object o : it ) {
					switch ( coll ) {
					case Set:
					case Arr:
						val[0] = idx;
						val[1] = o;
						break;
					case Map:
						val[0] = o;
						val[1] = item.access(MindAccess.Peek, hMem, coll, o, null, null);
						break;
					default:
						break;
					}
					tmMultiVal.addRow(val);
					++idx;
				}
				
				tmMultiVal.fireTableDataChanged();
			}
		}

		MindHandle hItem;
		KnowledgeItem ki;

		DefaultTableModel tmMembers = new DefaultTableModel(new String[] { "Type", "Member", "Value" }, 0);
		JTable tbMembers;

		DefaultTableModel tmMultiVal = new DefaultTableModel(new String[] { "Key", "Value" }, 0);
		JTable tbMultiVal;
		JScrollPane scMultiVal;
		MultiValue selMultiVal;
		
		JPanel pnlValues;
		JLabel lblSingle;

		JLabel lblHeader;

		public PropertyPanel(MindHandle hItem) {
			super(new BorderLayout());

			lblHeader = new JLabel("", JLabel.CENTER);
			add(lblHeader, BorderLayout.NORTH);

			tbMembers = new JTable(tmMembers);
			tbMembers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbMembers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if ( !e.getValueIsAdjusting() ) {
						updateValPanel();
					}
				}
			});
			
			lblSingle = new JLabel("Not multi member selected", JLabel.CENTER);
			pnlValues = new JPanel(new BorderLayout());
			pnlValues.add(lblSingle, BorderLayout.CENTER);
			tbMultiVal = new JTable(tmMultiVal);
			scMultiVal = new JScrollPane(tbMultiVal);

			JSplitPane splMain = new JSplitPane();
			splMain.setLeftComponent(new JScrollPane(tbMembers));
			splMain.setRightComponent(pnlValues);
			splMain.setResizeWeight(0.5);
			splMain.setDividerLocation(0.5);

			add(splMain, BorderLayout.CENTER);

			sethItem(hItem);
		}

		public void updateValPanel() {
			int selIdx = tbMembers.getSelectionModel().getMinSelectionIndex();
			JComponent cmpRight = lblSingle;
			
			if ( -1 != selIdx ) {
				Object val = tbMembers.getValueAt(selIdx, 2);
				
				if ( val instanceof MultiValue ) {
					cmpRight = scMultiVal;
					
					if ( val != selMultiVal ) {
						selMultiVal = (MultiValue) val;
						
						selMultiVal.loadModel(tmMultiVal);
					}
				}
			}
			
			pnlValues.removeAll();
			pnlValues.add(cmpRight, BorderLayout.CENTER);
			pnlValues.invalidate();
			pnlValues.repaint();
		}

		public void sethItem(MindHandle hItem) {
			this.hItem = hItem;

			lblHeader.setText("---");
			ki = null;
			tmMembers.setRowCount(0);

			if ( null != hItem ) {
				Object[] val = new Object[3];

				lblHeader.setText(hItem.toString());
				ki = DustBrainBootUtils.resolveBrainHandle(brain, hItem);

				Iterable<DustBrainHandle> members = ki.access(MindAccess.Peek, null, MindColl.Map, KEY_ITER, null, null);
				for (DustBrainHandle hMem : members) {
					KnowledgeItem mi = DustBrainBootUtils.resolveBrainHandle(brain, hMem);
					
					if ( null == mi ) {
						continue;
					}
					val[0] = mi.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeOwner), MindColl.One, null, null, null);
					val[1] = hMem;

					DustBrainHandle coll = mi.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeTags), MindColl.Map, GiskardUtils.getHandle(BootToken.tagColl), GiskardUtils.getHandle(BootToken.tagCollOne), null);

					BootToken bt =  GiskardUtils.getEnum(coll, BootToken.tagCollOne);
					
					val[2] = ( bt == BootToken.tagCollOne ) 
							? ki.access(MindAccess.Peek, hMem, MindColl.One, null, null, null)
									: new MultiValue(ki, hMem, bt);

					tmMembers.addRow(val);
				}

				tmMembers.fireTableDataChanged();
				
				tbMembers.getSelectionModel().setLeadSelectionIndex(0);
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

	KnowledgeItem brain;

	MindHandle hFocus;
	Map<MindHandle, Direction> hSel = new HashMap<>();

	Set<Conn> conns = new HashSet<>();

	JScrollPane scpGraph;
	GraphPanel pnlGraph;

	PropertyPanel pnlProp;

	JTextField tfFilter;
	DefaultListModel<MindHandle> lmItems;
	JList<MindHandle> lstItems;

	JList lstConnTypes;

	Map<GuiCmd, AbstractButton> cmds = new HashMap<>();

	public BootGui(KnowledgeItem brain) {
		super("Montru PoC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.brain = brain;

		pnlGraph = new GraphPanel();

		pnlProp = new PropertyPanel(null);

		tfFilter = new JTextField();
		tfFilter.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				filterItems();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterItems();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filterItems();
			}
		});

		lmItems = new DefaultListModel<>();
		lmItems.addElement(hFocus);

		for (MindHandle h : pnlGraph.labels.keySet()) {
			lmItems.addElement(h);
		}
		lstItems = new JList<MindHandle>(lmItems);
		lstItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstItems.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				MindHandle h = lstItems.getSelectedValue();
				if ( (null != h) && (h != hFocus) ) {
					setFocus(h);
				}
			}
		});

		Set<MindHandle> connTypes = new HashSet<>();
		for (Conn c : conns) {
			connTypes.add(c.hType);
		}
		lstConnTypes = new JList(connTypes.toArray());
		lstConnTypes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstConnTypes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				pnlGraph.dispConnTypes.clear();
				for (Object c : lstConnTypes.getSelectedValuesList()) {
					pnlGraph.dispConnTypes.add((MindHandle) c);
				}

				pnlGraph.invalidate();
				pnlGraph.repaint();
			}
		});

	}

	protected void filterItems() {
		lmItems.removeAllElements();
		String filter = tfFilter.getText().toUpperCase();
		if ( GiskardUtils.isEmpty(filter) ) {
			filter = null;
		}

		for (MindHandle h : pnlGraph.labels.keySet()) {
			if ( (null == filter) || (-1 != h.toString().toUpperCase().indexOf(filter)) ) {
				lmItems.addElement(h);
			}
		}

		if ( null != hFocus ) {
			lstItems.setSelectedValue(hFocus, true);
		}

		if ( cmds.get(GuiCmd.Filter).isSelected() ) {
			pnlGraph.updateFilter();
		}
	}

	void init() {
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.add(tfFilter, BorderLayout.NORTH);
		pnlTop.add(new JScrollPane(lstItems), BorderLayout.CENTER);

		JPanel pnlBottom = new JPanel(new BorderLayout());
		pnlBottom.add(new JScrollPane(lstConnTypes), BorderLayout.CENTER);

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiCmd cmd = GuiCmd.valueOf(e.getActionCommand());
//				AbstractButton bt = cmds.get(cmd);

				switch ( cmd ) {
				case Conn:
					break;
				case Filter:
					pnlGraph.updateFilter();
					break;
				case Layout:
					pnlGraph.layoutLabels();
					break;
				case Reset:
					cmds.get(GuiCmd.Filter).setSelected(false);
					cmds.get(GuiCmd.Conn).setSelected(false);
					for (JLabel lb : pnlGraph.labels.values()) {
						lb.setVisible(true);
					}
					pnlGraph.layoutLabels();

					break;
				}

				pnlGraph.invalidate();
				pnlGraph.repaint();
			}
		};
		JToolBar tb = new JToolBar();
		for (GuiCmd gc : GuiCmd.values()) {
			AbstractButton bt = gc.toggle ? new JToggleButton() : new JButton();
			cmds.put(gc, bt);
			bt.setText(gc.name());
			bt.setToolTipText(gc.tooltip);
			bt.setActionCommand(gc.name());
			bt.addActionListener(al);
			bt.setFocusable(false);
			tb.add(bt);
		}
		pnlBottom.add(tb, BorderLayout.SOUTH);

		JSplitPane splLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splLeft.setTopComponent(pnlTop);
		splLeft.setBottomComponent(pnlBottom);
		splLeft.setResizeWeight(0.7);
		splLeft.setDividerLocation(0.7);

		JSplitPane splRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splRight.setTopComponent(scpGraph = new JScrollPane(pnlGraph));
		splRight.setBottomComponent(pnlProp);
		splRight.setResizeWeight(0.7);
		splRight.setDividerLocation(0.7);

		JSplitPane splMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(splLeft, BorderLayout.CENTER);
		splMain.setLeftComponent(p1);
		p1 = new JPanel(new BorderLayout());
		p1.add(splRight, BorderLayout.CENTER);
		splMain.setRightComponent(p1);
		splMain.setResizeWeight(0.3);
		splMain.setDividerLocation(0.3);

		Container cp = getContentPane();

		cp.add(splMain, BorderLayout.CENTER);

		Dimension paneSize = new Dimension(1000, 800);
		pnlGraph.setPreferredSize(paneSize);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		pnlGraph.layoutLabels();
	}

	void setFocus(MindHandle h) {
		if ( hFocus != h ) {
			hFocus = h;
			hSel.clear();

			for (Conn c : conns) {
				if ( hFocus == c.hFrom ) {
					hSel.put(c.hTo, Direction.To);
				} else if ( hFocus == c.hTo ) {
					hSel.put(c.hFrom, Direction.From);
				}
			}

			pnlGraph.updateSelection();
			pnlProp.sethItem(h);

			if ( lmItems.contains(h) ) {
				lstItems.setSelectedValue(h, true);
			} else {
				lstItems.clearSelection();
			}
		}
	}

	public static MindHandle getHandle(Component c) {
		return (MindHandle) ((JComponent) c).getClientProperty(CP_HANDLE);
	}

	public static void showGui(KnowledgeItem brain) {

		BootGui bootGui = new BootGui(brain);

		bootGui.init();
	}
}
