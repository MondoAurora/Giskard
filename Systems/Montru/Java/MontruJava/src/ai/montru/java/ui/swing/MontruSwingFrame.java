package ai.montru.java.ui.swing;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ai.montru.java.MontruTokens;
import ai.montru.java.dust.DustWrapperAgent;

public class MontruSwingFrame extends DustWrapperAgent<JFrame> implements MontruTokens {

	@Override
	protected JFrame createWrappedInstance() {
		return new JFrame();
	}
	
	@Override
	public Token agentAction(Token action) throws Exception {
		AgentAction ac = (AgentAction) Token.getEnumKey(action);
		JFrame frm = getWrappedInstance();
		
		switch ( ac ) {
		case INIT:
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			String title = 	"MontruJava";
			frm.setTitle(title);
			
			JLabel lbTest = new JLabel("Test", JLabel.CENTER);
			lbTest.setPreferredSize(new Dimension(800, 600));
			frm.getContentPane().add(lbTest);

			frm.pack();
			frm.setVisible(true);
			break;

		default:
			break;
		}
		
		return null;
	}
	
}
