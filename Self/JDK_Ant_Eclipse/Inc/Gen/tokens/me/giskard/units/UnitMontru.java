package me.giskard.units;

import me.giskard.Giskard;
import me.giskard.tokens.DustTokensGui;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensMontru;
import me.giskard.tokens.DustTokensText;

public class UnitMontru implements DustTokensMind, DustTokensMontru, DustTokensGui, DustTokensText {
	public static void init() throws Exception {

		Giskard.log(MiNDEventLevel.Trace, UnitMontru.class, "init() called.");
		
		// right side
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "Main graph", MTMEMBER_ACTION_GPR01, MTMEMBER_PLAIN_STRING);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "Property pages", MTMEMBER_ACTION_GPR02, MTMEMBER_PLAIN_STRING);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR09);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_PANEL, MTMEMBER_ACTION_GPR09, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, MTMEMBER_ACTION_GPR09, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_GPR09, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, MTMEMBER_ACTION_GPR09, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR09, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, MTMEMBER_ACTION_GPR09, MTMEMBER_SPLIT_WEIGHT);

		// Filter block
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "Filter conditions", MTMEMBER_ACTION_GPR01, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_TAGS);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "Entity table", MTMEMBER_ACTION_GPR02, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_TAGS);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR08);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_PANEL, MTMEMBER_ACTION_GPR08, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, MTMEMBER_ACTION_GPR08, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_GPR08, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, MTMEMBER_ACTION_GPR08, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR08, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, MTMEMBER_ACTION_GPR08, MTMEMBER_SPLIT_WEIGHT);

		// Left side
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR01);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "View selector", MTMEMBER_ACTION_GPR01, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, MTMEMBER_ACTION_GPR01, MTMEMBER_ENTITY_TAGS);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR02);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_LABEL, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Set, "Controls", MTMEMBER_ACTION_GPR02, MTMEMBER_PLAIN_STRING);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_LINE, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_BORDER_TEXT, MTMEMBER_ACTION_GPR02, MTMEMBER_ENTITY_TAGS);

		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR07);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_PANEL, MTMEMBER_ACTION_GPR07, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_PAGE, MTMEMBER_ACTION_GPR07, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR01, MTMEMBER_ACTION_GPR07, MTMEMBER_LINK_MAP, MTTAG_LOC_PAGESTART);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR08, MTMEMBER_ACTION_GPR07, MTMEMBER_LINK_MAP, MTTAG_LOC_CENTER);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR02, MTMEMBER_ACTION_GPR07, MTMEMBER_LINK_MAP, MTTAG_LOC_PAGEEND);
		
		Giskard.access(MiNDAccessCommand.Get, MTMEMBER_ACTION_GPR06);
		Giskard.access(MiNDAccessCommand.Set, MTAGENT_PANEL, MTMEMBER_ACTION_GPR06, MTMEMBER_ENTITY_PRIMARYTYPE);
		Giskard.access(MiNDAccessCommand.Add, MTTAG_LAYOUT_GRID, MTMEMBER_ACTION_GPR06, MTMEMBER_ENTITY_TAGS);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR07, MTMEMBER_ACTION_GPR06, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.2, MTMEMBER_ACTION_GPR06, MTMEMBER_SPLIT_WEIGHT);
		Giskard.access(MiNDAccessCommand.Add, MTMEMBER_ACTION_GPR09, MTMEMBER_ACTION_GPR06, MTMEMBER_LINK_ARR);
		Giskard.access(MiNDAccessCommand.Add, 0.8, MTMEMBER_ACTION_GPR06, MTMEMBER_SPLIT_WEIGHT);


		Giskard.access(MiNDAccessCommand.Set, MTMEMBER_ACTION_GPR06, MTUNIT_MONTRU, MTMEMBER_UNIT_SERVICES, MTSERVICE_GUI);

	}
}
