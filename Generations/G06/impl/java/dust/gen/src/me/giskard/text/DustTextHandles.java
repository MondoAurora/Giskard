package me.giskard.text;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustTextHandles extends DustConsts {


	MindHandle DH_TEXT_LANGUAGE = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:0");
	MindHandle DH_TEXT_LANGUAGE_EN_US = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:1");
	MindHandle DH_TEXT_LANGUAGE_DEFAULT = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:2");
	MindHandle DH_TEXT_TOKEN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:3");

	MindHandle DH_TEXT_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:4");
	MindHandle DH_TEXT_TYPE_TOKEN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:5");
	MindHandle DH_TEXT_TYPE_LABEL = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:6");

	MindHandle DH_TEXT_PLAIN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:7");
	MindHandle DH_TEXT_PLAIN_TEXT = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:8");
}

