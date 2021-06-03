package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.tools.GisToolsTokenTranslator;
import me.giskard.tools.GisToolsTranslator;

public class DustTokens implements DustTokensMind, DustTokensMachine, DustTokensGeneric, DustTokensText {

	public static void addModule(String mod, String ver) throws Exception {
		Object hMod = Giskard.access(MiNDAccessCommand.Get, MTTYP_MACHINE_MODULE);
		Giskard.access(MiNDAccessCommand.Set, hMod, MTMEM_GENERIC_ACTION_THIS);
		Giskard.access(MiNDAccessCommand.Set, mod, hMod, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Set, ver, hMod, MTMEM_GENERIC_VERSIONED_SIGNATURE);
		Giskard.access(MiNDAccessCommand.Add, hMod, MTMEM_MACHINE_MACHINE_INSTANCE, MTMEM_MACHINE_MACHINE_MODULES);		
	}

	public static void addModuleImpInfo(MiNDToken agent, Class<?> c) throws Exception {
		Giskard.log(MiNDEventLevel.Trace, "Registering implementation for agent", agent, "->", c.getName());

		Object hNat = Giskard.access(MiNDAccessCommand.Get, MTTYP_MACHINE_NATIVE);
		Giskard.access(MiNDAccessCommand.Set, c, hNat, MTMEM_GENERIC_VALUE_RAW);
		Giskard.access(MiNDAccessCommand.Add, hNat, MTMEM_GENERIC_ACTION_THIS, MTMEM_MACHINE_MODULE_NATIVES, agent);
	}
	
	public static final GisToolsTranslator<MiNDValType, MiNDToken> TRANSLATE_VAL_TOKEN = new GisToolsTranslator<>();
	
	static {
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Int, MTMEM_GENERIC_VALUE_INT);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Real, MTMEM_GENERIC_VALUE_REAL);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Raw, MTMEM_GENERIC_VALUE_RAW);
		TRANSLATE_VAL_TOKEN.add(MiNDValType.Link, MTMEM_GENERIC_VALUE_LINK);
	}
	
	public static Object getValue(MiNDToken target, MiNDToken tok) {
		return Giskard.access(MiNDAccessCommand.Set, null, target, DustTokens.TRANSLATE_VAL_TOKEN.getRight(tok.getValType()));
	}
	
	public static Object getKey(MiNDToken target, MiNDToken tok) {
		MiNDToken tk = null;
		switch ( tok.getCollType() ) {
		case Arr:
		case Set:
			tk = MTMEM_DIALOG_VISITINFO_KEYARR;
			break;
		case Map:
			tk = MTMEM_DIALOG_VISITINFO_KEYMAP;
			break;
		default:
			break;				
		}
		
		return ( null == tk ) ? null : Giskard.access(MiNDAccessCommand.Get, null, target, tk);
	}
	
	public static void setValue(MiNDToken target, MiNDToken tok, Object val, Object key) {
		Giskard.access(MiNDAccessCommand.Set, tok.getEntity(), target, MTMEM_DIALOG_VISITINFO_TOKEN);
		Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(tok.getValType()), target, MTMEM_GENERIC_VALUE_TYPE);
		Giskard.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(tok.getCollType()), target, MTMEM_GENERIC_VALUE_COLLTYPE);

		Giskard.access(MiNDAccessCommand.Set, null, target, MTMEM_GENERIC_VALUE_INT);
		Giskard.access(MiNDAccessCommand.Set, null, target, MTMEM_GENERIC_VALUE_REAL);
		Giskard.access(MiNDAccessCommand.Set, null, target, MTMEM_GENERIC_VALUE_RAW);
		Giskard.access(MiNDAccessCommand.Set, null, target, MTMEM_GENERIC_VALUE_LINK);
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
		
		Giskard.access(MiNDAccessCommand.Set, keyArr, target, MTMEM_DIALOG_VISITINFO_KEYARR);
		Giskard.access(MiNDAccessCommand.Set, keyMap, target, MTMEM_DIALOG_VISITINFO_KEYMAP);
	}

	public static void setupValFormatter(Object target) {
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_LINK_ONE, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_DIALOG_VISITINFO_TOKEN, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_VALUE_TYPE, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_VALUE_INT, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_VALUE_REAL, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_VALUE_RAW, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_GENERIC_VALUE_LINK, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_DIALOG_VISITINFO_KEYARR, target, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, MTMEM_DIALOG_VISITINFO_KEYMAP, target, MTMEM_GENERIC_LINK_ARR);

		Giskard.access(MiNDAccessCommand.Set,
				"Entity: {0}, Token: {1}, Value type: {2}, Value(Int/Real/Raw/Link): ({3}, {4}, {5}, {6}), Key(Arr/Map): ({7}, {8})",
				target, MTMEM_TEXT_PLAINTEXT_STRING);
	}
	
}
