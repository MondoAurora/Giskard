package me.giskard.dust.forge;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustForgeConsts extends DustConsts {
	
	MindHandle FORGE_SRC_PATH = Dust.lookup(null, "forge:0");
	MindHandle FORGE_GEN_PACKAGE = Dust.lookup(null, "forge:1");
	MindHandle FORGE_BOOT_CLASSNAME = Dust.lookup(null, "forge:2");
	
	MindHandle FORGE_BOOT_TOKENS = Dust.lookup(null, "forge:10");
	MindHandle FORGE_BOOT_UNITS = Dust.lookup(null, "forge:11");
	
	MindHandle FORGE_BOOT_WRITER = Dust.lookup(null, "forge:20");

}
