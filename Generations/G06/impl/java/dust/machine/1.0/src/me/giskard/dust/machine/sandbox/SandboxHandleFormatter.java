package me.giskard.dust.machine.sandbox;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.machine.DustMachineHandle;
import me.giskard.dust.text.DustTextConsts;
import me.giskard.dust.utils.DustUtils;

public class SandboxHandleFormatter implements DustTextConsts, DustMachineHandle.Formatter {
	
	SandboxUnitLoader unitLoader;
	MindToken hLang;
	String langTemp = "en";

	Map<String, Map<MindToken, String>> texts = new HashMap<>();
	
	public SandboxHandleFormatter(SandboxUnitLoader unitLoader) {
		this.unitLoader = unitLoader;
	}
	
	public void setLang(MindToken hLang) {
		this.hLang = hLang;
	}

	protected void loadUnit(DustMachineHandle hUnit) {
		Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Now would load", hUnit);
		try {
			unitLoader.loadUnits(hUnit.toString());
			Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Load success", hUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString(DustMachineHandle h) {
		String id = h.getId();
		id = DustUtils.cutPostfix(id, DUST_SEP_ID);
		
		Map<MindToken, String> unitTexts = texts.get(id);
		
		if ( null == unitTexts ) {
			unitTexts = new HashMap<>();
			texts.put(id, unitTexts);
			
			String pf = DustUtils.getPostfix(id, "_");
			String fName = DustUtils.cutPostfix(id, "_") + "_txt_" + langTemp + "_" + pf;
			fName = fName.replace(":", "/res/");

			try {
				unitLoader.loadUnits(fName);
				
//				DustMachineHandle hRes = Dust.access(MindAccess.Lookup, fName);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return unitTexts.getOrDefault(h, h.getId());
	}

}
