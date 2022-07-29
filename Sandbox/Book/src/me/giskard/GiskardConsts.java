package me.giskard;

import me.giskard.java8.GiskardTokensGeneric;
import me.giskard.java8.GiskardTokensMachine;
import me.giskard.java8.GiskardTokensMind;

public interface GiskardConsts extends GiskardTokensMind, GiskardTokensMachine, GiskardTokensGeneric {
	enum GiskardGetMode {
		Peek, Create, Update
	}

	enum GiskardSetMode {
		Replace, Add, Insert
	}

	enum GiskardVisitMode {
		Flat, Cut, Free
	}

	enum GiskardEventLevel {
		Critical, Error, Warning, Info, Trace, Debug
	};

	public interface GiskardEntity {
	}

	public interface GiskardAgent {
		public void agentProcess() throws Exception;
	}

}
