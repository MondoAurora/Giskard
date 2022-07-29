package me.giskard;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GiskardUtilsSwing implements GiskardUtilsConsts {

	public static JComponent wrapComp(JComponent comp, String title, boolean scroll) {
		JComponent ret = scroll ? new JScrollPane(comp) : comp;
		if ( null != title ) {
			ret.setBorder(new TitledBorder(new LineBorder(Color.black), title));
		}
		return ret;
	}

	public static JComponent addActionComp(Enum<?> cmd, GiskardUtilsSwingCommandCenter scc, JPanel panel) {
		JButton btn = new JButton(cmd.name());
		btn.setFocusable(false);
		
		btn.setAction(scc.getAction(cmd));
		panel.add(btn);

		return btn;
	}
}
