package me.giskard.dust.runtime;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts;

public class DustRuntimeGiskard extends Giskard
		implements GisCollConsts, DustRuntimeBootConsts, DustRuntimeBootConsts.DustGiskard {

	DustRuntimeMachine machine;

	DustRuntimeLog log;
	Map<String, DustRuntimeToken> bootTokens = new TreeMap<>();

	public DustRuntimeGiskard() {
		log = new DustRuntimeLog();
		setImplementation(this);
		
		machine = new DustRuntimeMachine();
		
		DustRuntimeTokenManager tm = machine.getContext().getTokenManager();
		for ( Map.Entry<String, DustRuntimeToken> e : bootTokens.entrySet() ) {
			tm.registerToken(e.getKey(), e.getValue());
		}
	}

	@Override
	public void init(MiNDAgent agent) throws Exception {
		machine.init(agent);
	}

	@Override
	protected MiNDToken defineToken_(MiNDTokenType type, Object id, Object... params) {
		String name = GiskardUtils.toString(id);
		
		if ( null != machine ) {
			return machine.getContext().getTokenManager().defineToken(type, name, params);
		} else {
			String tokenId = DustRuntimeToken.buildId(type, name, params);
			DustRuntimeToken ret = bootTokens.get(id);

			if ( null == ret ) {
				ret = DustRuntimeToken.createToken(type, name, params);
				bootTokens.put(tokenId, ret);
			}

			return ret;
		}
	}

	@Override
	protected <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return machine.access(cmd, val, valPath);
	}

	@Override
	protected void log_(MiNDEventLevel lvl, Object... obs) {
		log.log(lvl, obs);
	}

	@Override
	public String toString() {
		return machine.getContext().toString();
	}
}
