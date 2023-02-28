package me.giskard.mind;

public final class GiskardException extends RuntimeException implements GiskardConsts {
	private static final long serialVersionUID = 1L;

	public GiskardException(Throwable src) {
		super(src);
	}

	public static void swallow(Throwable src, Object... params) {
		GiskardMind.dump(src, params);
		src.printStackTrace();
	}

	public static <FakeRet> FakeRet wrap(Throwable src, Object... params) {
		if ( src instanceof GiskardException ) {
			throw (GiskardException) src;
		}
	
		GiskardMind.dump(src, params);
		throw new GiskardException(src);
	}
}