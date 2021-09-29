package me.giskard.java8;

import java.util.Arrays;

import me.giskard.GiskardConsts;

public interface GiskardJavaConsts extends GiskardConsts, GiskardTokensMind, GiskardTokensGeneric, GiskardTokensMachine {
	
	String SEP_TOKEN = "::";
	
	String FMT_TIMESTAMP = "yyyy.MM.dd_HH.mm.ss";
	
	enum GiskardUnits {
		Model, Idea, Narrative, Dialog,
		
		Machine, Generic
	}
	
	class GisJavaEntity implements GiskardEntity {
		private static long nextId = 0;
		
		private static synchronized long getId() {
			return nextId++;
		}
		
		public final long id;
		
		public GisJavaEntity() {
			id = getId();
		}
		
		@Override
		public String toString() {
			return getClass().getSimpleName() + ": " + id;
		}
	}
	
	abstract class GisJavaCoreAgent implements GiskardAgent {
		protected final GiskardJava gisJava;
		
		public GisJavaCoreAgent(GiskardJava gisJava_) {
			this.gisJava = gisJava_;
		}
	}	
	
	class GisJavaException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		Object[] params;
		
		GisJavaException(Throwable cause, Object... params) {
			super(cause);
			this.params = Arrays.copyOfRange(params, 0, params.length);
		}
	}

}
