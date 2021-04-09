package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGui extends DustTokensMind {
	MiNDToken MTUNIT_GUI = Giskard.defineToken(MiNDTokenType.Unit, "Gui");
	
	MiNDToken MTTAG_PLATFORM = Giskard.defineToken(MiNDTokenType.Tag, "Platform", MTUNIT_GUI);
	
	MiNDToken MTTAG_PLATFORM_SWING = Giskard.defineToken(MiNDTokenType.Tag, "Swing", MTTAG_PLATFORM);

	
	MiNDToken MTTYPE_GUIOWNER = Giskard.defineToken(MiNDTokenType.Type, "GuiOwner", MTUNIT_GUI);
	MiNDToken MTMEMBER_GUIOWNER_PLATFORMS = Giskard.defineToken(MiNDTokenType.Member, "Platforms", MTTYPE_GUIOWNER, MiNDValType.Link, MiNDCollType.Map);
	
	MiNDToken MTTYPE_PLATFORM = Giskard.defineToken(MiNDTokenType.Type, "Platform", MTUNIT_GUI);
	MiNDToken MTMEMBER_PLATFORM_SCREENS = Giskard.defineToken(MiNDTokenType.Member, "Screens", MTTYPE_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_PLATFORM_POINTERS = Giskard.defineToken(MiNDTokenType.Member, "Pointers", MTTYPE_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_PLATFORM_WINDOWS = Giskard.defineToken(MiNDTokenType.Member, "Windows", MTTYPE_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_PLATFORM_MANAGER = Giskard.defineToken(MiNDTokenType.Member, "Manager", MTTYPE_PLATFORM, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTTYPE_SCREEN = Giskard.defineToken(MiNDTokenType.Type, "Screen", MTUNIT_GUI);

	MiNDToken MTTYPE_WINDOW = Giskard.defineToken(MiNDTokenType.Type, "Window", MTUNIT_GUI);
	
	MiNDToken MTTYPE_POINTER = Giskard.defineToken(MiNDTokenType.Type, "Pointer", MTUNIT_GUI);

	MiNDToken MTAGENT_MANAGER = Giskard.defineToken(MiNDTokenType.Agent, "Manager", MTUNIT_GUI);

	MiNDToken MTAGENT_FRAME = Giskard.defineToken(MiNDTokenType.Agent, "Frame", MTUNIT_GUI);
	MiNDToken MTAGENT_PANEL = Giskard.defineToken(MiNDTokenType.Agent, "Panel", MTUNIT_GUI);
	MiNDToken MTAGENT_LABEL = Giskard.defineToken(MiNDTokenType.Agent, "Label", MTUNIT_GUI);
	
	MiNDToken MTTAG_BORDER_LINE = Giskard.defineToken(MiNDTokenType.Tag, "BorderLine", MTUNIT_GUI);
	MiNDToken MTTAG_BORDER_TEXT = Giskard.defineToken(MiNDTokenType.Tag, "BorderLine", MTUNIT_GUI);

	MiNDToken MTTAG_LAYOUT_PAGE = Giskard.defineToken(MiNDTokenType.Tag, "LayoutPage", MTUNIT_GUI);
	MiNDToken MTTAG_LAYOUT_GRID = Giskard.defineToken(MiNDTokenType.Tag, "LayoutGrid", MTUNIT_GUI);
	MiNDToken MTTAG_LAYOUT_FLOW = Giskard.defineToken(MiNDTokenType.Tag, "LayoutFlow", MTUNIT_GUI);
	MiNDToken MTTAG_LAYOUT_FREE = Giskard.defineToken(MiNDTokenType.Tag, "LayoutFree", MTUNIT_GUI);

	MiNDToken MTTAG_LOC_PAGESTART = Giskard.defineToken(MiNDTokenType.Tag, "LocPageStart", MTUNIT_GUI);
	MiNDToken MTTAG_LOC_PAGEEND = Giskard.defineToken(MiNDTokenType.Tag, "LocPageEnd", MTUNIT_GUI);
	MiNDToken MTTAG_LOC_LINESTART = Giskard.defineToken(MiNDTokenType.Tag, "LocLineStart", MTUNIT_GUI);
	MiNDToken MTTAG_LOC_LINEEND = Giskard.defineToken(MiNDTokenType.Tag, "LocLineEnd", MTUNIT_GUI);
	MiNDToken MTTAG_LOC_CENTER = Giskard.defineToken(MiNDTokenType.Tag, "LocCenter", MTUNIT_GUI);
	
}
