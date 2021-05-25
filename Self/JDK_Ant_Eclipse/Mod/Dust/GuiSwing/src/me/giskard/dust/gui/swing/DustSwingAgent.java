package me.giskard.dust.gui.swing;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public abstract class DustSwingAgent<CompType> implements DustSwingConsts, GiskardConsts.MiNDAgentResource {

	final CompType comp;

	protected DustSwingAgent(CompType c) {
		this.comp = c;
	}
	
	@Override
	public void mindAgentInit() throws Exception {
	}
	
	@Override
	public void mindAgentRelease() throws Exception {
	}

	public static class SwingLabel extends DustSwingAgent<JLabel> {
		public SwingLabel() {
			super(new JLabel());
		}

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
			comp.setText(label);
			return MiNDResultType.Accept;
		}
	}
	
	public static class SwingPanel extends DustSwingAgent<JPanel> {
		public SwingPanel() {
			super(new JPanel());
			
			comp.setBackground(Color.yellow);
		}

		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			return MiNDResultType.Accept;
		}
	}

	public static class SwingFrame extends DustSwingAgent<JFrame> {
		public SwingFrame() {
			super(new JFrame());
		}
		
		@Override
		public void mindAgentInit() throws Exception {

			comp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			comp.pack();
			comp.setVisible(true);

			Giskard.access(MiNDAccessCommand.Set, comp, MTMEMBER_ACTION_THIS, MTMEMBER_VALUE_RAW);

			mindAgentProcess();
		}
		
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			String label = Giskard.access(MiNDAccessCommand.Get, "???", MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
			Rectangle rct = DustSwingUtils.toRect(MTMEMBER_ACTION_THIS);

			Giskard.log(MiNDEventLevel.Info, "Frame", label, ", rectangle (", rct, ")");

			comp.setTitle(label);
			
			comp.setSize(rct.width, rct.height);
			comp.setLocation(rct.getLocation());

			return MiNDResultType.Accept;
		}

	}

}
