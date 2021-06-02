package me.giskard.dust.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.giskard.GiskardConsts.MiNDNamed;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustRuntimeDataBlock implements DustRuntimeConsts, MiNDNamed {
	private static int NEXT_HANDLE = HANDLE_START;

	private synchronized static int getNextHandle() {
		return NEXT_HANDLE++;
	}

	private Integer handle;

	DustRuntimeDataContext ctx;

	private final Map<Integer, Object> localData;

	public DustRuntimeDataBlock(DustRuntimeDataContext ctx) {
		this.ctx = ctx;
		localData = new HashMap<>();
		handle = getNextHandle();
	}

	public DustRuntimeDataBlock(DustRuntimeDataContext ctx, DustRuntimeDataBlock source) {
		this.ctx = ctx;
		ctx.getTokenManager();
		localData = new HashMap<>(source.localData);
		handle = source.handle;
	}

	public int getHandle() {
		return handle;
	}
	
	public Iterator<Integer> getKeyIter() {
		return localData.keySet().iterator();
	}
	
	void setValue(MiNDToken t, Object o) {
		localData.put( ((DustRuntimeToken) t).getEntityHandle(ctx.getTokenManager()), o);
	}
	
	Object getValue(MiNDToken t) {
		return localData.get( ((DustRuntimeToken) t).getEntityHandle(ctx.getTokenManager()));
	}
	
	@Override
	public String getMiNDName() {
		Object ret = getValue(MTMEM_TEXT_PLAINTEXT_STRING);
		
		if ( null == ret ) {
			ret = getValue(MTMEM_MODEL_ENTITY_IDGLOBAL);
		}
		
		if ( null == ret ) {
			ret = getValue(MTMEM_MODEL_ENTITY_HANDLE);
		}
		
		return GiskardUtils.toString(ret);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof DustRuntimeDataBlock) ? ((DustRuntimeDataBlock) obj).handle == handle : false;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, RetType val, MiNDToken mt, Object key) {
		return access(NULL_NOTIF, cmd, val, mt, key);
	}

	public <RetType> RetType access(DustNotifier notif, MiNDAccessCommand cmd, RetType val, MiNDToken mt, Object key) {
		if ( null == mt ) {
			return (RetType) this;
		} else if ( MTMEM_MODEL_ENTITY_HANDLE == mt ) {
			return (RetType) handle;
		}

		try {
			DustRuntimeToken token = (DustRuntimeToken) mt;
			boolean one = token.getCollType() == MiNDCollType.One;
			Object current = getValue(token);

			if ( (null == current) && !one && GiskardUtils.isAccessCreator(cmd) ) {
				current = DustRuntimeDataCollection.create(this, token);
				setValue(token, current);
			}

			switch ( cmd ) {
			case Get:
				val = (RetType) (((null == current) || one) ? current
						: ((DustRuntimeDataCollection) current).access(notif, cmd, val, key));
				break;
			case Set:
				if ( one ) {
					if ( !GiskardUtils.isEqual(current, val) ) {
						setValue(token, val);
// call loop
//						notif.notify(cmd, handle, current, val, token, key);
					}
					val = (RetType) current;
				} else {
					val = (RetType) ((DustRuntimeDataCollection) current).access(notif, cmd, val, key);
				}
				break;
			case Add:
				val = (RetType) ((DustRuntimeDataCollection) current).access(notif, cmd, val, key);
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
