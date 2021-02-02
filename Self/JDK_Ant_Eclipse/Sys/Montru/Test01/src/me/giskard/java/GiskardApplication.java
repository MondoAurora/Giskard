package me.giskard.java;

public class GiskardApplication extends RuntimeConnector {
	public static String MODNAME_MIND = "MiND";

	public GiskardApplication(String[] args) {
		super(args);
	}

	public static void main(String[] args) throws Exception {
		GiskardApplication app = new GiskardApplication(args);
		
		app.addModule(MODNAME_MIND, "DustRuntime");
		
		Mind m = app.createObject(MODNAME_MIND, "me.giskard.java.dust.mind.DustMind");
		Mind.initMind(m, args);
	}

}
