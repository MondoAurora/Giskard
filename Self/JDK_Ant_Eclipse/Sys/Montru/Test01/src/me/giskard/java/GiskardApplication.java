package me.giskard.java;

public class GiskardApplication extends RuntimeConnector {

	public static void main(String[] args) throws Exception {
		Mind.initMind("me.giskard.java.dust.mind.DustMind", args);
	}

}
