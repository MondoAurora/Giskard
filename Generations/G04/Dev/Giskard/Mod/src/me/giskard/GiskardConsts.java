package me.giskard;

public interface GiskardConsts {
	String SEP_ID = "_";

	String DEF_ENCODING = "UTF-8";
	String DEF_FORMAT_DATE = "YYYYMMdd";
	String DEF_FORMAT_TIMESTAMP = "YYYYMMdd_HHmmss_SSS";
	
	enum MiNDAccessCommand {
		Check(false), Peek(false), Get(true), Set(true), Insert(true), Delete(false), //Visit(false), Broadcast(false),
		
		;
		
		public final boolean creator;

		private MiNDAccessCommand(boolean creator) {
			this.creator = creator;
		}
	};

	enum MiNDAction {
		Init, Begin, Process, End, Release
	};

	enum MiNDResultType {
		Notimplemented(false, false), 
		Reject(false, false), Accept(true, false), 
		AcceptRead(true, true), Read(false, true), 
		Processing(false, true), Waiting(false, true),
		
		;
		
		public final boolean accept;
		public final boolean read;
		
		private MiNDResultType(boolean accept, boolean read) {
			this.accept = accept;
			this.read = read;
		}
	};

	public interface MiNDHandle {
		Object getId();
	};

	public interface MiNDAgent extends GiskardConsts {
		MiNDResultType mindAgentStep() throws Exception;
	}

	public interface GiskardImpl extends MiNDAgent {
		<RetType> RetType access_(MiNDAccessCommand cmd, Object val, MiNDHandle ref, MiNDHandle att, Object key);
		void broadcast_(MiNDHandle event, Object... params);
	}

	public static class MiNDSpecKey {
		public final String key;
		
		public MiNDSpecKey(String key) {
			this.key = "SpecKey_" + key;
		}

		@Override
		public String toString() {
			return key;
		}
	};
	
	final MiNDSpecKey KEY_APPEND = new MiNDSpecKey("Append");
	final MiNDSpecKey KEY_SIZE = new MiNDSpecKey("Size");
	final MiNDSpecKey KEY_FORMAT_STRING = new MiNDSpecKey("FormatString");

}
