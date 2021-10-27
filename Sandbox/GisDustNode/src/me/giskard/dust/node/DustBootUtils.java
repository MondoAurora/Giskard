package me.giskard.dust.node;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

@SuppressWarnings("rawtypes")
public class DustBootUtils implements DustBootConsts {
	public static <RetType> RetType createAgent(DustEntityRef refType) {
		String className = Giskard.access(GiskardAccessCmd.Peek, null, GiskardContext.Module, ATT_NATIVEMAP, refType,
				ATT_TXT_ID);

		return GiskardUtils.instantiate(className);
	}

	public static boolean isUnit(Enum eKey) {
		return eKey.name().startsWith("Unit");
	}

	public static String toString(DustEntityRef ref) {
		Object unitId = ref.getUnit().getID();
		String unitName = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, ATT_UNITMAP, unitId, ATT_TXT_ID);
		String name = Giskard.access(GiskardAccessCmd.Get, "??", GiskardContext.Module, ATT_UNITMAP, unitId, ATT_ENTITYMAP,
				ref.getID(), ATT_TXT_ID);
		return "\"" + unitName + "->" + name + "\"";
	}
}
