package me.giskard.utils;

import me.giskard.GiskardConsts.MiNDCreator;

public class MiNDUtilsCreatorSimple<Key, Val> implements MiNDCreator<Key, Val> {
	Class<Val> cVal;

	public MiNDUtilsCreatorSimple(Class<Val> cVal) {
		this.cVal = cVal;
	}

	@Override
	public Val create(Key key) {
		try {
			return cVal.newInstance();
		} catch (Throwable e) {
			return MindUtils.wrapException(e);
		}
	}
}