package me.giskard.dust.runtime;

import me.giskard.GiskardConsts;

public interface DustRuntimeConsts extends GiskardConsts {
	String PACKAGENAME_RUNTIME = DustRuntimeConsts.class.getPackage().getName();
	
	String CLASSPATH_RUNTIME = "DustRuntime";
	String CLASSPATH_BOOT = "DustRuntimeBoot";
	
	String CLASSPATH_CONTEXT = "model.DustModelContext";
	String CLASSPATH_MACHINE = "machine.DustMachine";
	String CLASSPATH_NATIVECONNECTOR = "machine.DustMachineNativeConnector";
	
	public interface DustContext {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		void selectByPath(MiNDToken target, Object... path);
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath);
	}

	public interface DustRuntime {
		void init(String mindModule, MiNDAgent agent) throws Exception;
	}

	public interface DustMachine {
		void init(String mindModule, MiNDAgent agent) throws Exception;
		Object addModule(String modName, String ver) throws Exception;
		DustContext getContext();
		MiNDResultType invoke(Object... agentPath) throws Exception;
	}

}
