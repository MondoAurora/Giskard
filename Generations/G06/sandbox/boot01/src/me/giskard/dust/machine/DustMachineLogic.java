package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogic extends DustConsts.MindDialog implements DustMachineConsts, DustMachineBootConsts, DustUtilsConsts {

	private Map<MindHandle, Object> data = new HashMap<>();

	DustMachineLogicFormatterJson jsonFormatter;

	public DustMachineLogic(Map data) {
		this.data = data;
		jsonFormatter = new DustMachineLogicFormatterJson();
	}

	@Override
	public MindHandle lookup(MindHandle unitHandle, String id, String lang, String token) {
		MindHandle ret = null;
		Map dialogIdeas = DustUtils.simpleGet(data, DIALOG_IDEAS);
		
		if (null == unitHandle) {
			Map units = DustUtils.simpleGet(data, MACHINE_UNITS);

			ret = DustUtils.safeGet(units, id, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object k, Object... hints) {
					DustHandle ret = new DustHandle(id);
					DustMachineUtils.storeHandle(ret, dialogIdeas);
					return ret;
				}
			});
		} else {
			Map<MindHandle, Object> unitData = DustUtils.simpleGet(dialogIdeas, unitHandle, unitHandle);
			Map<String, MindHandle> unitHandles = DustUtils.simpleGet(unitData, UNIT_HANDLES);

			ret = DustUtils.safeGet(unitHandles, id, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object k, Object... hints) {
					DustHandle ret = new DustHandle(unitHandle, id);
					DustMachineUtils.storeHandle(ret, dialogIdeas);
					return ret;
				}
			});
			
			if (!DustUtils.isEmpty(token)) {
				Map vocabulary = DustUtils.simpleGet(data, DIALOG_VOCABULARY);
				DustMachineUtils.storeToken((DustHandle) ret, dialogIdeas, vocabulary, lang, token);
			}
		}

		return ret;
	}

	@Override
	public <RetType> RetType access(MindHandle cmd, Object val, Object... path) {
		Object ret = null;
		Dust.log(null, "       ", cmd, path, val);
		
		if ( ACCESS_SET == cmd ) {
			DustHandle hItem = (DustHandle) path[0];
			Map m = DustUtils.simpleGet(data, DIALOG_IDEAS, hItem.unit, hItem);
			ret = m.put(path[1], val);
		}
		
		return (RetType) ret;
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

}
