package me.giskard.dust.runtime;

import me.giskard.GiskardConsts;

public interface DustRuntimeConsts extends GiskardConsts {
	String MODULE_NAME = "DustRuntime";
	
	String PACKAGENAME_RUNTIME = DustRuntimeConsts.class.getPackage().getName();
	
	String CLASSPATH_GISKARD = "DustRuntimeGiskard";
	
	String CLASSPATH_CONTEXT = "knowledge.DustKnowledgeContext";
	String CLASSPATH_MACHINE = "machine.DustMachineAgora";
	String CLASSPATH_NATIVECONNECTOR = "machine.DustMachineNativeConnector";
	
	public interface DustContext {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		void selectByPath(MiNDToken target, Object... path);
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath);
		void put(MiNDToken token, Object block);
	}

	public interface DustRuntime {
		void init(MiNDAgent agent) throws Exception;
		void afterBoot() throws Exception;
	}

	public interface DustMachine {
		void init(DustContext knowledge, MiNDAgent agent) throws Exception;
		Object addModule(String modName, String ver) throws Exception;
		DustContext getContext();
		MiNDResultType invoke(Object... agentPath) throws Exception;
		void optLoadNativeConn() throws Exception;
	}

}
