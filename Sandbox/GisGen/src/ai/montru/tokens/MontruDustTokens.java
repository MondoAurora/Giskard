package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruDustTokens extends GiskardConsts {
	GiskardEntityRef GIS_UNI_DUST_REGISTRY = Giskard.getToken("Montru/Dust/Registry/1/0", null);

	GiskardEntityRef GIS_UNI_DUST_DEVGEN = Giskard.getToken("Montru/Dust/DevGen/1/0", null);
	GiskardEntityRef GIS_UNI_DUST_DEVJAVA = Giskard.getToken("Montru/Dust/DevJava/1/0", null);

	GiskardEntityRef GIS_UNI_DUST_RUNTIME = Giskard.getToken("Montru/Dust/Runtime/1/0", null);

	GiskardEntityRef GIS_TYP_DUST_RUNTIME = Giskard.getToken("Runtime", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_LOADEDUNITS = Giskard.getToken("RuntimeLoadedUnits", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_LOADEDMODULES = Giskard.getToken("RuntimeLoadedModules", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_PROCESSES = Giskard.getToken("RuntimeProcesses", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_THREADS = Giskard.getToken("RuntimeThreads", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_MODULE = Giskard.getToken("Module", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_NATIVES = Giskard.getToken("ModuleNatives", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_NATIVEAGENT = Giskard.getToken("NativeAgent", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_ASSIGNMENT = Giskard.getToken("NativeAgentAssignment", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_INSTANCE = Giskard.getToken("NativeAgentInstance", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_NATIVEASSIGNMENT = Giskard.getToken("NativeAssignment", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_PROCESS = Giskard.getToken("Process", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_LOCALENTITIES = Giskard.getToken("ProcessLocalEntities", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_CURRENTAGENT = Giskard.getToken("ProcessCurrentAgent", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_THREAD = Giskard.getToken("Thread", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_PROCESS = Giskard.getToken("ThreadProcess", GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_SELF = Giskard.getToken("Self", GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_THISMODULE = Giskard.getToken("SelfThisModule", GIS_UNI_DUST_RUNTIME);

}
