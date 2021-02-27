package me.giskard.dust.io;

import me.giskard.MindConsts;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMind;

public interface DustIOConsts extends MindConsts, DustTokensMind, DustTokensGeneric {
//	enum SerializeItem {
//		Key, Map, Array, Value
//	}
	
	public interface CodepointVisitor {
		boolean process(int cp);
	}
}
