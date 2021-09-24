package me.giskard.sandbox.utils;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GSBSwingUtils {

	public static JComponent wrapComp(JComponent comp, String title, boolean scroll) {
		JComponent ret = scroll ? new JScrollPane(comp) : comp;
		if ( null != title ) {
			ret.setBorder(new TitledBorder(new LineBorder(Color.black), title));
		}
		return ret;
	}

	public static JComponent addActionComp(Enum<?> cmd, ActionListener al, JPanel panel) {
		JButton btn = new JButton(cmd.name());

		btn.setActionCommand(cmd.name());
		btn.addActionListener(al);
		btn.setFocusable(false);

		panel.add(btn);

		return btn;
	}

}
