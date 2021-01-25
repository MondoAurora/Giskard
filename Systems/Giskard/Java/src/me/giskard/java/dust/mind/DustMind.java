package me.giskard.java.dust.mind;

import java.text.MessageFormat;

import me.giskard.java.Mind;
import me.giskard.java.dust.DustCollections;
import me.giskard.java.dust.DustConsts;

public class DustMind extends Mind implements DustConsts, DustCollections {
	
	DustLog log;
	DustFactory<String, DustEntity> entities = new DustFactory<>(true, ENTITY_CREATOR);
	
	public DustMind() {
		log = new DustLog();
	}

	@Override
	protected MiNDEntity getEntity_(Object unitName, Object entityId) {
		return entities.get(MessageFormat.format(FMT_ENTITY_ID, unitName, entityId));
	}

	@Override
	protected void select_(MiNDEntity target, Object... path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected <RetType> RetType access_(MiNDEntity cmd, MiNDEntity target, MiNDEntity tMember, RetType val, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void log_(MiNDEntity lvl, Object... obs) {
		log.log(lvl, obs);
	}
	
	@Override
	protected void init(String[] args) {
		LOGLEVEL_EXCEPTION = Mind.getEntity("Event", "EventLvlCritical");

		MiNDEntity t = LOGLEVEL_EXCEPTION;
		Mind.log(t, "Hello, world! ");
	}
}
