package me.giskard.montru.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;

public class MontruGuiMainPanel implements MontruGuiConsts, GiskardConsts.MiNDAgent {

	JComponent pnlMain;
	
	int filterCount;

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
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

	public void buildPanel() {

		StringBuilder sbAll = new StringBuilder();
		MiNDAgent reader = new MiNDAgent() {
			@Override
			public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
				return MiNDResultType.ACCEPT_READ;
			}
		};
		Giskard.access(MiNDAccessCommand.Use, reader);
		
		filterCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, KEY_SIZE);
		
		for ( int idx = 0; idx < filterCount; ++idx ) {
			Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_THIS, MTMEMBER_LINK_ARR, idx);
			Object type = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_TEMP01, MTMEMBER_ENTITY_PRIMARYTYPE);
			Object id = Giskard.access(MiNDAccessCommand.Get, "", MTMEMBER_ACTION_TEMP01, MTMEMBER_ENTITY_STOREID);
			GiskardUtils.sbAppend(sbAll, "\n", false, GiskardUtils.toString(type) + ":" + id);
		}
		
		JTextArea ta = new JTextArea();
		ta.setText(sbAll.toString());
		
		JSplitPane splFilter = split(JSplitPane.VERTICAL_SPLIT, 0.2, new JLabel("Filter conditions", JLabel.CENTER),
				new JLabel("Entity list", JLabel.CENTER));

		JSplitPane splSelector = split(JSplitPane.VERTICAL_SPLIT, 1.0, splFilter, new JLabel("Controls", JLabel.CENTER));

		JSplitPane splLeft = split(JSplitPane.VERTICAL_SPLIT, 0.0, new JLabel("View selector", JLabel.CENTER), splSelector);

		JSplitPane splRight = split(JSplitPane.VERTICAL_SPLIT, 0.8, 
				new JScrollPane(ta),
//				new JLabel("Main knowledge graph", JLabel.CENTER),
				new JLabel("Selected entity property sheet", JLabel.CENTER));

		JSplitPane splMain = split(JSplitPane.HORIZONTAL_SPLIT, 0.2, splLeft, splRight);

		pnlMain = splMain;
	}

	public JSplitPane split(int orientation, double weight, JComponent left, JComponent right) {
		JSplitPane splFilter = new JSplitPane(orientation, left, right);
		splFilter.setResizeWeight(weight);
		splFilter.setBorder(null);
		return splFilter;
	}

}
