package me.giskard.dust.runtime.machine;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeUtils;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tools.GisToolsModuleServices;

public class DustMachine implements DustMachineConsts, DustRuntimeConsts.DustMachine, DustTokensMachine, DustTokensMind {

	private Map<String, DustMachineModule> modules = new TreeMap<>();

	private DustContext knowledge;
	private DustMachineModule modMind;
	private NativeConnector nativeConnector;
	
	@Override
	public void init(String mindModule, MiNDAgent agent) throws Exception {		
		knowledge = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_CONTEXT);

		modMind = new DustMachineModule(mindModule, agent);
		modules.put(mindModule, modMind);

		nativeConnector = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_NATIVECONNECTOR);
		
		optLoadNativeConn();
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
		DustMachineModule mod = new DustMachineModule(modName, clMod);
		modules.put(modName, mod);
		
		optLoadNativeConn();

		return mod;
	}

	@Override
	public DustContext getContext() {
		return knowledge;
	}
	
	@Override
	public MiNDResultType invoke(Object... agentPath) throws Exception {
//		if ( 0 == agentPath.length ) {
//			agentPath = new Object[] {MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP, MTMEMBER_APPLICATION_MAINAGENT};
//		}
//		
//		Giskard.log(MiNDEventLevel.TRACE, "invoking", agentPath);
//		
		
		// for testing, MTMEMBER_ACTION_LOCAL is set to agent
//		knowledge.selectByPath(MTMEMBER_ACTION_PARAM, MTMEMBER_ACTION_LOCAL, MTMEMBER_ENTITY_PRIMARYTYPE);
//		MiNDAgent agent = nativeConnector.access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_PARAM);
		MiNDAgent agent = nativeConnector.access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_LOCAL);
		
		return (null == agent) ? MiNDResultType.REJECT : agent.process(MiNDAgentAction.Process);
	}


	private void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

}
