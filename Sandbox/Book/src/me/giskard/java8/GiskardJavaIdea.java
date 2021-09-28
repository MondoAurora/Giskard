package me.giskard.java8;

import me.giskard.GiskardUtils;

public class GiskardJavaIdea extends GiskardJavaConsts.GisJavaCoreAgent implements GiskardJavaConsts {

	GiskardUtils.HashFactory<Object, GisJavaEntity> mapAttributes = new GiskardUtils.HashFactory<Object, GisJavaEntity>(
			new GiskardUtils.Creator<Object, GisJavaEntity>() {
				@Override
				public GisJavaEntity create(Object key, Object... params) {
					return null;
				}
			});

	GiskardUtils.HashFactory<Enum<?>, GisJavaEntity> mapEnumToAtt = new GiskardUtils.HashFactory<Enum<?>, GisJavaEntity>(
			new GiskardUtils.Creator<Enum<?>, GisJavaEntity>() {
				@Override
				public GisJavaEntity create(Enum<?> key, Object... params) {
					return null;
				}
			});

	public GiskardJavaIdea(GiskardJava mind) {
		super(mind);
	}

	@Override
	public void agentProcess() throws Exception {
	}
	

}
