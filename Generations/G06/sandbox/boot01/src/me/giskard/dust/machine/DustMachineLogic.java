package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogic extends DustConsts.MindDialog implements DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	Map<MindHandle, Object> data = new HashMap<>();
	
	DustMachineLogicFormatterJson jsonFormatter;

	public DustMachineLogic(Map data) {
		this.data = data;
		jsonFormatter = new DustMachineLogicFormatterJson(this);
	}

	@Override
	public <RetType> RetType access(MindHandle cmd, Object val, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(MindHandle event, Object... params) {
		Dust.log(event, params);
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		jsonFormatter.optLoadUnit(null, DustMachineUtils.buildUnitKey("giskard.me", "test01"));
		return null;
	}

	public MindHandle resolveHandle(Map<String, String> unitRefMap, String unit, String t) {
		String ur = unit;
		String k = t;
		int sep = t.indexOf(DUST_SEP_TOKEN);
		if (-1 != sep) {
			ur = unitRefMap.get(t.substring(0, sep));
			k = t.substring(sep);
		} else {
			sep = t.indexOf(DUST_SEP_ID);
			if (-1 != sep) {
				ur = unitRefMap.get(t.substring(0, sep));
				k = t.substring(sep + DUST_SEP_ID.length());
			}
		}

		DustHandle hUnit = DustUtils.simpleGet(data, MACHINE_UNITS, ur);
		return DustUtils.isEmpty(k) ? hUnit : DustUtils.simpleGet(data, DIALOG_IDEAS, hUnit, hUnit, UNIT_HANDLES, k);
	}

}
