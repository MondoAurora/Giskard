package me.giskard.java8;

public interface GiskardMachineTokens {
	public enum MachineNode {
		OperatingSystem, Runtime, User, LaunchInfo,
		
		Units, Modules
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