package me.giskard.montru.gui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

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

		JSplitPane splFilter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				new JLabel("Filter conditions", JLabel.CENTER),
				new JLabel("Entity list", JLabel.CENTER));
		splFilter.setResizeWeight(0.2);

		JSplitPane splSelector = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splFilter,
				new JLabel("Controls", JLabel.CENTER));
		splSelector.setResizeWeight(1);

		JSplitPane splLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				new JLabel("View selector", JLabel.CENTER), splSelector);
		splLeft.setResizeWeight(0);

		JSplitPane splRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JLabel("Main knowledge graph", JLabel.CENTER),
				new JLabel("Selected entity property sheet", JLabel.CENTER));
		splRight.setResizeWeight(0.8);

		JSplitPane splMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splLeft, splRight);
		splMain.setResizeWeight(0.2);

		pnlMain = splMain;
	}

}
