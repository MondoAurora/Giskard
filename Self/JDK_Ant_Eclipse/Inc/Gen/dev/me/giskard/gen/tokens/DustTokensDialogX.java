package me.giskard.gen.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDialogX extends GiskardConsts { 
	MiNDToken MTUNIT_DIALOGX = Giskard.defineToken(MiNDTokenType.Unit, "DialogX"); 
	MiNDToken MTTYPE_CONTEXT = Giskard.defineToken(MiNDTokenType.Type, "Context", MTUNIT_DIALOGX);
	MiNDToken MTTAG_ACCESSCOMMAND = Giskard.defineToken(MiNDTokenType.Tag, "AccessCommand", MTUNIT_DIALOGX);
	MiNDToken MTTYPE_COMMIT = Giskard.defineToken(MiNDTokenType.Type, "Commit", MTUNIT_DIALOGX);
	MiNDToken MTTYPE_DIALOG = Giskard.defineToken(MiNDTokenType.Type, "Dialog", MTUNIT_DIALOGX);
	MiNDToken MTTYPE_VISITINFO = Giskard.defineToken(MiNDTokenType.Type, "VisitInfo", MTUNIT_DIALOGX);
	MiNDToken MTMEMBER_VISITINFO_KEYARR = Giskard.defineToken(MiNDTokenType.Member, "KeyArr", MTTYPE_VISITINFO, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_KEYMAP = Giskard.defineToken(MiNDTokenType.Member, "KeyMap", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKNEW = Giskard.defineToken(MiNDTokenType.Member, "LinkNew", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_LINKOLD = Giskard.defineToken(MiNDTokenType.Member, "LinkOld", MTTYPE_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_TOKEN = Giskard.defineToken(MiNDTokenType.Member, "Token", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALNEW = Giskard.defineToken(MiNDTokenType.Member, "ValNew", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_VISITINFO_VALOLD = Giskard.defineToken(MiNDTokenType.Member, "ValOld", MTTYPE_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEMBER_DIALOG_ACTIVITIES = Giskard.defineToken(MiNDTokenType.Member, "Activities", MTTYPE_DIALOG, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_DIALOG_CHANGE = Giskard.defineToken(MiNDTokenType.Member, "Change", MTTYPE_DIALOG, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTAG_ACCESSCOMMAND_ADD = Giskard.defineToken(MiNDTokenType.Tag, "Add", MTTAG_ACCESSCOMMAND);
	MiNDToken MTTAG_ACCESSCOMMAND_CHK = Giskard.defineToken(MiNDTokenType.Tag, "Chk", MTTAG_ACCESSCOMMAND);
	MiNDToken MTTAG_ACCESSCOMMAND_DEL = Giskard.defineToken(MiNDTokenType.Tag, "Del", MTTAG_ACCESSCOMMAND);
	MiNDToken MTTAG_ACCESSCOMMAND_GET = Giskard.defineToken(MiNDTokenType.Tag, "Get", MTTAG_ACCESSCOMMAND);
	MiNDToken MTTAG_ACCESSCOMMAND_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_ACCESSCOMMAND);
	MiNDToken MTMEMBER_CONTEXT_TOKENS = Giskard.defineToken(MiNDTokenType.Member, "Tokens", MTTYPE_CONTEXT, MiNDValType.Link, MiNDCollType.Map);
}
