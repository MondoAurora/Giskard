package me.giskard.utils;

import me.giskard.Mind;

public class MindUtils extends MindUtilsJava {

	public static final MiNDAgent LOGGER = new MiNDAgent() {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			Mind.log(MiNDEventLevel.INFO, action,  MindUtils.sbAppend(null, ",", true, params));
			return MiNDResultType.ACCEPT_READ;
		}
	};
	
	public static String getRoot() {
		String root = System.getenv("GISKARD");
		return isEmpty(root) ? System.getenv("GISKARD_ECLIPSE") : root;
	}
}
