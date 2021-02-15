package me.giskard;

public class GiskardApplication extends RuntimeConnector {
	public GiskardApplication(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws Exception {
		GiskardApplication app = new GiskardApplication(args);
		
		app.addModule(MODULE_MIND, "DustRuntime", "1.0");
		app.addModule("IO", "DustIO", "1.0", "json-simple-1.1.1.jar");
		
		app.launch();
	}

}
