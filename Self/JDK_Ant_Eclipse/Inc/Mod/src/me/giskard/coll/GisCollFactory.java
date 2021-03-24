package me.giskard.coll;

import me.giskard.coll.GisCollConsts.CreatorSimple;
import me.giskard.coll.GisCollConsts.MiNDCreator;

public class GisCollFactory<Key, Val> extends GisCollMap<Key, Val> {
	MiNDCreator<Key, ? extends Val> creator;
	
	public GisCollFactory(boolean sorted) {
		super(sorted);
	}
	
	public GisCollFactory(boolean sorted, MiNDCreator<Key, ? extends Val> creator) {
		this(sorted);
		setCreator(creator);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GisCollFactory(boolean sorted, Class<? extends Val> vc) {
		this(sorted, new CreatorSimple(vc));
	}
	
	public void setCreator(MiNDCreator<Key, ? extends Val> creator) {
		this.creator = creator;
	}
	
	public synchronized Val peek(Key key) {
		return super.get(key);
	}
	
	public synchronized Val get(Key key) {
		Val ret = super.get(key);
		if ( null == ret ) {
			ret = creator.create(key);
			map.put(key, ret);
		}
		return ret;
	}
}