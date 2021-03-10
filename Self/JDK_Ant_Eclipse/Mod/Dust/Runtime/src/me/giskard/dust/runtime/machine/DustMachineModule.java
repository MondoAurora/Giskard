package me.giskard.dust.runtime.machine;

import me.giskard.GiskardConsts.MiNDAgent;
import me.giskard.GiskardConsts.MiNDAgentAction;
import me.giskard.GiskardException;
import me.giskard.coll.MindCollConsts.MiNDBuilder;
import me.giskard.tools.GisToolsModuleServices;

class DustMachineModule implements MiNDBuilder<String, Object> {
	MiNDAgent modAgent;
	ClassLoader cl;

	DustMachineModule(String mod, ClassLoader cl) throws Exception {
		this.cl = cl;
		modAgent = GisToolsModuleServices.initModule(cl, mod);
	}

	DustMachineModule(String mod, MiNDAgent agent) throws Exception {
		this.cl = agent.getClass().getClassLoader();
		modAgent = agent;
	}

	@Override
	public Object create(String key) {
		try {
			Class<?> c = cl.loadClass(key);
			return c.newInstance();
		} catch (Exception e) {
			return GiskardException.wrap(e);
		}
	}

	@Override
	public void release(String key, Object v) {
		if ( v instanceof MiNDAgent ) {
			try {
				((MiNDAgent) v).process(MiNDAgentAction.Release);
			} catch (Exception e) {
				GiskardException.wrap(e);
			}
		}
	}
}