package me.giskard;

import java.util.HashMap;
import java.util.Map;

import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.utils.MindUtils;

public class GiskardNativeConnector implements GiskardMachineConsts, DustTokensMind, DustTokensGeneric, DustTokensMachine, GiskardMachineConsts.NativeConnector {
	private Map<MiNDToken, Class<?>> nativeClasses = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken target, Object... valPath) {
		switch ( cmd ) {
		case Add:
			break;
		case Chk:
			break;
		case Del:
			break;
		case Get:
			try {
				val = (RetType) nativeClasses.get(target).newInstance();
			} catch (Exception e) {
				MindUtils.wrapException(e);
			}
			break;
		case Set:
			break;
		case Use:
			break;
		default:
			break;
		
		}
		return val;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			Mind.access(MiNDAccessCommand.Use, this, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);
			break;
		case Process:
			MiNDToken t = Mind.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_PARAM, MTMEMBER_IMPLEMENTATION_AGENT);
			if ( null != t ) {
				Class<?> c = Mind.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
				if ( null != c ) {
					nativeClasses.put(t, c);
				}
			}
			break;
		case Release:
			break;
		}
		return MiNDResultType.ACCEPT;
	}

}
