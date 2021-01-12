package ai.montru.java;

import ai.montru.java.ui.swing.MontruSwingFrame;

public class MontruMain implements MontruTokens {
	public static void main(String[] args) throws Exception {
		MontruSwingFrame frame = new MontruSwingFrame();
		frame.agentAction(DT_MIND_NARRATIVE_ACTION_INIT);
	}
}
