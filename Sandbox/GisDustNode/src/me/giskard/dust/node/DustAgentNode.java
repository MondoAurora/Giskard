package me.giskard.dust.node;

import java.io.PrintStream;
import java.util.Date;

import me.giskard.Giskard;
import me.giskard.GiskardMain;
import me.giskard.GiskardUtils;

public class DustAgentNode extends GiskardMain implements DustNodeConsts, DustNodeConsts.DustNode {
	
	PrintStream out = System.out;
	
	Object nodeEntity;
	
	DustEntity aEntity;
	DustRuntime aRuntime;

	@Override
	public DustEntity getAgentEntity() {
		return aEntity;
	}

	@Override
	public DustRuntime getAgentRuntime() {
		return aRuntime;
	}

	@Override
	public Object resolve(GiskardEntityRef ref) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <RetType> RetType access_(GiskardAccessCmd cmd, Object val, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <RetType> RetType wrapException_(Throwable exception, GiskardEntityRef exType, Object... params)
			throws GiskardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void broadcastEvent_(GiskardEntityRef eventType, Object... params) {
		out.println(GiskardUtils.sbAppend(null, " ", "Normal event", eventType, "at", new Date(), "params [",
				GiskardUtils.sbAppend(null, ", ", params), "]"));
	}

	@Override
	protected String toString_(GiskardEntityRef ref) {
		return super.toString_(ref);
	}

	@Override
	public void gisAgentProcess() throws GiskardException {
		// TODO Auto-generated method stub
		
	}

	public void optInitRuntime(Giskard current) {
//		Giskard.wrapException(null, null, "Test", "exception");
		
		if ( ! (current instanceof DustAgentNode) ) {
			setRuntime(this);
			
			for ( BootEvent e : BOOT_EVENTS ) {
				broadcastEvent_(e.eventType, e.params);
			}
			
			BOOT_EVENTS.clear();
		}
	}
}
