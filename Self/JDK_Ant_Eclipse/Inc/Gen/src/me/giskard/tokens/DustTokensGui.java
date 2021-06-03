package me.giskard.tokens;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public interface DustTokensGui extends GiskardConsts { 
	MiNDToken MTUNI_GUI = Giskard.defineToken(MiNDTokenType.Unit, "Gui"); 
	MiNDToken MTTAG_GUI_PLATFORMTYPE = Giskard.defineToken(MiNDTokenType.Tag, "PlatformType", MTUNI_GUI);
	MiNDToken MTTAG_GUI_PLATFORMTYPE_SWING = Giskard.defineToken(MiNDTokenType.Tag, "Swing", MTTAG_GUI_PLATFORMTYPE);
	MiNDToken MTTYP_GUI_PLATFORM = Giskard.defineToken(MiNDTokenType.Type, "Platform", MTUNI_GUI);
	MiNDToken MTMEM_GUI_PLATFORM_SCREENS = Giskard.defineToken(MiNDTokenType.Member, "Screens", MTTYP_GUI_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEM_GUI_PLATFORM_POINTERS = Giskard.defineToken(MiNDTokenType.Member, "Pointers", MTTYP_GUI_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEM_GUI_PLATFORM_WINDOWS = Giskard.defineToken(MiNDTokenType.Member, "Windows", MTTYP_GUI_PLATFORM, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEM_GUI_PLATFORM_MANAGER = Giskard.defineToken(MiNDTokenType.Member, "Manager", MTTYP_GUI_PLATFORM, MiNDValType.Link, MiNDCollType.One);
	MiNDToken MTTYP_GUI_GUIOWNER = Giskard.defineToken(MiNDTokenType.Type, "GuiOwner", MTUNI_GUI);
	MiNDToken MTMEM_GUI_GUIOWNER_PLATFORMS = Giskard.defineToken(MiNDTokenType.Member, "Platforms", MTTYP_GUI_GUIOWNER, MiNDValType.Link, MiNDCollType.Map);
	MiNDToken MTTYP_GUI_SCREEN = Giskard.defineToken(MiNDTokenType.Type, "Screen", MTUNI_GUI);
	MiNDToken MTTYP_GUI_WINDOW = Giskard.defineToken(MiNDTokenType.Type, "Window", MTUNI_GUI);
	MiNDToken MTTYP_GUI_POINTER = Giskard.defineToken(MiNDTokenType.Type, "Pointer", MTUNI_GUI);
	MiNDToken MTAGN_GUI_MANAGER = Giskard.defineToken(MiNDTokenType.Agent, "Manager", MTUNI_GUI);
	MiNDToken MTAGN_GUI_FRAME = Giskard.defineToken(MiNDTokenType.Agent, "Frame", MTUNI_GUI);
	MiNDToken MTAGN_GUI_PANEL = Giskard.defineToken(MiNDTokenType.Agent, "Panel", MTUNI_GUI);
	MiNDToken MTAGN_GUI_LABEL = Giskard.defineToken(MiNDTokenType.Agent, "Label", MTUNI_GUI);
	MiNDToken MTTAG_GUI_BORDER = Giskard.defineToken(MiNDTokenType.Tag, "Border", MTUNI_GUI);
	MiNDToken MTTAG_GUI_BORDER_LINE = Giskard.defineToken(MiNDTokenType.Tag, "Line", MTTAG_GUI_BORDER);
	MiNDToken MTTAG_GUI_BORDER_TEXT = Giskard.defineToken(MiNDTokenType.Tag, "Text", MTTAG_GUI_BORDER);
	MiNDToken MTTAG_GUI_LAYOUT = Giskard.defineToken(MiNDTokenType.Tag, "Layout", MTUNI_GUI);
	MiNDToken MTTAG_GUI_LAYOUT_PAGE = Giskard.defineToken(MiNDTokenType.Tag, "Page", MTTAG_GUI_LAYOUT);
	MiNDToken MTTAG_GUI_LAYOUT_GRID = Giskard.defineToken(MiNDTokenType.Tag, "Grid", MTTAG_GUI_LAYOUT);
	MiNDToken MTTAG_GUI_LAYOUT_FLOW = Giskard.defineToken(MiNDTokenType.Tag, "Flow", MTTAG_GUI_LAYOUT);
	MiNDToken MTTAG_GUI_LAYOUT_FREE = Giskard.defineToken(MiNDTokenType.Tag, "Free", MTTAG_GUI_LAYOUT);
	MiNDToken MTTAG_GUI_ALIGN = Giskard.defineToken(MiNDTokenType.Tag, "Align", MTUNI_GUI);
	MiNDToken MTTAG_GUI_ALIGN_PAGESTART = Giskard.defineToken(MiNDTokenType.Tag, "PageStart", MTTAG_GUI_ALIGN);
	MiNDToken MTTAG_GUI_ALIGN_PAGEEND = Giskard.defineToken(MiNDTokenType.Tag, "PageEnd", MTTAG_GUI_ALIGN);
	MiNDToken MTTAG_GUI_ALIGN_LINESTART = Giskard.defineToken(MiNDTokenType.Tag, "LineStart", MTTAG_GUI_ALIGN);
	MiNDToken MTTAG_GUI_ALIGN_LINEEND = Giskard.defineToken(MiNDTokenType.Tag, "LineEnd", MTTAG_GUI_ALIGN);
	MiNDToken MTTAG_GUI_ALIGN_CENTER = Giskard.defineToken(MiNDTokenType.Tag, "Center", MTTAG_GUI_ALIGN);
}
