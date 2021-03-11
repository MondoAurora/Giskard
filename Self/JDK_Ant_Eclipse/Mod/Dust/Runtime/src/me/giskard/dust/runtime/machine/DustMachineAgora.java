package me.giskard.dust.runtime.machine;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeUtils;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tools.GisToolsModuleServices;

public class DustMachineAgora implements DustMachineConsts, DustRuntimeConsts.DustMachine, DustTokensMachine, DustTokensMind, DustTokensGeneric {
	private DustMachineModule modMind;
	private Map<String, DustMachineModule> modules = new TreeMap<>();

	DustContext knowledge;
	DustMachineControl.Invocation invocation;
	NativeConnector nativeConnector;
	
	@Override
	public void init(DustContext knowledge_, MiNDAgent agent) throws Exception {		
		this.knowledge = knowledge_;

		modMind = new DustMachineModule(MODULE_NAME, agent);
		modules.put(MODULE_NAME, modMind);

		nativeConnector = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_NATIVECONNECTOR);
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
		return (null == invocation) ? knowledge : invocation;
	}
	
	@Override
	public MiNDResultType invoke(Object... agentPath) throws Exception {
		int pl = agentPath.length;
		MiNDToken tAgent = (0 < pl) ? (MiNDToken) agentPath[0] : MTMEMBER_ACTION_TARGET;
		MiNDToken tParam = (1 < pl) ? (MiNDToken) agentPath[1] : MTMEMBER_ACTION_PARAM;
		
		if ( null == invocation ) {
			invocation = new DustMachineControl.Invocation(this, tAgent, tParam);
			invocation.process(MiNDAgentAction.Init);
			MiNDResultType ret;
			
			do {
				ret = invocation.process(MiNDAgentAction.Process);
			} while ( GiskardUtils.isAgentRead(ret) );
			
			return ret;
		} else {
			invocation.invoke(this, tAgent, tParam);
			return MiNDResultType.ACCEPT;
		}
		
//		if ( 0 == agentPath.length ) {
//			agentPath = new Object[] {MTSHARED_MACHINE, MTMEMBER_MACHINE_CURRENTAPP, MTMEMBER_APPLICATION_MAINAGENT};
//		}
//		
//		Giskard.log(MiNDEventLevel.TRACE, "invoking", agentPath);
//		
		
		// for testing, MTMEMBER_ACTION_LOCAL is set to agent
//		knowledge.selectByPath(MTMEMBER_ACTION_PARAM, MTMEMBER_ACTION_LOCAL, MTMEMBER_ENTITY_PRIMARYTYPE);
//		MiNDAgent agent = nativeConnector.access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_PARAM);
//		MiNDAgent agent = nativeConnector.access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_LOCAL);
//		
//		return (null == agent) ? MiNDResultType.REJECT : agent.process(MiNDAgentAction.Process);
	}

	public void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

}
