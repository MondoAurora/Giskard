package me.giskard.java8;

public class GiskardJavaUtils implements GiskardJavaConsts {
	
	public static String enumToId(Enum<?> eUnit, Enum<?> e) {
		StringBuilder sb = new StringBuilder(eUnit.name()).append(SEP_TOKEN).append(e.getClass().getSimpleName()).append(SEP_TOKEN).append(e.name());
		return sb.toString();
	}

}
