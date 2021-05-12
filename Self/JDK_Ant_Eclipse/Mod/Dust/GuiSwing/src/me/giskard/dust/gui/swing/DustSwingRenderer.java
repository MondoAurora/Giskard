package me.giskard.dust.gui.swing;

import javax.swing.JFrame;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustSwingRenderer implements DustSwingConsts, GiskardConsts.MiNDAgent {

	JFrame frmMain;

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			break;
		case Process:

			if ( null == frmMain ) {
				String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_DIALOG, MTMEMBER_PLAIN_STRING);

				Integer cx = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_CENTER,	MTMEMBER_GEODATA_COORDS, 0);
				Integer cy = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_CENTER, MTMEMBER_GEODATA_COORDS, 1);
				Integer sx = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_SPAN, MTMEMBER_GEODATA_COORDS, 0);
				Integer sy = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_DIALOG, MTMEMBER_AREA_SPAN, MTMEMBER_GEODATA_COORDS, 1);
				Giskard.log(MiNDEventLevel.Info, "Frame", label, ", center (", cx, cy, "), size (", sx, sy, ")");

				frmMain = new JFrame();
				frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmMain.setTitle(label);
				Giskard.access(MiNDAccessCommand.Set, frmMain, MTMEMBER_ACTION_DIALOG, MTMEMBER_VALUE_RAW);

				if ( null != Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_DIALOG,	MTMEMBER_LINK_ONE) ) {
					Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_PARAM, MTMEMBER_ACTION_DIALOG);					
//					Giskard.invoke();
				}

				frmMain.pack();
				frmMain.setVisible(true);

				frmMain.setSize(sx, sy);
				frmMain.setLocation(cx - sx / 2, cy - sy / 2);
			}

			break;
		case Release:
			break;
		}

		return ret;
	}

}
