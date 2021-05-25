package me.giskard.dust.db;

import me.giskard.GiskardUtils;

public class DustDBUtils extends GiskardUtils implements DustDBConsts {

	public static String buildColId(String tblName, String colName) {
		return GiskardUtils.sbAppend(null, ".", false, tblName, colName).toString();
	}
}
