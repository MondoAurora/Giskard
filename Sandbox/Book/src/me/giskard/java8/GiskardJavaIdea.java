package me.giskard.java8;

import me.giskard.GiskardUtilsConsts;

public class GiskardJavaIdea extends GiskardJavaConsts.GisJavaCoreAgent implements GiskardJavaConsts, GiskardUtilsConsts {

	GisUtilsFactory<Object, GisJavaEntity> mapAttributes = new GisUtilsFactory<Object, GisJavaEntity>(
			new GisUtilsCreator<Object, GisJavaEntity>() {
				@Override
				public GisJavaEntity create(Object key, Object... params) {
					return gisJava.model.createEntity();
				}
			});

	GisUtilsFactory<Enum<?>, GisJavaEntity> mapEnumToAtt = new GisUtilsFactory<Enum<?>, GisJavaEntity>(
			new GisUtilsCreator<Enum<?>, GisJavaEntity>() {
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
