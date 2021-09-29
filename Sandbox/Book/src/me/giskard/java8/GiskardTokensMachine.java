package me.giskard.java8;

public interface GiskardTokensMachine {
	public enum MachineNode {
		OperatingSystem, Runtime, User, LaunchInfo,
	}
	
	public enum MachineLaunchInfo {
		Arguments, Environment
	}
	
	public enum MachineRuntime {
		Properties
	}
	
	public enum MachineModule {
		Tokens
	}
	
}