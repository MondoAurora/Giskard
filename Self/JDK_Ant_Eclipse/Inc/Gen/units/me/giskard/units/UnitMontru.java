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
		Object lblMain = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Main graph", lblMain, MTMEM_TEXT_PLAINTEXT_STRING);

		Object lblProp = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Property pages", lblProp, MTMEM_TEXT_PLAINTEXT_STRING);

		Object pnlRight = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_LAYOUT_GRID, pnlRight, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GEOMETRY_LOCDATA_Y, pnlRight, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblMain, pnlRight, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlRight, MTMEM_GENERIC_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, lblProp, pnlRight, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlRight, MTMEM_GENERIC_SPLIT_WEIGHT);

		// Filter block
		Object lblFiltCond = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Filter conditions", lblFiltCond, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_LINE, lblFiltCond, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_TEXT, lblFiltCond, MTMEM_MODEL_ENTITY_TAGS);

		Object lblTable = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Entity table", lblTable, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_LINE, lblTable, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_TEXT, lblTable, MTMEM_MODEL_ENTITY_TAGS);

		Object pnlFilter = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_LAYOUT_GRID, pnlFilter, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GEOMETRY_LOCDATA_Y, pnlFilter, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblFiltCond, pnlFilter, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlFilter, MTMEM_GENERIC_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, lblTable, pnlFilter, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlFilter, MTMEM_GENERIC_SPLIT_WEIGHT);

		// Left side
		Object lblViewSel = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "View selector", lblViewSel, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_LINE, lblViewSel, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_TEXT, lblViewSel, MTMEM_MODEL_ENTITY_TAGS);

		Object lblCtrl = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_LABEL);
		Giskard.access(MiNDAccessCommand.Set, "Controls", lblCtrl, MTMEM_TEXT_PLAINTEXT_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_LINE, lblCtrl, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_BORDER_TEXT, lblCtrl, MTMEM_MODEL_ENTITY_TAGS);

		Object pnlLeft = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_LAYOUT_PAGE, pnlLeft, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, lblViewSel, pnlLeft, MTMEM_GENERIC_LINK_MAP, MTTAG_GUI_ALIGN_PAGESTART);
		Giskard.access(MiNDAccessCommand.Add, pnlFilter, pnlLeft, MTMEM_GENERIC_LINK_MAP, MTTAG_GUI_ALIGN_CENTER);
		Giskard.access(MiNDAccessCommand.Add, lblCtrl, pnlLeft, MTMEM_GENERIC_LINK_MAP, MTTAG_GUI_ALIGN_PAGEEND);
		
		Object pnlMain = Giskard.access(MiNDAccessCommand.Get, MTAGN_GUI_PANEL);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GUI_LAYOUT_GRID, pnlMain, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_GEOMETRY_LOCDATA_X, pnlMain, MTMEM_MODEL_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, pnlLeft, pnlMain, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, pnlMain, MTMEM_GENERIC_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, pnlRight, pnlMain, MTMEM_GENERIC_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, pnlMain, MTMEM_GENERIC_SPLIT_WEIGHT);


		Giskard.access(MiNDAccessCommand.Set, pnlMain, MTUNI_MONTRU, MTMEM_MODEL_UNIT_SERVICES, MTSVC_MONTRU_GUIMAIN);

	}
}
