package ai.montru.modules;

import ai.montru.dust.app.test01.T01Agent01;
import ai.montru.dust.app.test01.T01Consts;
import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public class GisTest01 implements T01Consts, GiskardConsts.GiskardModule {

	@Override
	public void gisInitModule(Giskard runtime, String name, String version) throws Exception {
		Giskard.registerNative(GIS_TYP_TEST01AGENT01, T01Agent01.class.getName());
		
		GiskardEntityRef refMain = Giskard.access(GiskardAccess.Insert, GIS_TYP_TEST01AGENT01, null);
		Giskard.access(GiskardAccess.Set, "Hello?", refMain, GIS_ATT_UTIL_ID);		

		
		Giskard.initiateProcess(refMain);
		
		Giskard.broadcastEvent(null, getClass().getSimpleName(), "says: Hello, world!");
	}

}
