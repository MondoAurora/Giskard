package me.giskard.sandbox.mind.plain;

import me.giskard.sandbox.mind.GSBMindConsts;

public interface GSBMindPlainConsts extends GSBMindConsts {
	
	class PlainEntity implements MindEntity {
		private static long nextId = 0;
		
		private static synchronized long getId() {
			return nextId++;
		}
		
		public final long id;
		
		public PlainEntity() {
			id = getId();
		}
	}
	
	class PlainException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public PlainException(Throwable cause, Object... params) {
			super(cause);
		}
		
	}
	
}
