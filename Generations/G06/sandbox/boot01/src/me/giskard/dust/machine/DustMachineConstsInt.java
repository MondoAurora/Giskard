package me.giskard.dust.machine;

import java.util.HashMap;

import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts.DustCreator;

@SuppressWarnings("rawtypes")
public interface DustMachineConstsInt extends DustMachineConsts, DustMachineBootConsts {
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

		public boolean isUnit() {
			return this == unit;
		}
		
		public String getResUnitId(String lang) {
			return getUnit().id + DUST_SEP + DustUtils.toSafeString(lang);
		}

		@Override
		public String toString() {
			String token = "";
//			token = DustUtils.simpleGet(CURR_VOC, unit, this);
//			if (!DustUtils.isEmpty(token)) {
//				token = DUST_SEP_TOKEN + token;
//			}
			return DustUtils.sbAppend(null, "", true, "<", ((this == unit) ? "" : unit.id), "::", id, token, ">").toString();
		}

//		static Map CURR_VOC = Collections.EMPTY_MAP;
	}

	public interface UnitLoader {
		void optLoadUnit(String key) throws Exception;
	}

	class DustIdea extends MindIdea {
		private static final long serialVersionUID = 1L;
//		private static final Set<DustIdea> SERIALIZING = new HashSet<>();

		public DustIdea(DustHandle h) {
			put(MIND_IDEA_HANDLE, h);

			if (h.isUnit()) {
				put(MIND_UNIT_HANDLES, new HashMap());
				put(MIND_UNIT_IDEAS, new HashMap());
			}
		}

//		@Override
//		public String toString() {
//			try {
//				return SERIALIZING.add(this) ? super.toString() : "{***}";				
//			} finally {
//				SERIALIZING.remove(this);
//			}
//		}
	}

	DustCreator<DustIdea> IDEA_CREATOR = new DustCreator<DustIdea>() {
		@Override
		public DustIdea create(Object key, Object... hints) {
			return new DustIdea((DustHandle) key);
		}
	};

}
