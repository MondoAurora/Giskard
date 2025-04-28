package me.giskard.text;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustTextTokens extends DustConsts {


	MindToken TOKEN_TEXT_LANGUAGE = Dust.access(null, "giskard.me:text_1.0:0");
	MindToken TOKEN_TEXT_LANGUAGE_EN_US = Dust.access(null, "giskard.me:text_1.0:1");
	MindToken TOKEN_TEXT_LANGUAGE_DEFAULT = Dust.access(null, "giskard.me:text_1.0:2");
	MindToken TOKEN_TEXT_TOKEN = Dust.access(null, "giskard.me:text_1.0:3");

	MindToken TOKEN_TEXT_TYPE = Dust.access(null, "giskard.me:text_1.0:4");
	MindToken TOKEN_TEXT_TYPE_TOKEN = Dust.access(null, "giskard.me:text_1.0:5");
	MindToken TOKEN_TEXT_TYPE_LABEL = Dust.access(null, "giskard.me:text_1.0:6");

	MindToken TOKEN_TEXT_PLAIN = Dust.access(null, "giskard.me:text_1.0:7");
	MindToken TOKEN_TEXT_PLAIN_TEXT = Dust.access(null, "giskard.me:text_1.0:8");
}

