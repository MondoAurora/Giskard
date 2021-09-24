package me.giskard.sandbox.mind.plain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GSBMindPlainModel implements GSBMindPlainConsts {

	class EntityData {
		Map<PlainEntity, Object> content = new HashMap<>();
	}

	Map<PlainEntity, EntityData> liveEntities = new HashMap<>();
	LinkedList<EntityData> pool = new LinkedList<>();

	protected Object access(MindAccessCmd cmd, Object value, Object... path) {
		return null;
	}

}
