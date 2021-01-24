package me.giskard.java;

public interface MindConsts {
	interface MiNDToken {
	}
	
	interface MiNDTokenSource {
		MiNDToken resolve(String dslId, String termId);
	}
	
//	interface Unit {
//		Object resolve(String name);
//	}
	
	interface MiNDContext {
		void select(MiNDToken target, MiNDToken... path);
		<RetType> RetType access(MiNDToken cmd, MiNDToken target, MiNDToken tMember, RetType val, Object key);
	}
	
	interface MiNDAgent {
		void process() throws Exception;
	}
	
	interface MiNDLog {
		void log(MiNDToken lvl, Object... obs);
	}
	
	interface MiNDRuntime extends MiNDContext, MiNDAgent {
	}
}
