package me.giskard;

import me.giskard.GiskardConsts.MiNDEventLevel;

public final class GiskardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GiskardException(Throwable src) {
		super(src);
	}

	public static void swallow(Throwable src, Object... params) {
		Giskard.log(MiNDEventLevel.WARNING, src, params);
	}

	public static <FakeRet> FakeRet wrap(Throwable src, Object... params) {
		if ( src instanceof GiskardException ) {
			throw (GiskardException) src;
		}
	
		Giskard.log(MiNDEventLevel.CRITICAL, src, params);
		throw new GiskardException(src);
	}
}