package me.giskard.java8;

public interface GiskardMindTokens {
	
	public enum MindIdeaMeta {
		Types, Attributes, Tags, Events,
	}
	
	public enum MindIdeaMultiplicity {
		One, Set, Arr, Map
	}
	
	public enum MindIdeaValueType {
		Entity, Integer, Real, Raw
	}
	
	enum MindNarrativeEventLevel {
		Critical, Error, Warning, Info, Trace, Debug
	};

	public enum MindNarrativeAccessCmd {
		Visit, Peek, Chk, Get, Set, Del
	}
	
	enum MindNarrativeAction {
		Init, Begin, Process, End, Release
	};
	
	enum MindNarrativeResult {
		Notimplemented, Reject, Accept, AcceptRead, Read, Wait
	};
	
}
