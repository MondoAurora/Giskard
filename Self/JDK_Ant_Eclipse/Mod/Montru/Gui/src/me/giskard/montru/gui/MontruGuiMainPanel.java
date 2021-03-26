package me.giskard.montru.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

	class KnowledgeGraph extends JPanel {
		private static final long serialVersionUID = 1L;

		Rectangle rct = new Rectangle();
		double dC = 500;
		double dS = 1000;
		
		GisCollFactory<Integer, Color> factColor = new GisCollFactory<Integer, Color>(false, new MiNDCreator<Integer, Color>() {
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
			int lbl = 1;
			
			g.setColor(Color.white);
			g.drawArc(dx, dy, 20, 20, 0, 360);
			
			for (Iterator<Double> it = coords.iterator(); it.hasNext() && (lbl < filteredEntities.size()); ++lbl) {
				double x = it.next();
				double y = it.next();
				double z = it.next();

				int px = dx + (int) (dS * x / (dC + z));
				int py = dy + (int) (dS * y / (dC + z));
				
				int alpha = (int) ((z + 300.0) / 400.0 * 255.0); 
				int r = alpha / 25;
				
				g.setColor(factColor.get(alpha));				
				g.setFont(factFont.get(10 + r));
				
				String text = GiskardUtils.toString(factEntityData.get(filteredEntities.get(lbl)).get(MTMEMBER_PLAIN_STRING));
				g.drawString(text, px, py);				
			}
			
			g.setColor(c);
			g.setFont(fnt);

			Point2D.Double in = new Point2D.Double();
			AffineTransform rotX = AffineTransform.getRotateInstance(Math.toRadians(10));
			AffineTransform rotY = AffineTransform.getRotateInstance(Math.toRadians(5));

			for ( int i = 0; i < coords.size(); i += 3) {
				double x = coords.get(i);
				double y = coords.get(i+1);
				double z = coords.get(i+2);
				
				in.setLocation(x, z);
				
				rotX.transform(in, in);
				
				coords.set(i, in.getX());
				in.setLocation(y, in.getY());

				rotY.transform(in, in);
				coords.set(i+1, in.getX());
				coords.set(i+2, in.getY());
			}
		}
	}

	enum FilterInfo {
		text, textValue, textAnyData, textRegexp
	}

	Object[] columns = { MTMEMBER_ENTITY_PRIMARYTYPE, MTMEMBER_ENTITY_STOREUNIT, MTMEMBER_ENTITY_STOREID, MTMEMBER_PLAIN_STRING };

	EnumMap<FilterInfo, Object> filterSettings = new EnumMap<>(FilterInfo.class);

	GisCollFactory<Object, Map<Object, Object>> factEntityData = new GisCollFactory<Object, Map<Object, Object>>(false, new MiNDCreator<Object, Map<Object, Object>>() {
		@Override
		public Map<Object, Object> create(Object key) {
			return new HashMap<>();
		}
	});

	Set<Object> types = new HashSet<>();
	Set<Object> units = new HashSet<>();

	ArrayList<Object> filteredEntities = new ArrayList<>();
	JTable tblFiltEnt;

	ArrayList<Double> coords = new ArrayList<>();

	JComponent pnlMain;

	int entityCount;

	JTabbedPane tpFilter;
	JComponent cmpGraph;

	String selView;

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT;

		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			if ( null == pnlMain ) {
				buildPanel();

				JFrame frm = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
				frm.getContentPane().add(pnlMain, BorderLayout.CENTER);

				selectView(DEFAULT_VIEW);
				updateEntityList();
			}

			Giskard.access(MiNDAccessCommand.Set, pnlMain, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
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
		loadEntities();

		for (int x = 0; x < 2; ++x) {
			for (int y = 0; y < 2; ++y) {
				for (int z = 0; z < 2; ++z) {
					coords.add(100.0 * x - 50);
					coords.add(100.0 * y - 50);
					coords.add(100.0 * z - 50);
				}
			}
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

		tpFilter = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		for (int i = 0; i < FILTER_TABS.length; ++i) {
			addFilterTab(i);
		}

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
	}

	public void loadEntities() {
		MiNDAgent reader = new MiNDAgent() {
			@Override
			public MiNDResultType process(MiNDAgentAction action) throws Exception {
				return MiNDResultType.ACCEPT_READ;
			}
		};
		Giskard.access(MiNDAccessCommand.Use, reader);

		entityCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);

		for (int idx = 0; idx < entityCount; ++idx) {
			Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);
			Object handle = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_TEMP01, MTMEMBER_ENTITY_HANDLE);

			Map<Object, Object> ed = factEntityData.get(handle);
			for (Object key : columns) {
				Object val = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_TEMP01, key);
				if ( null != val ) {
					ed.put(key, val);
				}
			}

			Object pt = ed.get(MTMEMBER_ENTITY_PRIMARYTYPE);
			if ( MTTYPE_TYPE == pt ) {
				types.add(handle);
			} else if ( MTTYPE_UNIT == pt ) {
				types.add(handle);
			}
		}
	}

	private void addFilterTab(int i) {
		JComponent comp;
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
		default:
			comp = new JLabel(FILTER_TABS[i], JLabel.CENTER);
			break;
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

			if ( chkTxt ) {
				String str = (String) ed.get(MTMEMBER_ENTITY_STOREID);

				if ( -1 == str.toLowerCase().indexOf(txt) ) {
					continue;
				}
			}

			filteredEntities.add(key);
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
