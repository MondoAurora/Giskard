package me.giskard.dust.runtime;

import me.giskard.GiskardConsts;

public interface DustRuntimeConsts extends GiskardConsts {
	String CLASSNAME_RUNTIME = "me.giskard.dust.runtime.DustRuntime";
	String CLASSNAME_BOOT = "me.giskard.dust.runtime.DustRuntimeBoot";
	
	String CLASSNAME_CONTEXT = "me.giskard.dust.runtime.model.DustModelContext";
	
	String CLASSNAME_MACHINE = "me.giskard.dust.runtime.machine.DustMachine";
	String CLASSNAME_NATIVECONNECTOR = "me.giskard.dust.runtime.machine.DustMachineNativeConnector";
	
	public interface DustAccessor {
		<RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath);
	};

	public interface DustContext extends DustAccessor {
		MiNDToken defineToken(MiNDTokenType type, String name, Object... params);
		void selectByPath(MiNDToken target, Object... path);
	}

	public interface DustRuntime {
		void setMachine(DustMachine machine);
	}

	public interface DustMachine extends DustAccessor {
		void init(String mindModule, MiNDAgent agent) throws Exception;
		Object addModule(String modName, String ver) throws Exception;
		void launch() throws Exception;
		MiNDAgent testCreateAgent(MiNDToken token) throws Exception;
	}

}
