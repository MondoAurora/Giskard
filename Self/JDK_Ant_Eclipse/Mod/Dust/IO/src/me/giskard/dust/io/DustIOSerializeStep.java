package me.giskard.dust.io;

import java.util.Map;

import me.giskard.Mind;
import me.giskard.utils.MindTokenTranslator;

public class DustIOSerializeStep implements DustIOTokens {
	public static final MiNDBuilder<Integer, DustIOSerializeStep> BUILDER = new MiNDBuilder<Integer, DustIOSerializeStep>() {
		@Override
		public DustIOSerializeStep create(Integer key) {
			return new DustIOSerializeStep();
		}

		@Override
		public void release(Integer key, DustIOSerializeStep val) {
			val.reset();
		}
	};
	
	public MiNDAgentAction action;
	public MiNDToken item;
	public Object data;

	public void reset() {
		data = null;
	}

	public void setAll(MiNDAgentAction action, MiNDToken item, Object data) {
		this.action = action;
		this.item = item;
		this.data = data;
	}

	public Object publish(MiNDToken target) {
		Mind.access(MiNDAccessCommand.Set, item, MTTYPE_SERIALIZEEVENT, MTMEMBER_SERIALIZEEVENT_TYPE);
		Object val = (item == DustIOConsts.MTTAG_VALTYPE_REF) ? ((Map.Entry<?,?>) data).getKey()
				: (item == DustIOConsts.MTTAG_VALTYPE_RAW) ? data : null;
		Mind.access(MiNDAccessCommand.Set, val, MTTYPE_SERIALIZEEVENT, DustIOConsts.MTMEMBER_VARIANT_VALUE);
		MindTokenTranslator.setEnumToken(MTTYPE_SERIALIZEEVENT, action);

		return val;
	}

	@Override
	public String toString() {
		return action + " " + item + " " + data;
	}
}