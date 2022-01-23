package ai.montru.modules;

import java.util.ArrayList;

import ai.montru.dust.app.test01.T01Agent01;
import ai.montru.dust.app.test01.T01Consts;
import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public class GisMontruTest01 implements T01Consts, GiskardConsts.GiskardModule {

	@Override
	public Iterable<GiskardEntityRef> gisInitModule(Giskard runtime, String name, String version) throws Exception {
		Giskard.registerNative(GIS_TYP_TEST01AGENT01, T01Agent01.class.getName());
		
		ArrayList<GiskardEntityRef> initTasks = new ArrayList<>();
		
		GiskardEntityRef refProc = Giskard.access(GiskardAccess.Insert, GIS_TYP_TEST01AGENT01);
		Giskard.access(GiskardAccess.Set, "Marco?", refProc, GIS_ATT_UTIL_ID);		
		initTasks.add(refProc);
		
		refProc = Giskard.access(GiskardAccess.Insert, GIS_TYP_TEST01AGENT01);
		Giskard.access(GiskardAccess.Set, "Polo!", refProc, GIS_ATT_UTIL_ID);		
		initTasks.add(refProc);
		
		refProc = Giskard.access(GiskardAccess.Insert, GIS_TYP_MIND_ITERATOR);
		Giskard.access(GiskardAccess.Set, "Simple counter", refProc, GIS_ATT_UTIL_ID);		
		Giskard.access(GiskardAccess.Set, 5, refProc, GIS_ATT_UTIL_RANGEINTMIN);		
		Giskard.access(GiskardAccess.Set, 15, refProc, GIS_ATT_UTIL_RANGEINTMAX);		
//		initTasks.add(refProc);
		
		refProc = Giskard.access(GiskardAccess.Insert, GIS_TYP_MIND_VISITOR);
		Giskard.access(GiskardAccess.Set, "Simple visitor", refProc, GIS_ATT_UTIL_ID);		
		Giskard.access(GiskardAccess.Set, refProc, refProc, GIS_ATT_UTIL_TARGET);		
//		initTasks.add(refProc);
		
		Giskard.broadcastEvent(null, getClass().getSimpleName(), "says: Hello, world!");
		
		return initTasks;
	}

}
