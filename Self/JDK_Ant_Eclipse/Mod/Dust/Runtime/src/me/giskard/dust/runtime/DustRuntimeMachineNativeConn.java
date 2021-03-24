package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardException;
import me.giskard.coll.GisCollConsts;
import me.giskard.tools.GisToolsModuleServices;

public class DustRuntimeMachineNativeConn implements DustRuntimeConsts, GisCollConsts, GiskardConsts.MiNDAgent {
	class DustModule implements MiNDBuilder<String, Object> {
		MiNDAgent modAgent;
		ClassLoader cl;

		DustModule(String mod, ClassLoader cl) throws Exception {
			this.cl = cl;
			modAgent = GisToolsModuleServices.initModule(cl, mod);
		}

		DustModule(String mod, MiNDAgent agent) throws Exception {
			this.cl = agent.getClass().getClassLoader();
			modAgent = agent;
		}

		@Override
		public Object create(String key) {
			try {
				Class<?> c = cl.loadClass(key);
				return c.newInstance();
			} catch (Exception e) {
				return GiskardException.wrap(e);
			}
		}

		@Override
		public void release(String key, Object v) {
			if ( v instanceof MiNDAgent ) {
				try {
					((MiNDAgent) v).process(MiNDAgentAction.Release);
				} catch (Exception e) {
					GiskardException.wrap(e);
				}
			}
		}
	}
	private Map<String, DustModule> modules = new TreeMap<>();

	private Map<Integer, Class<?>> nativeClasses = new HashMap<>();
	
	public DustRuntimeMachineNativeConn(MiNDAgent agent) throws Exception {
		DustModule modMind = new DustModule(MODULE_NAME, agent);
		modules.put(MODULE_NAME, modMind);
	}
	
	public Object addModule(String modName, String ver) throws Exception {
		if ( modules.containsKey(modName) ) {
			GiskardException.wrap(null, "Module already loaded", modName);
		}

		ClassLoader clMod = GisToolsModuleServices.getClassLoader(modName, ver);

		if ( null == clMod ) {
			Giskard.log(MiNDEventLevel.INFO, "would download module from server", modName, ver);
			return null;
		}

		Giskard.log(MiNDEventLevel.TRACE, "Adding module", modName, ver);
		DustModule mod = new DustModule(modName, clMod);
		modules.put(modName, mod);

		loadMappedClasses();

		return mod;
	}

	public void loadMappedClasses() throws Exception {
		process(MiNDAgentAction.Init);
	}


	@SuppressWarnings("unchecked")
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		switch ( cmd ) {
		case Add:
			try {
				Integer a = Giskard.access(MiNDAccessCommand.Get, null, target, MTMEMBER_ENTITY_PRIMARYTYPE);
				val = (RetType) nativeClasses.get(a).newInstance();
			} catch (Exception e) {
				GiskardException.wrap(e);
			}
			break;
		case Chk:
			break;
		case Del:
			break;
		case Get:
			break;
		case Set:
			break;
		case Use:
			break;
		default:
			break;
		}
		
		return (RetType) val;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action) throws Exception {
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			Giskard.access(MiNDAccessCommand.Use, this, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);
			break;
		case Process:
			Object agent = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_IMPLEMENTATION_AGENT);
			if ( null != agent ) {
				Class<?> c = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
				if ( null != c ) {
					nativeClasses.put((Integer) agent, c);
				}
			}
			break;
		case Release:
			break;
		}
		return MiNDResultType.ACCEPT;
	}

	@Override
	public String toString() {
		return nativeClasses.toString();
	}
}
