package me.giskard.dust.machine;

public interface DustMachineConsts extends DustGenConsts {
//public interface DustMachineConsts extends DustGenConstsSave {
//public interface DustMachineConsts extends DustMachineConstsOrig {
	
	enum MindValType {
		Int, Real, Handle, Bin,
	};

	enum MindCollType {
		One, Set, Arr, Map,
	};
	
	enum MindAccess {
		Check, Peek, Get, Set, Insert, Delete, Reset, Commit,
	};
	
	enum MindAction {
		Init, Begin, Process, End, Release,
	};

	enum MindResult {
		Reject, Pass, Read, ReadAccept, Accept,
	};
}
