package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.tools.GisToolsTokenTranslator;
import me.giskard.tools.GisToolsTranslator;

public class DustTokens implements DustTokensMind, DustTokensMachine, DustTokensGeneric, DustTokensText {

	public static void addModule(String mod, String ver) throws Exception {
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, MTTYPE_MODULE, MTMEMBER_ACTION_THIS, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, mod, MTMEMBER_ACTION_THIS, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Set, ver, MTMEMBER_ACTION_THIS, MTMEMBER_VERSIONED_SIGNATURE);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_THIS, MTSHARED_MACHINE, MTMEMBER_MACHINE_MODULES);
	}

	public static void addModuleImpInfo(MiNDToken agent, Class<?> c) throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Registering implementation for agent", agent, "->", c.getName());

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_GPR01, MTMEMBER_VALUE_RAW);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_THIS, MTMEMBER_MODULE_NATIVES, agent);
	}
	
	public static void createAgent(MiNDToken target, MiNDToken agent) {
		Giskard.access(MiNDAccessCommand.Get, target);
		Giskard.access(MiNDAccessCommand.Set, agent, target, MTMEMBER_ENTITY_PRIMARYTYPE);
	}
	
	public static final GisToolsTranslator<MiNDValType, MiNDToken> TRANSLATE_VAL_TOKEN = new GisToolsTranslator<>();
	
	static {
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Int, MTMEMBER_VALUE_INT);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Real, MTMEMBER_VALUE_REAL);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Raw, MTMEMBER_VALUE_RAW);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Link, MTMEMBER_VALUE_LINK);
	}
	
	public static Object getValue(MiNDToken target, MiNDToken tok) {
		return Giskard.access(MiNDAccessCommand.Set, null, target, DustTokens.TRANSLATE_VAL_TOKEN.getRight(tok.getValType()));
	}
	
	public static Object getKey(MiNDToken target, MiNDToken tok) {
		MiNDToken tk = null;
		switch ( tok.getCollType() ) {
		case Arr:
		case Set:
			tk = MTMEMBER_VISITINFO_KEYARR;
			break;
		case Map:
			tk = MTMEMBER_VISITINFO_KEYMAP;
			break;
		default:
			break;				
		}
		
		return ( null == tk ) ? null : Giskard.access(MiNDAccessCommand.Get, null, target, tk);
	}
	
	public static void setValue(MiNDToken target, MiNDToken tok, Object val, Object key) {
		Giskard.access(MiNDAccessCommand.Set, tok, target, MTMEMBER_VISITINFO_TOKEN);
		Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(tok.getValType()), target, MTMEMBER_VALUE_TYPE);
		Giskard.access(MiNDAccessCommand.Set, val, target, DustTokens.TRANSLATE_VAL_TOKEN.getRight(tok.getValType()));

		Object keyArr = null;
		Object keyMap = null;
		switch ( tok.getCollType() ) {
		case Arr:
		case Set:
			keyArr = key;
			break;
		case Map:
			keyMap = key;
			break;
		default:
			break;				
		}
		
		Giskard.access(MiNDAccessCommand.Set, keyArr, target, MTMEMBER_VISITINFO_KEYARR);
		Giskard.access(MiNDAccessCommand.Set, keyMap, target, MTMEMBER_VISITINFO_KEYMAP);
	}
	

}
