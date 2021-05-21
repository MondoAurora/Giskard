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
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_DIALOG, MTMEMBER_PLAIN_STRING);

			Rectangle rct = DustSwingUtils.toRect(MTMEMBER_ACTION_THIS);

//			Integer cx = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_CENTER,
//					MTMEMBER_GEODATA_COORDS, 0);
//			Integer cy = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_CENTER,
//					MTMEMBER_GEODATA_COORDS, 1);
//			Integer sx = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_SPAN,
//					MTMEMBER_GEODATA_COORDS, 0);
//			Integer sy = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_SPAN,
//					MTMEMBER_GEODATA_COORDS, 1);
//			Giskard.log(MiNDEventLevel.Info, "Frame", label, ", center (", cx, cy, "), size (", sx, sy, ")");

			frmMain = new JFrame();
			frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmMain.setTitle(label);
			Giskard.access(MiNDAccessCommand.Set, frmMain, MTMEMBER_ACTION_DIALOG, MTMEMBER_VALUE_RAW);

//				if ( null != Giskard.access(MiNDAccessCommand.GetNew, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_DIALOG,	MTMEMBER_LINK_ONE) ) {
//					Giskard.access(MiNDAccessCommand.GetNew, MTMEMBER_CALL_PARAM, MTMEMBER_ACTION_DIALOG);					
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
