package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogic extends DustConsts.MindDialog implements DustMachineConstsInt, DustMachineBootConsts, DustUtilsConsts {

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

			int sep = id.indexOf(DUST_SEP_ID);

			String k = null;

			if (-1 != sep) {
				k = id.substring(sep + 1);
				id = id.substring(0, sep);
			}

			ret = DustUtils.safeGet(units, id, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object key, Object... hints) {
					DustHandle ret = new DustHandle((String) key);
					DustMachineUtils.storeHandle(ret, dialogIdeas);
					return ret;
				}
			});

			if (null == Dust.access(ACCESS_PEEK, null, ret, PERS_ID)) {
				try {
					jsonFormatter.optLoadUnit(id);
				} catch (Exception e) {
					DustException.swallow(e, "Reading unit", id);
				}
			}

			if (null == k) {
				return ret;
			} else {
				unitHandle = ret;
				id = k;
			}
		}
		
		Map<MindHandle, Object> unitData = DustUtils.simpleGet(dialogIdeas, unitHandle, unitHandle);
		Map<String, MindHandle> unitHandles = DustUtils.simpleGet(unitData, UNIT_HANDLES);
		
		if ( "?".equals(id) ) {
			id = DustMachineUtils.nextId(unitData, UNIT_NEXT_ID);
		}

		ret = DustUtils.safeGet(unitHandles, id, new DustCreator<DustHandle>() {
			@Override
			public DustHandle create(Object key, Object... hints) {
				DustHandle ret = new DustHandle((MindHandle) hints[0], (String) key);
				DustMachineUtils.storeHandle(ret, dialogIdeas);
				return ret;
			}
		}, unitHandle);

		if (!DustUtils.isEmpty(token)) {
			Map vocabulary = DustUtils.simpleGet(data, DIALOG_VOCABULARY);
			DustMachineUtils.storeToken((DustHandle) ret, dialogIdeas, vocabulary, lang, token);
		}

		return ret;
	}

	@Override
	public <RetType> RetType access(MindHandle cmd, Object val, Object... path) {
		Object ret = null;
		DustHandle hItem = (DustHandle) path[0];
		Map m = DustUtils.simpleGet(data, DIALOG_IDEAS, hItem.unit, hItem);

		if (ACCESS_SET == cmd) {
			Dust.log(null, "       ", cmd, path, val);
			ret = m.put(path[1], val);
		} else if (ACCESS_PEEK == cmd) {
			ret = (null == m) ? null : m.get(path[1]);
		}

		return (RetType) ret;
	}

	@Override
	public void broadcast(MindHandle event, Object... params) {
		Dust.log(event, params);
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		return null;
	}

}
