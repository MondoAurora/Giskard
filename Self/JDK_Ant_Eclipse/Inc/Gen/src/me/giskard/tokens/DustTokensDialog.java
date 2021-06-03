package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensDialog extends GiskardConsts { 
	MiNDToken MTUNI_DIALOG = Giskard.defineToken(MiNDTokenType.Unit, "Dialog"); 
	MiNDToken MTTYP_DIALOG_CONTEXT = Giskard.defineToken(MiNDTokenType.Type, "Context", MTUNI_DIALOG);
	MiNDToken MTMEM_DIALOG_CONTEXT_TOKENS = Giskard.defineToken(MiNDTokenType.Member, "Tokens", MTTYP_DIALOG_CONTEXT, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTMEM_DIALOG_CONTEXT_ACTIVITIES = Giskard.defineToken(MiNDTokenType.Member, "Activities", MTTYP_DIALOG_CONTEXT, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEM_DIALOG_CONTEXT_CHANGE = Giskard.defineToken(MiNDTokenType.Member, "Change", MTTYP_DIALOG_CONTEXT, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND = Giskard.defineToken(MiNDTokenType.Tag, "AccessCommand", MTUNI_DIALOG);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND_ADD = Giskard.defineToken(MiNDTokenType.Tag, "Add", MTTAG_DIALOG_ACCESSCOMMAND);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND_CHK = Giskard.defineToken(MiNDTokenType.Tag, "Chk", MTTAG_DIALOG_ACCESSCOMMAND);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND_DEL = Giskard.defineToken(MiNDTokenType.Tag, "Del", MTTAG_DIALOG_ACCESSCOMMAND);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND_GET = Giskard.defineToken(MiNDTokenType.Tag, "Get", MTTAG_DIALOG_ACCESSCOMMAND);
	MiNDToken MTTAG_DIALOG_ACCESSCOMMAND_SET = Giskard.defineToken(MiNDTokenType.Tag, "Set", MTTAG_DIALOG_ACCESSCOMMAND);
	MiNDToken MTTYP_DIALOG_COMMIT = Giskard.defineToken(MiNDTokenType.Type, "Commit", MTUNI_DIALOG);
	MiNDToken MTTYP_DIALOG_VISITINFO = Giskard.defineToken(MiNDTokenType.Type, "VisitInfo", MTUNI_DIALOG);
	MiNDToken MTMEM_DIALOG_VISITINFO_KEYARR = Giskard.defineToken(MiNDTokenType.Member, "KeyArr", MTTYP_DIALOG_VISITINFO, MiNDValType.Int, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_KEYMAP = Giskard.defineToken(MiNDTokenType.Member, "KeyMap", MTTYP_DIALOG_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_LINKNEW = Giskard.defineToken(MiNDTokenType.Member, "LinkNew", MTTYP_DIALOG_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_LINKOLD = Giskard.defineToken(MiNDTokenType.Member, "LinkOld", MTTYP_DIALOG_VISITINFO, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_TOKEN = Giskard.defineToken(MiNDTokenType.Member, "Token", MTTYP_DIALOG_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_VALNEW = Giskard.defineToken(MiNDTokenType.Member, "ValNew", MTTYP_DIALOG_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
	MiNDToken MTMEM_DIALOG_VISITINFO_VALOLD = Giskard.defineToken(MiNDTokenType.Member, "ValOld", MTTYP_DIALOG_VISITINFO, MiNDValType.Raw, MiNDCollType.One);
}
