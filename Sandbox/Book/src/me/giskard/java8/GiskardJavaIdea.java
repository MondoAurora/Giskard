package me.giskard.java8;

import me.giskard.GiskardUtils;

public class GiskardJavaIdea extends GiskardJavaConsts.GisJavaCoreAgent implements GiskardJavaConsts {

	GiskardUtils.HashFactory<Object, GisJavaEntity> mapAttributes = new GiskardUtils.HashFactory<Object, GisJavaEntity>(
			new GiskardUtils.Creator<Object, GisJavaEntity>() {
				@Override
				public GisJavaEntity create(Object key, Object... params) {
					return gisJava.model.createEntity();
				}
			});

	GiskardUtils.HashFactory<Enum<?>, GisJavaEntity> mapEnumToAtt = new GiskardUtils.HashFactory<Enum<?>, GisJavaEntity>(
			new GiskardUtils.Creator<Enum<?>, GisJavaEntity>() {
				@Override
				public GisJavaEntity create(Enum<?> key, Object... params) {
					String id = GiskardJavaUtils.enumToId((Enum<?>) params[0], key);
					return mapAttributes.get(id);
				}
			});

	public GiskardJavaIdea(GiskardJava mind) {
		super(mind);
	}
	
	public GisJavaEntity registerEnum(Enum<?> unit, Enum<?> key) {
		return mapEnumToAtt.get(key, unit);
	}

	public GisJavaEntity resolveEnum(Enum<?> key) {
		return mapEnumToAtt.peek(key);
	}

	@Override
	public void agentProcess() throws Exception {
	}
	

}
