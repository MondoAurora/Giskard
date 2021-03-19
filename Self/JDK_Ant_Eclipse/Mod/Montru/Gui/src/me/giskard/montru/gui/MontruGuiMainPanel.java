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

//		pnlMain = new JPanel(new BorderLayout());
//		JComponent content = new JLabel("Montru GUI main panel", JLabel.CENTER);
//		pnlMain.add(content, BorderLayout.CENTER);
		
		StringBuilder sbAll = new StringBuilder();
		MiNDAgent reader = new MiNDAgent() {
			@Override
			public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
				switch ( action ) {
				case Process:
					Object p = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_LOCAL, MTMEMBER_VARIANT_VALUE);
					GiskardUtils.sbAppend(sbAll, "\n", false, p);
					return MiNDResultType.ACCEPT_READ;
				default:
					break;
				}
				return null;
			}
		};
		Giskard.access(MiNDAccessCommand.Use, reader);
		
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
