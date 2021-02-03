package me.giskard;

public class GiskardApplication extends RuntimeConnector {
	public GiskardApplication(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws Exception {
		GiskardApplication app = new GiskardApplication(args);
		
		app.addModule("MiND", "DustRuntime");
		app.addModule("IO", "DustIO", "json-simple-1.1.1.jar");
		
		app.initMind("MiND", "me.giskard.dust.mind.DustMind", args);
	}

}
