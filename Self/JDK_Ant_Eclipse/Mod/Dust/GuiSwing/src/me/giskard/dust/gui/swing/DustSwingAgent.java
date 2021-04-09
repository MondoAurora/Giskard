package me.giskard.dust.gui.swing;

import java.awt.Rectangle;

import javax.swing.JFrame;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public abstract class DustSwingAgent<CompType> implements DustSwingConsts, GiskardConsts.MiNDAgent {

	final CompType comp;

	protected DustSwingAgent(CompType c) {
		this.comp = c;
	}

	public static class Frame extends DustSwingAgent<JFrame> {
		public Frame() {
			super(new JFrame());
		}

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:

				String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
				Rectangle rct = DustSwingUtils.toRect(MTMEMBER_ACTION_THIS);

				Giskard.log(MiNDEventLevel.Info, "Frame", label, ", rectangle (", rct, ")");

				comp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				comp.setTitle(label);
				Giskard.access(MiNDAccessCommand.Set, comp, MTMEMBER_ACTION_THIS, MTMEMBER_VARIANT_VALUE);

				if ( null != Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_ACTION_THIS,
						MTMEMBER_LINK_ONE) ) {
					Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_PARAM, MTMEMBER_ACTION_THIS);
					Giskard.invoke();
				}

				comp.pack();
				comp.setVisible(true);

				comp.setSize(rct.width, rct.height);
				comp.setLocation(rct.getLocation());

				break;
			case Process:
				break;
			case Release:
				break;
			}

			return ret;
		}

	}

}
