package me.giskard.coll;

import me.giskard.GiskardConsts.MiNDCreator;
import me.giskard.utils.MiNDUtilsCreatorSimple;

public class MindCollFactory<Key, Val> extends MindCollMap<Key, Val> {
	MiNDCreator<Key, ? extends Val> creator;
	
	public MindCollFactory(boolean sorted) {
		super(sorted);
	}
	
	public MindCollFactory(boolean sorted, MiNDCreator<Key, ? extends Val> creator) {
		this(sorted);
		setCreator(creator);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MindCollFactory(boolean sorted, Class<? extends Val> vc) {
		this(sorted, new MiNDUtilsCreatorSimple(vc));
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