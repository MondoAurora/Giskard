package me.giskard.tools;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

@SuppressWarnings("unchecked")
public class GisToolsTokenTranslator implements GiskardConsts {
	private static GisToolsTranslator<MiNDToken, Class<Enum<?>>> TOKEN_ENUM_CLASS = new GisToolsTranslator<>();
	private static GisToolsTranslator<MiNDToken, Enum<?>> TOKEN_ENUM = new GisToolsTranslator<>();
	
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
			Giskard.access(MiNDAccessCommand.Set, token, target);
		}
	}

	public static MiNDToken toToken(Enum<?> e) {
		return TOKEN_ENUM.getLeft(e);
	}
	public static Enum<?> toEnum(MiNDToken t) {
		return TOKEN_ENUM.getRight(t);
	}
}
