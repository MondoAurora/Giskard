package me.giskard.java.dust;

import me.giskard.java.MindConsts.MiNDToken;
import me.giskard.java.MindConsts.MiNDTokenSource;
import me.giskard.java.dust.DustCollections.DustFactory;
import me.giskard.java.dust.DustConsts.DustCreator;

public class DustToken implements MiNDToken {
	private static final DustCreator<String, DustToken> TOKEN_CREATOR = new DustCreator<String, DustToken>() {
		@Override
		public DustToken create(String key, Object... hints) {
			DustToken t = new DustToken(key, (String) hints[0], (String) hints[1]);
			return t;
		}
	};
	
	private static final DustFactory<String, DustToken> TOKEN_FACTORY = new DustFactory<String, DustToken>(TOKEN_CREATOR, true);
	
	static final MiNDTokenSource TOKEN_SOURCE = new MiNDTokenSource() {
		@Override
		public MiNDToken resolve(String dslId, String termId) {
			String name = dslId + DustMind.SEP_ID + termId;
			return TOKEN_FACTORY.get(name, dslId, termId);
		}
	};

	public final String name;
	public final String dslId;
	public final String termId;
	
	DustToken(String name, String dslId, String termId) {
		this.name = name;
		this.dslId = dslId;
		this.termId = termId;
	}

	@Override
	public String toString() {
		return "Token[" + name + "]";
	}
}