package ai.montru.tokens;

import ai.montru.giskard.Giskard;
import ai.montru.giskard.GiskardConsts;

public interface MontruDustTokens extends GiskardConsts {
	GiskardEntityRef GIS_UNI_DUST_REGISTRY = Giskard.access(GiskardAccessCmd.Get, "Montru/Dust/Registry/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_UNI_DUST_DEVGEN = Giskard.access(GiskardAccessCmd.Get, "Montru/Dust/DevGen/1/0", GiskardContext.ById);
	GiskardEntityRef GIS_UNI_DUST_DEVJAVA = Giskard.access(GiskardAccessCmd.Get, "Montru/Dust/DevJava/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_UNI_DUST_RUNTIME = Giskard.access(GiskardAccessCmd.Get, "Montru/Dust/Runtime/1/0", GiskardContext.ById);

	GiskardEntityRef GIS_TYP_DUST_NODE = Giskard.access(GiskardAccessCmd.Get, "Node", GiskardContext.ById, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_TYP_DUST_RUNTIME = Giskard.access(GiskardAccessCmd.Get, "Runtime", GiskardContext.ById, GIS_UNI_DUST_RUNTIME);
	GiskardEntityRef GIS_ATT_DUST_NATIVES = Giskard.access(GiskardAccessCmd.Get, "Natives", GiskardContext.ById, GIS_UNI_DUST_RUNTIME);

}
