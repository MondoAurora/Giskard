package me.giskard.dust.machine;

import java.util.Collections;
import java.util.Map;

import me.giskard.dust.utils.DustUtils;

@SuppressWarnings("rawtypes")
public interface DustMachineConstsInt extends DustMachineConsts {
	public class DustHandle extends MindHandle {
		DustHandle unit;
		String id;
		
		public DustHandle(MindHandle unit, String id) {
			this.unit = (DustHandle) unit;
			this.id = id;
		}
		
		public DustHandle(String id) {
			this.unit = this;
			this.id = id;
		}
		
		public String getId() {
			return id;
		}
		
		public DustHandle getUnit() {
			return unit;
		}
		
		@Override
		public String toString() {
			String token = DustUtils.simpleGet(CURR_VOC, unit, this);
			if (!DustUtils.isEmpty(token)) {
				token = DUST_SEP_TOKEN + token;
			}
			return DustUtils.sbAppend(null, "", true, "[", ((this == unit) ? "" : unit.id), "::", id, token, "]").toString();
		}
		
		static Map CURR_VOC = Collections.EMPTY_MAP;
	}
	
}
