package me.giskard.java8;

public interface GiskardTokensNarrative {

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
