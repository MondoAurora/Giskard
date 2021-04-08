package me.giskard.tokens;

import me.giskard.Giskard;

public interface DustTokensGui extends DustTokensMind {
	MiNDToken MTUNIT_GUI = Giskard.defineToken(MiNDTokenType.UNIT, "Gui");
	
	MiNDToken MTTYPE_GUIOWNER = Giskard.defineToken(MiNDTokenType.TYPE, "GuiOwner", MTUNIT_GUI);
	MiNDToken MTMEMBER_GUIOWNER_WORLD = Giskard.defineToken(MiNDTokenType.MEMBER, "World", MTTYPE_GUIOWNER, MiNDValType.Link, MiNDCollType.One);
	
	MiNDToken MTTYPE_WORLD = Giskard.defineToken(MiNDTokenType.TYPE, "World", MTUNIT_GUI);
	MiNDToken MTMEMBER_WORLD_SCREENS = Giskard.defineToken(MiNDTokenType.MEMBER, "Screens", MTTYPE_WORLD, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_WORLD_POINTERS = Giskard.defineToken(MiNDTokenType.MEMBER, "Pointers", MTTYPE_WORLD, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_WORLD_WINDOWS = Giskard.defineToken(MiNDTokenType.MEMBER, "Windows", MTTYPE_WORLD, MiNDValType.Link, MiNDCollType.Arr);
	MiNDToken MTMEMBER_WORLD_RENDERER = Giskard.defineToken(MiNDTokenType.MEMBER, "Renderer", MTTYPE_WORLD, MiNDValType.Link, MiNDCollType.One);

	MiNDToken MTTYPE_SCREEN = Giskard.defineToken(MiNDTokenType.TYPE, "Screen", MTUNIT_GUI);

	MiNDToken MTTYPE_WINDOW = Giskard.defineToken(MiNDTokenType.TYPE, "Window", MTUNIT_GUI);
	
	MiNDToken MTTYPE_POINTER = Giskard.defineToken(MiNDTokenType.TYPE, "Pointer", MTUNIT_GUI);

	MiNDToken MTAGENT_RENDERER = Giskard.defineToken(MiNDTokenType.AGENT, "Renderer", MTUNIT_GUI);

	MiNDToken MTAGENT_FRAME = Giskard.defineToken(MiNDTokenType.AGENT, "Frame", MTUNIT_GUI);

}
