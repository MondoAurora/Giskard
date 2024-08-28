package me.giskard.expr;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public interface DustExprHandles extends DustConsts {


	MindHandle DH_EXPR_EXPRESSION = Dust.access(MindAccess.Lookup, "giskard.me:expr_1.0:0");
	MindHandle DH_EXPR_EXPRESSION_STATIC = Dust.access(MindAccess.Lookup, "giskard.me:expr_1.0:1");
	MindHandle DH_EXPR_EXPRESSION_STR = Dust.access(MindAccess.Lookup, "giskard.me:expr_1.0:2");

	MindHandle DH_EXPR_POPULATE = Dust.access(MindAccess.Lookup, "giskard.me:expr_1.0:3");

	MindHandle DH_EXPR_FILTER = Dust.access(MindAccess.Lookup, "giskard.me:expr_1.0:4");
// No text assigned to handle giskard.me:expr_1.0:14
}

