package me.giskard.mind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GiskardBootParams implements GiskardConsts, GiskardConsts.MindBrain {

	ArrayList<String> params = new ArrayList<>();
	Map<String, Object> config = new HashMap<>();

	Object appModule;

	public void initParams(String[] cmdLine) {
		for (Map.Entry<String, String> e : System.getenv().entrySet()) {
			addConfigVal(e.getKey(), e.getValue());
		}

		Properties p = System.getProperties();
		for (String key : p.stringPropertyNames()) {
			addConfigVal(key, p.get(key));
		}

		for (String str : cmdLine) {
			if ( str.startsWith("-") ) {
				str = str.substring(1);
				String key;
				Object val;

				int idx = str.indexOf("=");
				if ( -1 == idx ) {
					key = str;
					val = Boolean.TRUE;
				} else {
					key = str.substring(0, idx);
					val = str.substring(idx + 1);
				}

				addConfigVal(key, val);
			} else {
				params.add(str);
			}
		}
	}

	protected void addConfigVal(String key, Object val) {
		String ls = null;

		if ( key.endsWith("*") ) {
			key = key.substring(0, key.length() - 1);
			String sVal = (String) val;
			if ( Character.isJavaIdentifierStart(sVal.charAt(0)) ) {
				ls = ",";
			} else {
				ls = sVal.substring(0, 1);
				sVal = sVal.substring(1);
			}

			ArrayList<String> al = new ArrayList<>();
			for (String s : sVal.split(ls)) {
				al.add(s);
			}
			val = al;
		}

		config.put(key, val);
	}

	@Override
	public MindStatus agentExecAction(MindAction action) throws Exception {
		return MindStatus.Reject;
	}

	@Override
	public <RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path) {
		Object ret = null;

		if ( root == BootParam.AppModule ) {
			ret = appModule;
			switch ( cmd ) {
			case Peek:
			case Get:
				break;
			case Insert:
			case Set:
				appModule = val;
				break;
			default:
				break;
			}
		} else {
			Object curr = (root == BootParam.LaunchParams) ? params : config;

			for (Object p : path) {
				if ( curr instanceof ArrayList ) {
					curr = ((ArrayList) curr).get((Integer) p);
				} else if ( curr instanceof Map ) {
					curr = ((Map) curr).get(p);
				} else {
					curr = null;
				}
			}

			ret = (null == curr) ? val : curr;
		}

		return (RetType) ret;
	}

	@Override
	public void log(Object event, Object... params) {
		GiskardUtils.dump(", ", false, params);
	}
}
