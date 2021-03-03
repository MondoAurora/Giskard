package me.giskard.dust.runtime.model;

import java.util.Set;

import me.giskard.coll.MindCollMap;
import me.giskard.utils.MindUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustModelBlock implements DustModelConsts {
	DustModelContext ctx;
	DustModelBlock orig;
	Set<DustModelRef> incomingRefs;

	MiNDCollMap<DustTokenMember, Object> localData = new MindCollMap<DustTokenMember, Object>(false);

	public DustModelBlock(DustModelContext ctx) {
		this.ctx = ctx;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, Object... valPath) {
		try {
//		Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);

			DustTokenMember t = (DustTokenMember) valPath[0];
			Object current = localData.get(t);

			DustModelBlock tBlock = (val instanceof DustToken) ? ctx.entityBlocks.peek((DustToken) val) : null;
			Object data = (null == tBlock) ? val : tBlock;
			
			switch ( cmd ) {
			case Set:
				if ( null != tBlock ) {
					data = ctx.setRef(this, t, tBlock);
				}
				localData.put(t, data);
				val = (RetType) current;
				break;
			case Add:
				if ( null == current ) {
					current = DustModelMultiVal.create(t);
					localData.put(t, current);
				}
				val = (RetType) ((DustModelMultiVal) current).access(this, t, cmd, data, valPath);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin, t);

						if ( current instanceof DustModelMultiVal ) {
							((DustModelMultiVal) current).access(this, t, cmd, agent);
						} else {
							DustModelUtils.notifyAgent(agent, ctx, current);
						}
					} finally {
						agent.process(MiNDAgentAction.End, t);
					}
				}
				break;
			default:
//			Mind.log(MiNDEventLevel.TRACE, cmd, val, valPath);
				break;
			}
			return val;
		} catch (Exception e) {
			return MindUtils.wrapException(e);
		}
	}

	@Override
	public String toString() {
		return localData.toString();
	}
}
