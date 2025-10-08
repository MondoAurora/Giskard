package me.giskard.dust.forge;



import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustGenForgeConsts2 extends DustConsts {
	
	MindHandle FORGE_SRC_PATH = Dust.lookup(null, "forge:0");
	MindHandle FORGE_GEN_PATH = Dust.lookup(null, "forge:1");
	MindHandle FORGE_BOOT_TAG = Dust.lookup(null, "forge:2");
	MindHandle FORGE_TAG_CLASS = Dust.lookup(null, "forge:3");
	MindHandle FORGE_BOOT_TOKENS = Dust.lookup(null, "forge:10");
	MindHandle FORGE_BOOT_UNITS = Dust.lookup(null, "forge:11");
	MindHandle FORGE_CLASS_WRITERS = Dust.lookup(null, "forge:12");
}