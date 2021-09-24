package me.giskard.sandbox.mind;

public interface GSBMindConsts {
	public interface MindEntity {
		
	}
	
	public enum MindMultiplicity {
		One, Set, Arr, Map
	}
	
	public enum MindValueType {
		Entity, Integer, Real, Raw
	}
	
	public enum MindAccessCmd {
		Visit, Peek, Chk, Get, Set, Del
	}
	
	
	enum MindEventLevel {
		Critical, Error, Warning, Info, Trace, Debug
	};

	enum MindAction {
		Init, Begin, Process, End, Release
	};
	
	enum MindResult {
		Notimplemented, Reject, Accept, AcceptRead, Read, Wait
	};

	public interface MindAgent {
		public  MindResult agentProcess() throws Exception;
	}	
	
}
