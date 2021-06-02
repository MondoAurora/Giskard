package me.giskard.dust.io;

import java.util.Map;

import me.giskard.Giskard;
import me.giskard.coll.GisCollConsts.MiNDBuilder;

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
	
	public MiNDToken action;
	public MiNDToken item;
	public Object data;

	public void reset() {
		data = null;
	}

	public void setAll(MiNDToken action, MiNDToken item, Object data) {
		this.action = action;
		this.item = item;
		this.data = data;
	}

	public Object publish(MiNDToken target) {
		Giskard.access(MiNDAccessCommand.Set, item, MTTYP_IO_SERIALIZEEVENT, MTMEM_MODEL_ENTITY_TAGS);
		Object val = (item == DustIOConsts.MTTAG_IDEA_VALTYPE_LINK) ? ((Map.Entry<?,?>) data).getKey()
				: (item == DustIOConsts.MTTAG_IDEA_VALTYPE_RAW) ? data : null;
		Giskard.access(MiNDAccessCommand.Set, val, MTTYP_IO_SERIALIZEEVENT, DustIOConsts.MTMEM_GENERIC_VALUE_RAW);
//		GisToolsTokenTranslator.setEnumToken(MTTYP_SERIALIZEEVENT, action);
		Giskard.access(MiNDAccessCommand.Set, action, MTTYP_IO_SERIALIZEEVENT, MTMEM_MODEL_ENTITY_TAGS);
		return val;
	}

	@Override
	public String toString() {
		return action + " " + item + " " + data;
	}
}