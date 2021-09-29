package me.giskard.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GiskardJavaModel extends GiskardJavaConsts.GisJavaCoreAgent implements GiskardJavaConsts {

	public GiskardJavaModel(GiskardJava mind) {
		super(mind);
	}

	class EntityData {
		GisJavaEntity e = new GisJavaEntity();
		Map<GisJavaEntity, Object> content = new HashMap<>();

		@Override
		public String toString() {
			return "[" + e + "]: " + content.toString();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	class PathResolver {
		MindNarrativeAccessCmd cmd;
		Object[] path;

		Object lastColl;
		Object lastKey;
		Object currValue;

		boolean found;

		PathResolver(MindNarrativeAccessCmd cmd, Object... path) {
			this.cmd = cmd;
			this.path = path;

			currValue = liveEntities;

			for (Object p : path) {
				if ( p instanceof Enum<?> ) {
					p = gisJava.idea.resolveEnum((Enum<?>) p);
				}

				if ( null == currValue ) {
					if ( p instanceof Integer ) {
						currValue = new ArrayList();
					} else {
						currValue = new HashMap<>();
					}
					
					update(currValue);
				}

				lastColl = currValue;
				if ( lastColl instanceof EntityData ) {
					lastColl = ((EntityData)lastColl).content;
				}
				lastKey = p;

				currValue = (p instanceof Integer) ? ((ArrayList) lastColl).get((Integer) p) : ((Map) lastColl).get(p);
			}
		}
		
		void update(Object value) {
			if ( lastKey instanceof Integer ) {
				((ArrayList) lastColl).add(value);
			} else {
				((Map) lastColl).put(lastKey, value);
			}
		}
		
		Object set(Object value) {
			update(value);
			return currValue;
		}
	}

	Map<GisJavaEntity, EntityData> liveEntities = new HashMap<>();
	LinkedList<EntityData> pool = new LinkedList<>();

	GisJavaEntity createEntity() {
		EntityData ed;

		if ( !pool.isEmpty() ) {
			ed = pool.remove();
		} else {
			ed = new EntityData();
		}

		liveEntities.put(ed.e, ed);

		return ed.e;
	}

	public EntityData getEntityData(GiskardEntity gje) {
		return liveEntities.get(gje);
	}

	protected Object access(MindNarrativeAccessCmd cmd, Object value, Object... path) {
		PathResolver pr = new PathResolver(cmd, path);
		Object ret = value;
		
		switch ( cmd ) {
		case Chk:
			break;
		case Del:
			break;
		case Get:
			ret = pr.currValue;
			break;
		case Peek:
			break;
		case Set:
			if ( value.getClass().isArray() ) {
				List<String> list1 = new ArrayList<String>();
	      Collections.addAll(list1, (String[]) value);
	      value = list1;
			}
			ret = pr.set(value);
			break;
		case Visit:
			break;
		}
		
		return ret;
	}

	@Override
	public void agentProcess() throws Exception {
	}

}
