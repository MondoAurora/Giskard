package me.giskard.dust.runtime.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;

public class DustMachineNativeConnector implements DustMachineConsts, DustTokensMind, DustTokensGeneric, DustTokensMachine, DustMachineConsts.NativeConnector {
	private Map<MiNDToken, Class<?>> nativeClasses = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		switch ( cmd ) {
		case Add:
			try {
				val = (RetType) nativeClasses.get(target).newInstance();
			} catch (Exception e) {
				GiskardException.wrap(e);
			}
			break;
		case Chk:
			break;
		case Del:
			break;
		case Get:
			break;
		case Set:
			break;
		case Use:
			break;
		default:
			break;
		}
		
		return (RetType) val;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			Giskard.access(MiNDAccessCommand.Use, this, MTMEMBER_ACTION_THIS, MTMEMBER_CONN_PROVIDES);
			break;
		case Process:
			MiNDToken t = Giskard.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_PARAM, MTMEMBER_IMPLEMENTATION_AGENT);
			if ( null != t ) {
				Class<?> c = Giskard.access(MiNDAccessCommand.Set, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VARIANT_VALUE);
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

	@Override
	public String toString() {
		return nativeClasses.toString();
	}
}
