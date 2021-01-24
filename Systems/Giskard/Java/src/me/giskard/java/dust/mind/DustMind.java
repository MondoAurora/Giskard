package me.giskard.java.dust.mind;

import me.giskard.java.Mind;
import me.giskard.java.dust.DustCollections;
import me.giskard.java.dust.DustConsts;

public class DustMind extends Mind implements DustConsts, DustCollections {
	
	DustLog log;
	DustTokenManager tokenManager;
	
	public DustMind() {
		log = new DustLog();
		tokenManager = new DustTokenManager();
	}

	@Override
	protected MiNDToken getToken_(String dslId, String termId) {
		return tokenManager.getToken(dslId, termId);
	}

	@Override
	protected void select_(MiNDToken target, MiNDToken... path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected <RetType> RetType access_(MiNDToken cmd, MiNDToken target, MiNDToken tMember, RetType val, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void log_(MiNDToken lvl, Object... obs) {
		log.log(lvl, obs);
	}
	
	@Override
	protected void init(String[] args) {
		LOGLEVEL_EXCEPTION = Mind.getToken("Event", "EventLvlCritical");

		MiNDToken t = Mind.getToken("test", "test");
		Mind.log(t, "Hello, world! ");
	}
}
