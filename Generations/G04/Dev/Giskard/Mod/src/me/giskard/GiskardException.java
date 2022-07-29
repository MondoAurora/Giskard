package me.giskard;

import me.giskard.GiskardConsts.MiNDAccessCommand;

public final class GiskardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GiskardException(Throwable src) {
		super(src);
	}

	public static void swallow(Throwable src, Object... params) {
		Giskard.access(MiNDAccessCommand.Broadcast, src, params);
		src.printStackTrace();
	}

	public static <FakeRet> FakeRet wrap(Throwable src, Object... params) {
		if ( src instanceof GiskardException ) {
			throw (GiskardException) src;
		}
	
		Giskard.access(MiNDAccessCommand.Broadcast, src, params);
		throw new GiskardException(src);
	}
}