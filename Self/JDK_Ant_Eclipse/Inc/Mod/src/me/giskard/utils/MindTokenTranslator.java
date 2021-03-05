package me.giskard.utils;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

@SuppressWarnings("unchecked")
public class MindTokenTranslator implements GiskardConsts {
	private static MindUtilsTranslator<MiNDToken, Class<Enum<?>>> TOKEN_ENUM_CLASS = new MindUtilsTranslator<>();
	private static MindUtilsTranslator<MiNDToken, Enum<?>> TOKEN_ENUM = new MindUtilsTranslator<>();
	
	private static MiNDToken TOKEN_MEMBER;
	
	public static void setTokenMember(MiNDToken tm) {
		TOKEN_MEMBER = tm;
	}

	public static void register(MiNDToken t, Object ob) {
		System.out.println("Registering token " + t + " with " + ob);
		
		if ( ob instanceof Class ) {
			TOKEN_ENUM_CLASS.add(t, (Class<Enum<?>>) ob);
		} else {
			TOKEN_ENUM.add(t, (Enum<?>) ob);
		}
	}

	public static void setEnumToken(MiNDToken target, Enum<?> e) {
		if ( null != e ) {
			MiNDToken token = TOKEN_ENUM.getLeft(e);
//			Mind.access(MiNDAccessCommand.Set, token, target, TOKEN_ENUM_CLASS.getLeft((Class<Enum<?>>) e.getClass()));
			Giskard.access(MiNDAccessCommand.Set, token, target, TOKEN_MEMBER);
		}
	}

	public static MiNDToken toToken(Enum<?> e) {
		return TOKEN_ENUM.getLeft(e);
	}
}
