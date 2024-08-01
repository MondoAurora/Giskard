package me.giskard.dust;

public class Dust implements DustConsts {
	
	private static Machine MACHINE;

	public static abstract class Machine {
		protected Machine() {
			if ( null != MACHINE ) {
				DustException.wrap(null, "Multiple Dust machine initialization");
			}

			MACHINE = this;
		}

		protected abstract <RetType> RetType access(MindAccess cmd, Object val, Object... path);
	}

	public static <RetType> RetType access(MindAccess cmd, Object val, Object... path) {
		return MACHINE.access(cmd, val, path);
	}

	public static void log(MindHandle event, Object... params) {
		access(MindAccess.Broadcast, event, params);
	}

}
