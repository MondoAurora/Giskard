package me.giskard.dust.gui.swing;

import java.awt.Rectangle;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class DustSwingUtils extends GiskardUtils implements DustSwingConsts {

	public static Rectangle toRect(MiNDToken entity) {
		Integer cx = Giskard.access(MiNDAccessCommand.Get, -1, entity, MTMEM_GEOMETRY_AREA_CENTER,
				MTMEM_GEOMETRY_GEODATA_COORDS, 0);
		Integer cy = Giskard.access(MiNDAccessCommand.Get, -1, entity, MTMEM_GEOMETRY_AREA_CENTER,
				MTMEM_GEOMETRY_GEODATA_COORDS, 1);
		Integer sx = Giskard.access(MiNDAccessCommand.Get, -1, entity, MTMEM_GEOMETRY_AREA_SPAN,
				MTMEM_GEOMETRY_GEODATA_COORDS, 0);
		Integer sy = Giskard.access(MiNDAccessCommand.Get, -1, entity, MTMEM_GEOMETRY_AREA_SPAN,
				MTMEM_GEOMETRY_GEODATA_COORDS, 1);
		
		Rectangle rct = new Rectangle(sx,  sy);
		rct.setLocation(cx - sx / 2, cy - sy / 2);
		
		return rct;
	}
}
