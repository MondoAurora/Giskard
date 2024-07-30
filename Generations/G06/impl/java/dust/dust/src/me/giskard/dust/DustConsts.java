package me.giskard.dust;

public interface DustConsts {
	int KEY_ADD = -1;
	int KEY_SIZE = -2;

	public static abstract class MindHandle {
		protected MindHandle() {
		}
		
		public abstract String getId();
	};

	enum MindAccess {
		Check, Peek, Get, Set, Insert, Delete, Reset, Commit, Broadcast, Lookup, Visit, 
	};

}
