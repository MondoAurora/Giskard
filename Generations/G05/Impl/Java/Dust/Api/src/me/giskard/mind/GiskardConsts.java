package me.giskard.mind;

public interface GiskardConsts {
	String EXT_JAR = ".jar";
	String EXT_JSON = ".json";
	String SEP = "_";

	int KEY_ADD = -1;
	int KEY_SIZE = -2;
	int KEY_ITER = -3;
	int KEY_KEYS = -4;

	interface MindHandle {
		Object getUnitToken();

		Object getItemId();

		Object getId();
	};

	interface MindAgent {
		MindStatus agentExecAction(MindAction action) throws Exception;
	}

	interface MindBrain extends MindAgent {
		<RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path);

		void log(Object event, Object... params);
	}

	interface MindHandleEnum {
		Object getUnitToken();

		Object getItemId();

		MindHandleEnum getItemParent();
	};

	enum ModuleGiskardUnit implements MindHandleEnum {
		unitMiND("Giskard_MiND_1", 1),;

		final Object unitToken;
		final Object itemId;

		private ModuleGiskardUnit(Object unitToken, Object itemId) {
			this.unitToken = unitToken;
			this.itemId = itemId;
		}

		@Override
		public Object getUnitToken() {
			return unitToken;
		}

		@Override
		public Object getItemId() {
			return unitToken;
		}

		@Override
		public MindHandleEnum getItemParent() {
			return null;
		}
	}

	enum MindEnumGroup implements MindHandleEnum {
		tagValtype("Giskard_MiND_1", 1), tagXXXColl("Giskard_MiND_1", 1), tagXXXProxy("Giskard_MiND_1", 1);

		final Object unitToken;
		final Object itemId;

		private MindEnumGroup(Object unitToken, Object itemId) {
			this.unitToken = unitToken;
			this.itemId = itemId;
		}

		@Override
		public Object getUnitToken() {
			return unitToken;
		}

		@Override
		public Object getItemId() {
			return unitToken;
		}

		@Override
		public MindHandleEnum getItemParent() {
			return null;
		}
	}

	enum MindValType implements MindHandleEnum {
		tagValtypeInt("Giskard_MiND_1", 1), tagValtypeReal("Giskard_MiND_1", 1), tagValtypeHandle("Giskard_MiND_1", 1), tagValtypeBin("Giskard_MiND_1", 1),;

		final Object unitToken;
		final Object itemId;

		private MindValType(Object unitToken, Object itemId) {
			this.unitToken = unitToken;
			this.itemId = itemId;
		}

		@Override
		public Object getUnitToken() {
			return unitToken;
		}

		@Override
		public Object getItemId() {
			return unitToken;
		}

		@Override
		public MindHandleEnum getItemParent() {
			return MindEnumGroup.tagValtype;
		}
	};

	enum MindColl implements MindHandleEnum {
		One("Giskard_MiND_1", 1), Set("Giskard_MiND_1", 1), Arr("Giskard_MiND_1", 1), Map("Giskard_MiND_1", 1);

		final Object unitToken;
		final Object itemId;

		private MindColl(Object unitToken, Object itemId) {
			this.unitToken = unitToken;
			this.itemId = itemId;
		}

		@Override
		public Object getUnitToken() {
			return unitToken;
		}

		@Override
		public Object getItemId() {
			return unitToken;
		}

		@Override
		public MindHandleEnum getItemParent() {
			return MindEnumGroup.tagXXXColl;
		}
	};

	enum MindRoot {
		Action, Self, Dialog,
	};

	enum MindAccess {
		Check, Peek, Commit, Get, Set, Insert, Delete,

	};

	enum MindAction {
		Init, Begin, Process, End, Release,
	};

	enum MindStatus {
		Waiting, Processing, Reject, Pass, Read, ReadAccept, Accept, Error,
	};

	enum MindListener {
		Interceptor, Monitor, Processor, Follower,
	};
}
