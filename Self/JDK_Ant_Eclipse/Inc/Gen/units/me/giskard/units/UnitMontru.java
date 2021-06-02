package me.giskard.units;

import me.giskard.Giskard;
import me.giskard.tokens.DustTokensGeometry;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensMontru;
import me.giskard.tokens.DustTokensText;

public class UnitMontru implements DustTokensMind, DustTokensMontru, DustTokensGui, DustTokensText, DustTokensGeometry {
	public static void init() throws Exception {

		Giskard.log(MiNDEventLevel.Trace, UnitMontru.class, "init() called.");
		
		// right side
		Object lblMain = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Main graph", lblMain, MTMEMBER_PLAIN_STRING);

		Object lblProp = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Property pages", lblProp, MTMEMBER_PLAIN_STRING);

		Object pnlRight = Giskard.access(MiNDAccessCommand.Get, MTAGENT_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, pnlRight, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LOCDATA_Y, pnlRight, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblMain, pnlRight, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlRight, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, lblProp, pnlRight, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlRight, MTMEMBER_SPLIT_WEIGHT);

		// Filter block
		Object lblFiltCond = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Filter conditions", lblFiltCond, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, lblFiltCond, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, lblFiltCond, MTMEMBER_ENTITY_TAGS);

		Object lblTable = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Entity table", lblTable, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, lblTable, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, lblTable, MTMEMBER_ENTITY_TAGS);

		Object pnlFilter = Giskard.access(MiNDAccessCommand.Get, MTAGENT_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, pnlFilter, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LOCDATA_Y, pnlFilter, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblFiltCond, pnlFilter, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlFilter, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, lblTable, pnlFilter, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlFilter, MTMEMBER_SPLIT_WEIGHT);

		// Left side
		Object lblViewSel = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "View selector", lblViewSel, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, lblViewSel, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, lblViewSel, MTMEMBER_ENTITY_TAGS);

		Object lblCtrl = Giskard.access(MiNDAccessCommand.Get, MTAGENT_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Controls", lblCtrl, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, lblCtrl, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, lblCtrl, MTMEMBER_ENTITY_TAGS);

		Object pnlLeft = Giskard.access(MiNDAccessCommand.Get, MTAGENT_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_PAGE, pnlLeft, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblViewSel, pnlLeft, MTMEMBER_LINK_MAP, MTTAG_ALIGN_PAGESTART);
		Giskard.access(MiNDAccessCommand.Add, pnlFilter, pnlLeft, MTMEMBER_LINK_MAP, MTTAG_ALIGN_CENTER);
		Giskard.access(MiNDAccessCommand.Add, lblCtrl, pnlLeft, MTMEMBER_LINK_MAP, MTTAG_ALIGN_PAGEEND);
		
		Object pnlMain = Giskard.access(MiNDAccessCommand.Get, MTAGENT_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, pnlMain, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LOCDATA_X, pnlMain, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, pnlLeft, pnlMain, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlMain, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, pnlRight, pnlMain, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlMain, MTMEMBER_SPLIT_WEIGHT);


		Giskard.access(MiNDAccessCommand.Set, pnlMain, MTUNIT_MONTRU, MTMEMBER_UNIT_SERVICES, MTSERVICE_GUIMAIN);

	}
}
