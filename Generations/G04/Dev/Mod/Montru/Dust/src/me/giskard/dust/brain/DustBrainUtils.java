package me.giskard.dust.brain;

public class DustBrainUtils implements DustBrainConsts {

	public static CollType guessCollType(MiNDAccessCommand cmd, Object key) {
		return (null == key)
				? (cmd == MiNDAccessCommand.Set) ? CollType.One : (cmd == MiNDAccessCommand.Insert) ? CollType.Set : null
				: (key instanceof Number) ? CollType.Arr : CollType.Map;
	}

	public static ValType guessValType(Object val) {
		if ( null == val ) {
			return null;
		} else if ( val instanceof Number ) {
			return Long.class.isAssignableFrom(val.getClass()) ? ValType.Int : ValType.Real;
		} else {
			return (val instanceof MiNDHandle) ? ValType.Link : ValType.Raw;
		}
	}

}
