package me.giskard.text;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustTextHandles extends DustConsts {


	MindHandle TEXT_TAG_LANGUAGE = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:0");
	MindHandle TEXT_TAG_LANGUAGE_EN_US = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:1");
	MindHandle TEXT_ATT_LANGUAGE_DEFAULT = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:2");
	MindHandle TEXT_ATT_TOKEN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:3");

	MindHandle TEXT_TAG_TYPE = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:4");
	MindHandle TEXT_TAG_TYPE_TOKEN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:5");
	MindHandle TEXT_TAG_TYPE_LABEL = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:6");

	MindHandle TEXT_ASP_PLAIN = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:7");
	MindHandle TEXT_ATT_PLAIN_TEXT = Dust.access(MindAccess.Lookup, "giskard.me:text_1.0:8");
}

