package me.giskard.utils;

import me.giskard.MindConsts;

public class MindTokenTranslator implements MindConsts {	
	private static MindUtilsTranslator<MiNDToken, Class<Enum<?>>> TOKEN_ENUM_CLASS = new MindUtilsTranslator<>();
	private static MindUtilsTranslator<MiNDToken, Enum<?>> TOKEN_ENUM = new MindUtilsTranslator<>();
	
	public static void setTokenClass(MiNDToken t, Class<Enum<?>> c) {
		TOKEN_ENUM_CLASS.add(t, c);
	}
	
	public static void setTokenEnum(MiNDToken t, Enum<?> e) {
		TOKEN_ENUM.add(t, e);
	}
}
