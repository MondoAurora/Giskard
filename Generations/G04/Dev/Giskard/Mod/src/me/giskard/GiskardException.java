package me.giskard;

public final class GiskardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GiskardException(Throwable src) {
		super(src);
	}

	public static void swallow(Throwable src, Object... params) {
		Giskard.log(src, params);
		src.printStackTrace();
	}

	public static <FakeRet> FakeRet wrap(Throwable src, Object... params) {
		if ( src instanceof GiskardException ) {
			throw (GiskardException) src;
		}
	
		Giskard.log(src, params);
		throw new GiskardException(src);
	}
}