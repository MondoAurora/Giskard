package me.giskard.dust.io.json;

import me.giskard.dust.io.DustIOConsts;
import me.giskard.utils.MindUtils;

public interface DustIOJsonConsts extends DustIOConsts {

	enum JsonBlock {
		Entry, Object, Array
	}

	public class JsonContext {
		public JsonBlock block;
		public Object param;

		@Override
		public String toString() {
			StringBuilder sb = MindUtils.sbAppend(null, " ", true, block, param);
			return sb.toString();
		}
	}
}
