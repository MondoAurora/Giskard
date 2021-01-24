package me.giskard.java.dust;

import me.giskard.java.Mind;

public class DustMind extends Mind implements DustConsts, DustCollections {
	static {
		LOG = new DustLog();
		TOKEN_SOURCE = DustToken.TOKEN_SOURCE;
	}
	
	public static void main(String[] args) {
		MiNDToken t = Mind.getToken("test", "test");
		Mind.log(t, "Hello, world! ");
	}
}
