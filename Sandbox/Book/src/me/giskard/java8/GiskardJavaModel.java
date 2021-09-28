package me.giskard.java8;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GiskardJavaModel extends GiskardJavaConsts.GisJavaCoreAgent implements GiskardJavaConsts {

	public GiskardJavaModel(GiskardJava mind) {
		super(mind);
	}

	class EntityData {
		Map<GisJavaEntity, Object> content = new HashMap<>();
	}

	Map<GisJavaEntity, EntityData> liveEntities = new HashMap<>();
	LinkedList<EntityData> pool = new LinkedList<>();

	protected Object access(MindNarrativeAccessCmd cmd, Object value, Object... path) {
		return null;
	}

	@Override
	public void agentProcess() throws Exception {
	}

}
