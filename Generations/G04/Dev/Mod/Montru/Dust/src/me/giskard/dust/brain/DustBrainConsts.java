package me.giskard.dust.brain;

import me.giskard.dust.DustConsts;

public interface DustBrainConsts extends DustConsts {

	enum CollType {
		One, Arr, Map, Set, // Stack, Queue, Pool?
	}
	
	enum ValType {
		Int, Real, Link, Raw
	}
	
}
