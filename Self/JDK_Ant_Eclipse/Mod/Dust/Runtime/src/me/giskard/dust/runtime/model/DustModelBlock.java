package me.giskard.dust.runtime.model;

import java.util.Set;

import me.giskard.GiskardException;
import me.giskard.coll.MindCollMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustModelBlock implements DustModelConsts {
	DustModelContext ctx;
	DustModelBlock orig;
	Set<DustModelRef> incomingRefs;

	MiNDCollMap<DustTokenMember, Object> localData = new MindCollMap<DustTokenMember, Object>(false);

	public DustModelBlock(DustModelContext ctx) {
		this.ctx = ctx;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustTokenMember tMember) {
		try {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

			Object current = localData.get(tMember);

			DustModelBlock tBlock = (val instanceof DustToken) ? ctx.entityBlocks.peek((DustToken) val) : null;
			Object data = (null == tBlock) ? val : tBlock;
			
			switch ( cmd ) {
			case Set:
				if ( null != tBlock ) {
					data = ctx.setRef(this, tMember, tBlock);
				}
				localData.put(tMember, data);
				val = (RetType) current;
				break;
			case Add:
				if ( null == current ) {
					current = DustModelMultiVal.create(tMember);
					localData.put(tMember, current);
				}
				val = (RetType) ((DustModelMultiVal) current).access(this, tMember, cmd, data);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin, tMember);

						if ( current instanceof DustModelMultiVal ) {
							((DustModelMultiVal) current).access(this, tMember, cmd, agent);
						} else {
							DustModelUtils.notifyAgent(agent, ctx, current);
						}
					} finally {
						agent.process(MiNDAgentAction.End, tMember);
					}
				}
				break;
			default:
//			Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);
				break;
			}
			return val;
		} catch (Exception e) {
			return GiskardException.wrap(e);
		}
	}

	@Override
	public String toString() {
		return localData.toString();
	}
}
