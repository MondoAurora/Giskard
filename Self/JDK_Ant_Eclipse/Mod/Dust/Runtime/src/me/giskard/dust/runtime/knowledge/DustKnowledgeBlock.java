package me.giskard.dust.runtime.knowledge;

import java.util.HashMap;
import java.util.Map;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustKnowledgeBlock implements DustKnowledgeConsts {
	private static int NEXT_HANDLE = HANDLE_START;

	private synchronized static int getNextHandle() {
		return NEXT_HANDLE++;
	}

	private Integer handle;

	DustKnowledgeContext ctx;

	final Map<DustToken, Object> localData;

	public DustKnowledgeBlock(DustKnowledgeContext ctx, Integer handle_) {
		this.ctx = ctx;
		localData = new HashMap<>();
		handle = handle_;
	}

	public DustKnowledgeBlock(DustKnowledgeContext ctx) {
		this(ctx, getNextHandle());
	}

	public DustKnowledgeBlock(DustKnowledgeContext ctx, DustKnowledgeBlock source) {
		this.ctx = ctx;
		localData = new HashMap<>(source.localData);
		handle = source.handle;
	}

	public int getHandle() {
		return handle;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DustKnowledgeBlock) ? ((DustKnowledgeBlock) obj).handle == handle : false;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, DustToken token, Object key) {
		if ( null == token ) {
			return (RetType) this;
		}

		try {
			boolean one = token.getCollType() == MiNDCollType.One;
			Object current = localData.get(token);

			if ( (null == current) && !one && GiskardUtils.isAccessCreator(cmd) ) {
				current = DustKnowledgeCollection.create(this, token);
				localData.put(token, current);
			}

			switch ( cmd ) {
			case Get:
				val = (RetType) (((null == current) || one) ? current
						: ((DustKnowledgeCollection) current).access(cmd, val, key));
				break;
			case Set:
				if ( one ) {
					localData.put(token, val);
					val = (RetType) current;
				} else {
					val = (RetType) ((DustKnowledgeCollection) current).access(cmd, val, key);
				}
				break;
			case Add:
				val = (RetType) ((DustKnowledgeCollection) current).access(cmd, val, null);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin, token);

						if ( one ) {
							DustKnowledgeUtils.notifyAgent(agent, ctx, current);
						} else {
							((DustKnowledgeCollection) current).access(cmd, agent, null);
						}
					} finally {
						agent.process(MiNDAgentAction.End, token);
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
