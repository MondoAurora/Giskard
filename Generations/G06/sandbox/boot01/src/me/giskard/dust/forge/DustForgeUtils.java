package me.giskard.dust.forge;

import java.lang.reflect.Field;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.machine.DustMachineBootConsts;
import me.giskard.dust.machine.DustMachineConsts;
import me.giskard.dust.machine.DustMachineConstsInt.DustHandle;
import me.giskard.dust.machine.DustMachineUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings("rawtypes")
public class DustForgeUtils implements DustForgeConsts, DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	public static MindHandle loadTokens(Class handleContainer, Map machineIdea) throws Exception {
		String cn = handleContainer.getName();

		MindHandle classTag = Dust.lookup(FORGE, "JavaClass$" + cn);
		Dust.access(MIND_TAG_ACCESS_SET, cn, classTag, JAVA_LOGIC_CLASSNAME);

		for (Field f : handleContainer.getFields()) {
			Object o = f.get(null);
			if (o instanceof DustHandle) {
				if ((RUNTIME_MACHINE == o) || (RUNTIME_MAINDIALOG == o)) {
					continue;
				}

				String n = f.getName();
				DustHandle h = (DustHandle) o;

				DustMachineUtils.storeToken(h, machineIdea, LANG_ID, n);
				Dust.access(MIND_TAG_ACCESS_SET, classTag, h, MIND_IDEA_TAGS, FORGE_TAG_CLASS);
			}
		}

		return classTag;
	}

}