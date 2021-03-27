package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Map;

import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.GiskardConsts.MiNDNamed;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustRuntimeDataBlock implements DustRuntimeConsts, MiNDNamed {
	private static int NEXT_HANDLE = HANDLE_START;

	private synchronized static int getNextHandle() {
		return NEXT_HANDLE++;
	}

	private Integer handle;

	DustRuntimeContext ctx;

	final Map<DustRuntimeToken, Object> localData;

	public DustRuntimeDataBlock(DustRuntimeContext ctx, Integer handle_) {
		this.ctx = ctx;
		localData = new HashMap<>();
		handle = handle_;
		if ( HANDLE_NULL < handle ) {
			localData.put((DustRuntimeToken) MTMEMBER_ENTITY_HANDLE, handle);
		}
	}

	public DustRuntimeDataBlock(DustRuntimeContext ctx) {
		this(ctx, getNextHandle());
	}

	public DustRuntimeDataBlock(DustRuntimeContext ctx, DustRuntimeDataBlock source) {
		this.ctx = ctx;
		localData = new HashMap<>(source.localData);
		handle = source.handle;
	}

	public int getHandle() {
		return handle;
	}
	
	@Override
	public String getMiNDName() {
		Object ret = localData.get(MTMEMBER_PLAIN_STRING);
		
		if ( null == ret ) {
			ret = localData.get(MTMEMBER_ENTITY_STOREID);
		}
		
		if ( null == ret ) {
			ret = localData.get(MTMEMBER_ENTITY_HANDLE);
		}
		
		return GiskardUtils.toString(ret);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DustRuntimeDataBlock) ? ((DustRuntimeDataBlock) obj).handle == handle : false;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken mt, Object key) {
		if ( null == mt ) {
			return (RetType) this;
		}

		try {
			DustRuntimeToken token = (DustRuntimeToken) mt;
			boolean one = token.getCollType() == MiNDCollType.One;
			Object current = localData.get(token);

			if ( (null == current) && !one && GiskardUtils.isAccessCreator(cmd) ) {
				current = DustRuntimeValueCollection.create(this, token);
				localData.put(token, current);
			}

			switch ( cmd ) {
			case Get:
				val = (RetType) (((null == current) || one) ? current
						: ((DustRuntimeValueCollection) current).access(cmd, val, key));
				break;
			case Set:
				if ( one ) {
					localData.put(token, val);
					val = (RetType) current;
				} else {
					val = (RetType) ((DustRuntimeValueCollection) current).access(cmd, val, key);
				}
				break;
			case Add:
				val = (RetType) ((DustRuntimeValueCollection) current).access(cmd, val, key);
				break;
			case Use:
				if ( null != current ) {
					MiNDAgent agent = (MiNDAgent) val;
					try {
						agent.process(MiNDAgentAction.Begin);

						if ( one ) {
							DustRuntimeUtils.notifyAgent(agent, ctx, current);
						} else {
							((DustRuntimeValueCollection) current).access(cmd, agent, null);
						}
					} finally {
						agent.process(MiNDAgentAction.End);
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
