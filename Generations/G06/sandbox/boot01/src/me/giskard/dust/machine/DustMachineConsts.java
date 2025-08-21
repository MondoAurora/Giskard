package me.giskard.dust.machine;

import me.giskard.dust.DustConsts;
import me.giskard.dust.utils.DustUtils;

public interface DustMachineConsts extends DustConsts {
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
			return DustUtils.sbAppend(null, "", true, "[", ((this == unit) ? "" : unit.id), "::", id, "]").toString();
		}
	}
	
}
