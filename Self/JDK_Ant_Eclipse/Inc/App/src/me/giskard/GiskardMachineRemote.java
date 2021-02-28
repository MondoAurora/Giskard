package me.giskard;

import java.io.File;

public class GiskardMachineRemote extends GiskardMachineModular {

	public GiskardMachineRemote(String mindModule, String mindVersion, String[] args) {
		super(mindModule, mindVersion, args);
	}
	
	@Override
	public DynamicModule addModule(String modName, String ver) {
		File fMod = new File(modRoot, getModuleKey(modName, ver) + ".jar");
		
		if ( !fMod.isFile() ) {
			Mind.log(MiNDEventLevel.INFO, "would download module from server", modName, ver);
		}

		return super.addModule(modName, ver);
	}

}
