package me.giskard;

public class Giskard implements GiskardConsts {
	
	public static abstract class GiskardImplementation implements GiskardAgent {
		protected abstract void init(String[] args) throws Exception;
		
		protected abstract <RetType> RetType get(GiskardGetMode mode, RetType defValue, Object... path);
		protected abstract <RetType> RetType set(GiskardSetMode mode, RetType value, Object... path);
		protected abstract boolean del(Object... path);
		protected abstract void visit(GiskardVisitMode mode, GiskardEntity visitor, Object... path);
		
		protected abstract void log(GiskardLogLevel level, Object... params);
		protected abstract <RetType> RetType wrapException(Throwable orig, Object... params);
	}

	private static GiskardImplementation GIS_IMPL;

	public static void main(String[] args) throws Exception {
		String mc = "me.giskard.java8.GiskardJava";

		GIS_IMPL = (GiskardImplementation) Class.forName(mc).newInstance();
		
		GIS_IMPL.init(args);
	}

	public static <RetType> RetType get(GiskardGetMode mode, RetType defValue, Object... path) {
		return GIS_IMPL.get(mode, defValue, path);
	}

	public static <RetType> RetType set(GiskardSetMode mode, RetType value, Object... path) {
		return GIS_IMPL.set(mode, value, path);
	}

	public static boolean del(Object... path) {
		return GIS_IMPL.del(path);
	}

	public static void visit(GiskardVisitMode mode, GiskardEntity visitor, Object... path) {
		GIS_IMPL.visit(mode, visitor, path);
	}

	public static <RetType> RetType wrapException(Throwable orig, Object... params) {
		return GIS_IMPL.wrapException(orig, params);
	}

	public static void log(GiskardLogLevel level, Object... params) {
		GIS_IMPL.log(level, params);
	}


}
