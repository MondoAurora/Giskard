package me.giskard.dust.runtime;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;
import me.giskard.tools.GisToolsModuleServices;

public class DustRuntimeNativeConnector implements DustRuntimeConsts, GisCollConsts, GiskardConsts.MiNDAgent {
	class DustModule implements MiNDBuilder<String, Object> {
		MiNDAgent modAgent;
		ClassLoader cl;

		DustModule(String mod, ClassLoader cl) throws Exception {
			this.cl = cl;
			modules.put(mod, this);
			modAgent = GisToolsModuleServices.initModule(cl, mod);
		}

		DustModule(String mod, MiNDAgent agent) throws Exception {
			this.cl = agent.getClass().getClassLoader();
			modAgent = agent;
		}

		public void optInitUnit(String unitName) {
			try {
				Class<?> c = cl.loadClass(GISKARD_PREFIX_UNIT + unitName);
				Method init = c.getMethod("init");
				init.invoke(null);
			} catch (Exception e) {
				GiskardException.swallow(e);
			}
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
	private Map<String, DustModule> provider = new TreeMap<>();

	private Map<Integer, Class<?>> nativeClasses = new HashMap<>();

	public DustRuntimeNativeConnector(MiNDAgent agent) throws Exception {
		DustModule modMind = new DustModule(MODULE_NAME, agent);
		modules.put(MODULE_NAME, modMind);
	}

	public Object addModule(String modName, String ver) throws Exception {
		if ( modules.containsKey(modName) ) {
			GiskardException.wrap(null, "Module already loaded", modName);
		}

		ClassLoader clMod = GisToolsModuleServices.getClassLoader(modName, ver);

		if ( null == clMod ) {
			Giskard.log(MiNDEventLevel.Info, "would download module from server", modName, ver);
			return null;
		}

		Giskard.log(MiNDEventLevel.Trace, "Adding module", modName, ver);
		DustModule mod = new DustModule(modName, clMod);

		return mod;
	}

	public <RetType> RetType createNative(MiNDToken target) {
		Integer a = Giskard.access(MiNDAccessCommand.Get, null, target, MTMEMBER_ENTITY_PRIMARYTYPE);
		return createNative(a);
	}

	@SuppressWarnings("unchecked")
	public <RetType> RetType createNative(Integer hType) {
		try {
			return (RetType) nativeClasses.get(hType).newInstance();
		} catch (Exception e) {
			return GiskardException.wrap(e);
		}
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
			break;
		case Process:
			Object pt = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_TOKEN);

			if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_MACHINE_MODULES).entityHandle, pt) ) {
				String modName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
						MTMEMBER_PLAIN_STRING);
				String modVer = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
						MTMEMBER_VERSIONED_SIGNATURE);

				if ( !MODULE_NAME.equals(modName) ) {
					addModule(modName, modVer);
				}
			} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_MODULE_NATIVES).entityHandle, pt) ) {
				Object agent = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_KEYMAP);
				
				if ( null != agent ) {
					Class<?> c = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
							MTMEMBER_VARIANT_VALUE);
					if ( null != c ) {
						nativeClasses.put((Integer) agent, c);
					}
				}
			} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_CONN_PROVIDES).entityHandle, pt) ) {
				String unitName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
						MTMEMBER_PLAIN_STRING);
				
				if ( !provider.containsKey(unitName) ) {
					String modName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_LINK_ONE,
							MTMEMBER_PLAIN_STRING);

					Giskard.log(MiNDEventLevel.Trace, "Module", modName, "provides unit", unitName);
					DustModule mod = modules.get(modName);
					mod.optInitUnit(unitName);
					
					provider.put(unitName, mod);
				}
			}
			break;
		case Release:
			break;
		}

		return ret;
	}

	@Override
	public String toString() {
		return nativeClasses.toString();
	}
}
