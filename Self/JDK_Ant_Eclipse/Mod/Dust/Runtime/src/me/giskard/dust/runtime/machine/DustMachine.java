package me.giskard.dust.runtime.machine;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.coll.MindCollConsts.MiNDBuilder;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.tools.GisToolsModuleServices;

public class DustMachine implements DustMachineConsts, DustRuntimeConsts.DustMachine {

	protected class MachineModule implements MiNDBuilder<String, Object>, DustAccessor {
		MiNDAgent modAgent;
		ClassLoader cl;

		MachineModule(String mod, ClassLoader cl) throws Exception {
			this.cl = cl;
			modAgent = GisToolsModuleServices.initModule(cl, mod);
			optLoadNativeConn();
		}

		MachineModule(String mod, MiNDAgent agent) throws Exception {
			this.cl = agent.getClass().getClassLoader();
			modAgent = agent;
			optLoadNativeConn();
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

		@Override
		public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
			return null;
		}
	}

	protected MachineModule modMind;
	private Map<String, MachineModule> modules = new TreeMap<>();
	protected NativeConnector nativeConnector;

	@Override
	public void init(String mindModule, MiNDAgent agent) throws Exception {
		modMind = new MachineModule(mindModule, agent);
		modules.put(mindModule, modMind);

		nativeConnector = (NativeConnector) Class.forName(CLASSNAME_NATIVECONNECTOR).newInstance();
		optLoadNativeConn();
	}

	public void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

	@Override
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
		MachineModule mod = new MachineModule(modName, clMod);
		modules.put(modName, mod);
		return mod;
	}

	@Override
	public MiNDAgent testCreateAgent(MiNDToken token) throws Exception {
		return nativeConnector.access(MiNDAccessCommand.Add, null, token);
	}

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		return modMind.access(cmd, val, target, valPath);
	}

	public final void launch() throws Exception {
		Giskard.log(MiNDEventLevel.INFO, "Native assignments:", nativeConnector);
		Giskard.log(MiNDEventLevel.INFO, "GISKARD boot success.");

		modMind.modAgent.process(MiNDAgentAction.Begin);

		Giskard.log(MiNDEventLevel.INFO, "GISKARD finished.");
	}

}
