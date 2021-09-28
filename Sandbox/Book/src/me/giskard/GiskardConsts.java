package me.giskard;

import me.giskard.java8.GiskardGenericTokens;
import me.giskard.java8.GiskardMachineTokens;
import me.giskard.java8.GiskardMindTokens;

public interface GiskardConsts extends GiskardMindTokens, GiskardMachineTokens, GiskardGenericTokens {
	enum GiskardGetMode {
		Peek, Create, Update
	}

	enum GiskardSetMode {
		Replace, Add, Insert
	}

	enum GiskardVisitMode {
		Flat, Cut, Free
	}

	enum GiskardLogLevel {
		Critical, Error, Warning, Info, Trace, Debug
	};

	public interface GiskardEntity {
	}

	public interface GiskardAgent {
		public void agentProcess() throws Exception;
	}

}
