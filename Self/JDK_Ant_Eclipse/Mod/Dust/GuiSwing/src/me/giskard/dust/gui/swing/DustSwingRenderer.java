package me.giskard.dust.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustSwingRenderer implements DustSwingConsts, GiskardConsts.MiNDAgent {

	JFrame frmMain;

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT;
		
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			break;
		case Process:
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_PARAM, MTMEMBER_STRING);
			
			Giskard.selectByPath(MTMEMBER_ACTION_TEMP01, MTMEMBER_ACTION_PARAM, MTMEMBER_AREA_CENTER);			
			Giskard.log(MiNDEventLevel.INFO, "Frame center", MTMEMBER_ACTION_TEMP01);

			frmMain = new JFrame();
			frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmMain.setTitle(label);
			
			JLabel lblTest = new JLabel("Test content", JLabel.CENTER);
			lblTest.setPreferredSize(new Dimension(200, 70));
			
			frmMain.getContentPane().add(lblTest, BorderLayout.CENTER);
			frmMain.pack();
			frmMain.setVisible(true);
			break;
		case Release:
			break;
		}
		
		return ret;
	}
	

}
