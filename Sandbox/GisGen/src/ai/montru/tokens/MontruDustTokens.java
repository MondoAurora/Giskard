package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruDustTokens extends GiskardConsts {
	GiskardEntityRef GIS_UNI_DUST_REGISTRY = Giskard.access(GiskardAccess.Get, "Montru/Dust/Registry/1/0", GiskardContext.Absolute);

	GiskardEntityRef GIS_UNI_DUST_DEVGEN = Giskard.access(GiskardAccess.Get, "Montru/Dust/DevGen/1/0", GiskardContext.Absolute);
	GiskardEntityRef GIS_UNI_DUST_DEVJAVA = Giskard.access(GiskardAccess.Get, "Montru/Dust/DevJava/1/0", GiskardContext.Absolute);

	GiskardEntityRef GIS_UNI_DUST_RUNTIME = Giskard.access(GiskardAccess.Get, "Montru/Dust/Runtime/1/0", GiskardContext.Absolute);

	GiskardEntityRef GIS_TYP_DUST_RUNTIME = Giskard.access(GiskardAccess.Get, "Runtime", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_LOADEDMODULES = Giskard.access(GiskardAccess.Get, "RuntimeLoadedModules", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_PROCESSES = Giskard.access(GiskardAccess.Get, "RuntimeProcesses", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_THREADS = Giskard.access(GiskardAccess.Get, "RuntimeThreads", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_MODULE = Giskard.access(GiskardAccess.Get, "Module", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_NATIVES = Giskard.access(GiskardAccess.Get, "ModuleNatives", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_PROCESS = Giskard.access(GiskardAccess.Get, "Process", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_LOCALENTITIES = Giskard.access(GiskardAccess.Get, "LocalEntities", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);

	GiskardEntityRef GIS_TYP_DUST_THREAD = Giskard.access(GiskardAccess.Get, "Thread", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_PROCESS = Giskard.access(GiskardAccess.Get, "ThreadProcess", GiskardContext.Absolute, GIS_UNI_DUST_RUNTIME);

}
