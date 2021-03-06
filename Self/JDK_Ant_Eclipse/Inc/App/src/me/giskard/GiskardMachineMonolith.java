package me.giskard;

import me.giskard.coll.MindCollConsts.MiNDBuilder;

public class GiskardMachineMonolith implements GiskardMachineConsts, GiskardConsts.MiNDMachine {

	protected class MachineModule implements MiNDBuilder<String, Object>, MiNDAccessor {
		MiNDAgent modAgent;
		ClassLoader cl;

		public void setLoader(ClassLoader cl) {
			this.cl = cl;
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

		@Override
		public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	protected MachineModule modMind;
	protected MiNDAgent mainAgent;
	protected NativeConnector nativeConnector;

	protected GiskardMachineMonolith(MiNDAgent mainAgent, String... args) {
		Giskard.log(MiNDEventLevel.TRACE, "GISKARD boot started...");
		this.mainAgent = mainAgent;
	}

	@Override
	public void init() {
		modMind = new MachineModule();
		modMind.setLoader(ClassLoader.getSystemClassLoader());
		modMind.modAgent = mainAgent;
		
		setNativeConnector();
	}
	
	public void setNativeConnector() {
		try {
			this.nativeConnector = (NativeConnector) Class.forName("me.giskard.GiskardNativeConnector").newInstance();
			optLoadNativeConn();
		} catch (Exception e) {
			GiskardException.wrap(e);
		}
	}

	public void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

	@Override
	public Object addModule(String modName, String ver) {
		return GiskardException.wrap(null, "addModule() not supported for monolith machine");
	}
	
	@Override
	public MiNDAgent testCreateAgent(MiNDToken token) throws Exception {
		return nativeConnector.access(MiNDAccessCommand.Add, null, token);
	}

	@Override
	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
		return modMind.access(cmd, val, target, valPath);
	}

	public final void launch() throws Exception {
		Giskard.log(MiNDEventLevel.INFO, "Native assignments:", nativeConnector);
		Giskard.log(MiNDEventLevel.INFO, "GISKARD boot success.");

		modMind.modAgent.process(MiNDAgentAction.Begin);

		Giskard.log(MiNDEventLevel.INFO, "GISKARD finished.");
	}

}
