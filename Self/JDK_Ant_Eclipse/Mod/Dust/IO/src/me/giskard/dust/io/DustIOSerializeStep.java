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
		Mind.access(MiNDAccessCommand.Set, item, MT_IO_SERIALIZEEVENT, MT_IO_SERIALIZEEVENT_TYPE);
		Object val = (item == DustIOConsts.MT_IDEA_VALTYPE_REF) ? ((Map.Entry<?,?>) data).getKey()
				: (item == DustIOConsts.MT_IDEA_VALTYPE_RAW) ? data : null;
		Mind.access(MiNDAccessCommand.Set, val, MT_IO_SERIALIZEEVENT, DustIOConsts.MT_VARIANT_VALUE);
		MindTokenTranslator.setEnumToken(MT_IO_SERIALIZEEVENT, action);

		return val;
	}

	@Override
	public String toString() {
		return action + " " + item + " " + data;
	}
}