package me.giskard;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class GiskardUtilsSwingCommandCenter implements GiskardUtilsConsts {
	private final GisUtilsCommandCenter cc;

	class CmdAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public final Enum<?> cmd;

		public CmdAction(Enum<?> cmd) {
			super(cmd.name());
			this.cmd = cmd;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				cc.execCmd(cmd);
			} catch (Throwable e1) {
				GiskardException.wrap(e1, "Executing command", cmd);
			}
		}
	};

	private final GisUtilsFactory<Enum<?>, CmdAction> actions = new GisUtilsFactory<Enum<?>, CmdAction>(
			new GisUtilsCreator<Enum<?>, CmdAction>() {
				public CmdAction create(Enum<?> key, Object... params) {
					return new CmdAction(key);
				}
			});

	private final Map<Integer, CmdAction> hotkeys = new HashMap<>();

	public GiskardUtilsSwingCommandCenter(GisUtilsCommandCenter cc) {
		this.cc = cc;
	}

	public CmdAction getAction(Enum<?> cmd) {
		return actions.get(cmd);
	}

	public void addHotkey(Integer key, Enum<?> cmd) {
		hotkeys.put(key, getAction(cmd));
	}

	public void setHotkeys(JComponent comp, int ctrlMask) {
		InputMap im = comp.getInputMap();
		ActionMap am = comp.getActionMap();

		for (Map.Entry<Integer, CmdAction> e : hotkeys.entrySet()) {
			CmdAction ca = e.getValue();
			String actName = ca.cmd.toString();
			KeyStroke keyStroke = KeyStroke.getKeyStroke(e.getKey(), ctrlMask);
			im.put(keyStroke, actName);
			am.put(actName, e.getValue());
		}
	}
}