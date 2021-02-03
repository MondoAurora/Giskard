package me.giskard;

public interface MindConsts {

	enum MiNDEventLevel {
		CRITICAL, ERROR, WARNING, INFO, TRACE, DEBUG
	};

	enum MiNDAgentAction {
		INIT, BEGIN, PROCESS, END, RELEASE
	};

	enum MiNDDialogCmd {
		CHK, GET, SET, ADD, DEL
	};

	enum MiNDResultType {
		NOTIMPLEMENTED, REJECT, ACCEPT_PASS, ACCEPT, ACCEPT_READ, READ
	};
	
	public interface MiNDEntity {
	}

	public interface MiNDContext {
		void select(MiNDEntity target, Object... path);
		<RetType> RetType access(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val, Object key);
	}

	public interface MiNDModuleManager {
		void addModule(String modName, String mainLib, String... extLibs);
		<RetType> RetType createObject(String modName, String binTypeId, Object... params);
		void deleteObject(String modName, String binTypeId, Object object);
	}

	public interface MiNDAgent {
		MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception;
	}

	public static final class MiNDException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		MiNDException(Throwable src) {
			super(src);
		}
	}

	interface EntityWrapper {
		MiNDEntity getEntity();
	}

}
