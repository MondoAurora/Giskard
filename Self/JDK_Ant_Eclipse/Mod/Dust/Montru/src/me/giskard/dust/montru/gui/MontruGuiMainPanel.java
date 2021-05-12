package me.giskard.dust.montru.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.coll.GisCollFactory;

public class MontruGuiMainPanel implements MontruGuiConsts, GisCollConsts, GiskardConsts.MiNDAgent {
	static final String DEFAULT_VIEW = "Default view";
	static final String GRAPH_TITLE = "Knowledge graph";

	static final String[] FILTER_TABS = { "Text", "Type", "Unit" };

	enum FilterInfo {
		text, textValue, textAnyData, textRegexp, types, units
	}

	class KnowledgeGraph extends JPanel {
		private static final long serialVersionUID = 1L;

		Rectangle rct = new Rectangle();
		double dC = 500;
		double dS = 1000;

		GisCollFactory<Integer, Color> factColor = new GisCollFactory<Integer, Color>(false,
				new MiNDCreator<Integer, Color>() {
					@Override
					public Color create(Integer key) {
						return new Color(255, 255, 255, key);
					}
				});

		Font fnt;
		GisCollFactory<Integer, Font> factFont = new GisCollFactory<Integer, Font>(false, new MiNDCreator<Integer, Font>() {
			@Override
			public Font create(Integer key) {
				return fnt.deriveFont((float) key);
			}
		});

		public KnowledgeGraph() {
			setBackground(Color.black);
			setForeground(Color.white);
		}

		@Override
		protected void paintChildren(Graphics g) {
			getBounds(rct);

			Color c = g.getColor();
			fnt = g.getFont();

			int dx = rct.x + rct.width / 2;
			int dy = rct.y + rct.height / 2;
			int lbl = 0;

			g.setColor(Color.white);
			g.drawArc(dx, dy, 20, 20, 0, 360);

			for (Iterator<Double> it = coords.iterator(); it.hasNext() && (lbl < selectedEntities.size()); ++lbl) {
				double x = it.next();
				double y = it.next();
				double z = it.next();

				int px = dx + (int) (dS * x / (dC + z));
				int py = dy + (int) (dS * y / (dC + z));

				int alpha = (int) ((z + 300.0) / 400.0 * 255.0);
				int r = alpha / 25;

				g.setColor(factColor.get(alpha));
				g.setFont(factFont.get(10 + r));

				Map<Object, Object> ed = factEntityData.get(selectedEntities.get(lbl));
				String text = GiskardUtils.toString(ed.get(MTMEMBER_PLAIN_STRING));
				g.drawString(text, px, py);
			}

			g.setColor(c);
			g.setFont(fnt);

			Point2D.Double in = new Point2D.Double();
			AffineTransform rotX = AffineTransform.getRotateInstance(Math.toRadians(10));
			AffineTransform rotY = AffineTransform.getRotateInstance(Math.toRadians(5));

			for (int i = 0; i < coords.size(); i += 3) {
				double x = coords.get(i);
				double y = coords.get(i + 1);
				double z = coords.get(i + 2);

				in.setLocation(x, z);

				rotX.transform(in, in);

				coords.set(i, in.getX());
				in.setLocation(y, in.getY());

				rotY.transform(in, in);
				coords.set(i + 1, in.getX());
				coords.set(i + 2, in.getY());
			}
		}
	}

	private static int[] NOSEL = new int[] {};

	class FilterPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		final int tabIdx;
		final Object type;
		final Object key;

		Set<Object> items = new HashSet<>();
		DefaultListModel<Object> lm = new DefaultListModel<Object>();
		JList<Object> lst;
		
		JButton btnClear;

		public FilterPanel(FilterInfo fi, int tabIdx, Object type, Object key) {
			super(new BorderLayout());
			this.tabIdx = tabIdx;
			this.type = type;
			this.key = key;

			lst = new JList<>(lm);
			lst.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			lst.setCellRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					value = factEntityData.get(value).get(MTMEMBER_ENTITY_STOREID);
					Component ret = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
					return ret;
				}
			});

			lst.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					items.clear();

					for (int sel : lst.getSelectedIndices()) {
						items.add(lm.elementAt(sel));
					}

					updateStatus();
				}
			});

			add(new JScrollPane(lst), BorderLayout.CENTER);

			btnClear = new JButton("Clear");
			btnClear.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clearSelection();
				}
			});
			add(btnClear, BorderLayout.SOUTH);

			filterSettings.put(fi, items);
		}

		void clearSelection() {
			lst.setSelectedIndices(NOSEL);
			items.clear();
			updateStatus();
		}

		void updateStatus() {
			boolean empty = items.isEmpty();
			tpFilter.setForegroundAt(tabIdx, empty ? Color.black : Color.pink);
			btnClear.setEnabled(!empty);

			updateEntityList();
		}

		void optAdd(Object handle, Map<Object, Object> ed) {
			if ( type == ed.get(MTMEMBER_ENTITY_PRIMARYTYPE) ) {
				if ( items.add(handle) ) {
					lm.addElement(handle);
				}
			}
		}

		public boolean allows(Map<Object, Object> ed) {
			if ( items.isEmpty() ) {
				return true;
			} else {
				Object h = ed.get(key);
				if ( h instanceof MiNDToken ) {
					Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_CONTEXT_TOKENS, h);
					h = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_HANDLE);
				}
				if ( items.contains(h) ) {
					return true;
				}
			}
			return false;
		}
	}

	Object[] allColumns = { MTMEMBER_ENTITY_PRIMARYTYPE, MTMEMBER_ENTITY_STOREUNIT, MTMEMBER_ENTITY_STOREID,
			MTMEMBER_PLAIN_STRING };

	Object[] columns = { MTMEMBER_ENTITY_PRIMARYTYPE, MTMEMBER_ENTITY_STOREUNIT, MTMEMBER_PLAIN_STRING };

	EnumMap<FilterInfo, Object> filterSettings = new EnumMap<>(FilterInfo.class);

	GisCollFactory<Object, Map<Object, Object>> factEntityData = new GisCollFactory<Object, Map<Object, Object>>(false,
			new MiNDCreator<Object, Map<Object, Object>>() {
				@Override
				public Map<Object, Object> create(Object key) {
					return new HashMap<>();
				}
			});

	ArrayList<Object> filteredEntities = new ArrayList<>();
	JTable tblFiltEnt;

	ArrayList<Object> selectedEntities = new ArrayList<>();
	ArrayList<Double> coords = new ArrayList<>();

	JComponent pnlMain;

	int entityCount;

	JTabbedPane tpFilter;

	Set<FilterPanel> filterPanels = new HashSet<>();

	JComponent cmpGraph;

	String selView;

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			if ( null == pnlMain ) {
				buildPanel();

				JFrame frm = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_VALUE_RAW);
				frm.getContentPane().add(pnlMain, BorderLayout.CENTER);

				selectView(DEFAULT_VIEW);
				updateEntityList();
			}

			Giskard.access(MiNDAccessCommand.Set, pnlMain, MTMEMBER_ACTION_DIALOG, MTMEMBER_VALUE_RAW);
			break;
		case Process:
			break;
		case Release:
			break;
		}

		return ret;
	}

	void selectView(String sel) {
		selView = sel;
		((TitledBorder) cmpGraph.getBorder()).setTitle(GRAPH_TITLE + " - " + selView);
		cmpGraph.repaint();
	}

	public JComponent boundSegment(String name) {
		return boundSegment(name, null);
	}

	public JComponent boundSegment(String name, JComponent comp) {
		if ( null == comp ) {
			comp = new JLabel(name, JLabel.CENTER);
		}
		comp.setBorder(BorderFactory.createTitledBorder(name));
		return comp;
	}

	public void buildPanel() {

		for (int x = 0; x < 2; ++x) {
			for (int y = 0; y < 2; ++y) {
				for (int z = 0; z < 2; ++z) {
					coords.add(100.0 * x - 50);
					coords.add(100.0 * y - 50);
					coords.add(100.0 * z - 50);
				}
			}
		}

		tpFilter = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		for (int i = 0; i < FILTER_TABS.length; ++i) {
			addFilterTab(i);
		}

		DefaultComboBoxModel<String> cbmViews = new DefaultComboBoxModel<>();
		cbmViews.addElement(DEFAULT_VIEW);
		JComboBox<String> cbViews = new JComboBox<>(cbmViews);

		cbViews.setEditable(true);
		cbViews.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				if ( ie.getStateChange() == ItemEvent.SELECTED ) {
					String selView = (String) cbViews.getSelectedItem();
					if ( -1 == cbmViews.getIndexOf(selView) ) {
						cbmViews.addElement(selView);
					}

					selectView(selView);
				}
			}
		});

		TableModel tmFilteredModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() {
				return columns.length;
			}

			public String getColumnName(int column) {
				return GiskardUtils.toString(columns[column]);
			};

			@Override
			public int getRowCount() {
				return filteredEntities.size();
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object handle = filteredEntities.get(rowIndex);
				Map<Object, Object> ed = factEntityData.peek(handle);

				if ( null == ed ) {
					return "?";
				} else {
					Object val = ed.get(columns[columnIndex]);
					if ( val instanceof Integer ) {
						ed = factEntityData.peek(val);
						if ( null != ed ) {
							val = ed.get(MTMEMBER_ENTITY_STOREID);
						}
					}

					return GiskardUtils.toString(val);
				}
			}
		};
		tblFiltEnt = new JTable(tmFilteredModel);
		tblFiltEnt.setFillsViewportHeight(true);
		ListSelectionModel sm = tblFiltEnt.getSelectionModel();
		sm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedEntities.clear();
				for (int selRow : tblFiltEnt.getSelectedRows()) {
					selectedEntities.add(filteredEntities.get(selRow));
				}
				cmpGraph.repaint();
			}
		});

		JSplitPane splFilter = split(JSplitPane.VERTICAL_SPLIT, 0.2, boundSegment("Filter conditions", tpFilter),
				boundSegment("Entity list", new JScrollPane(tblFiltEnt)));

		JSplitPane splSelector = split(JSplitPane.VERTICAL_SPLIT, 1.0, splFilter, boundSegment("Controls"));

		JPanel pnlLeft = new JPanel(new BorderLayout());
		pnlLeft.add(boundSegment("View selector", cbViews), BorderLayout.NORTH);
		pnlLeft.add(splSelector, BorderLayout.CENTER);

		cmpGraph = new KnowledgeGraph();
		JSplitPane splRight = split(JSplitPane.VERTICAL_SPLIT, 0.8, boundSegment(GRAPH_TITLE, cmpGraph),
				boundSegment("Property sheet"));

		JSplitPane splMain = split(JSplitPane.HORIZONTAL_SPLIT, 0.2, pnlLeft, splRight);

		pnlMain = splMain;

		loadEntities();
	}

	public void loadEntities() {
//		MiNDAgent reader = new MiNDAgent() {
//			@Override
//			public MiNDResultType process(MiNDAgentAction action) throws Exception {
//				return MiNDResultType.AcceptRead;
//			}
//		};
//		Giskard.access(MiNDAccessCommand.Use, reader);

		entityCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);

		for (int idx = 0; idx < entityCount; ++idx) {
			Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);
			Object handle = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_HANDLE);

			Map<Object, Object> ed = factEntityData.get(handle);
			for (Object key : allColumns) {
				Object val = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_GPR01, key);
				if ( null != val ) {
					ed.put(key, val);
				}
			}

			for (FilterPanel fp : filterPanels) {
				fp.optAdd(handle, ed);
			}
		}

		for (FilterPanel fp : filterPanels) {
			fp.clearSelection();
		}
	}

	private void addFilterTab(int i) {
		JComponent comp;
		FilterPanel fp = null;

		switch ( i ) {
		case 0:
			JTextField tf = new JTextField();
			tf.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					setFilterText(tf.getText());
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					setFilterText(tf.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					setFilterText(tf.getText());
				}
			});

			JCheckBox chkAnyType = new JCheckBox("Search in any data");
			chkAnyType.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setFilterInfo(FilterInfo.textAnyData, chkAnyType.isSelected(), true);
				}
			});
			JCheckBox chkRegexp = new JCheckBox("Regular expression");
			chkAnyType.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setFilterInfo(FilterInfo.textRegexp, chkRegexp.isSelected(), true);
				}
			});

			JPanel pnlTxt = new JPanel(new GridLayout(3, 1));

			pnlTxt.add(tf);
			pnlTxt.add(chkRegexp);
			pnlTxt.add(chkAnyType);

			comp = pnlTxt;
			break;
		case 1:
			fp = new FilterPanel(FilterInfo.types, i, MTTYPE_TYPE, MTMEMBER_ENTITY_PRIMARYTYPE);
			comp = fp;
			break;
		case 2:
			fp = new FilterPanel(FilterInfo.units, i, MTTYPE_UNIT, MTMEMBER_ENTITY_STOREUNIT);
			comp = fp;
			break;
		default:
			comp = new JLabel(FILTER_TABS[i], JLabel.CENTER);
			break;
		}

		if ( null != fp ) {
			filterPanels.add(fp);
		}

		tpFilter.addTab(FILTER_TABS[i], comp);
	}

	protected boolean setFilterInfo(FilterInfo info, Object value, boolean update) {
		boolean chg = !GiskardUtils.isEqual(value, filterSettings.put(info, value));

		if ( chg && update ) {
			updateEntityList();
		}

		return chg;
	}

	private void updateEntityList() {
		String txt = GiskardUtils.toString(filterSettings.get(FilterInfo.textValue)).toLowerCase();
		boolean chkTxt = !GiskardUtils.isEmpty(txt);

		filteredEntities.clear();

		for (Object key : factEntityData.keys()) {
			Map<Object, Object> ed = factEntityData.get(key);
			boolean add = true;

			if ( chkTxt ) {
				String str = (String) ed.get(MTMEMBER_ENTITY_STOREID);

				if ( -1 == str.toLowerCase().indexOf(txt) ) {
					add = false;
				}
			}

			for (FilterPanel fp : filterPanels) {
				if ( !fp.allows(ed) ) {
					add = false;
				}
//				if ( !fp.items.isEmpty() && fp.items.contains(ed.get(fp.key))) {
//					continue;
//				}
			}

			if ( add ) {
				filteredEntities.add(key);
			}
		}

		((AbstractTableModel) tblFiltEnt.getModel()).fireTableDataChanged();
		cmpGraph.repaint();
	}

	protected void setFilterText(String text) {
		boolean chg = setFilterInfo(FilterInfo.textValue, text, false);

		boolean valid = !GiskardUtils.isEmpty(text);
		chg |= setFilterInfo(FilterInfo.text, valid, false);
		tpFilter.setForegroundAt(0, valid ? Color.pink : Color.black);

		if ( chg ) {
			updateEntityList();
		}
	}

	public JSplitPane split(int orientation, double weight, JComponent left, JComponent right) {
		JSplitPane splFilter = new JSplitPane(orientation, left, right);
		splFilter.setResizeWeight(weight);
		splFilter.setBorder(null);
		return splFilter;
	}

}
