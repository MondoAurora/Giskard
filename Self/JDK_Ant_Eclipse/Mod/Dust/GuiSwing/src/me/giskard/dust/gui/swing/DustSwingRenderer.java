package me.giskard.dust.gui.swing;

import java.awt.Rectangle;

import javax.swing.JFrame;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustSwingRenderer implements DustSwingConsts, GiskardConsts.MiNDAgent {

	JFrame frmMain;

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		if ( null == frmMain ) {
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEM_GENERIC_ACTION_DIALOG, MTMEM_TEXT_PLAINTEXT_STRING);

			Rectangle rct = DustSwingUtils.toRect(MTMEM_GENERIC_ACTION_THIS);

			frmMain = new JFrame();
			frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmMain.setTitle(label);
			Giskard.access(MiNDAccessCommand.Set, frmMain, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_GENERIC_VALUE_RAW);

//				if ( null != Giskard.access(MiNDAccessCommand.GetNew, MTMEM_CALL_TARGET, MTMEM_GENERIC_ACTION_DIALOG,	MTMEM_GENERIC_LINK_ONE) ) {
//					Giskard.access(MiNDAccessCommand.GetNew, MTMEM_CALL_PARAM, MTMEM_GENERIC_ACTION_DIALOG);					
////					Giskard.invoke();
//				}

			frmMain.pack();
			frmMain.setVisible(true);

//			frmMain.setSize(sx, sy);
//			frmMain.setLocation(cx - sx / 2, cy - sy / 2);
			
			frmMain.setSize(rct.width, rct.height);
			frmMain.setLocation(rct.getLocation());

		}

		return ret;
	}

}
