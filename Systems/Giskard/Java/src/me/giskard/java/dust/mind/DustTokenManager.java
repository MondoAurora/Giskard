package me.giskard.java.dust.mind;

import me.giskard.java.dust.DustCollections.DustFactory;
import me.giskard.java.dust.DustConsts;
import me.giskard.java.dust.DustConsts.DustToken;

public class DustTokenManager extends DustFactory<String, DustToken> implements DustConsts {

	public class TokenImpl implements DustToken {
		public final String name;
		public final String dslId;
		public final String termId;

		TokenImpl(String name, String dslId, String termId) {
			this.name = name;
			this.dslId = dslId;
			this.termId = termId;
		}

		@Override
		public String toString() {
			return "Token[" + name + "]";
		}
	}

	private final DustCreator<String, TokenImpl> TOKEN_CREATOR = new DustCreator<String, TokenImpl>() {
		@Override
		public TokenImpl create(String key, Object... hints) {
			TokenImpl t = new TokenImpl(key, (String) hints[0], (String) hints[1]);
			return t;
		}
	};

	public DustTokenManager() {
		super(true);
		setCreator(TOKEN_CREATOR);
	}

	public DustToken getToken(String dslId, String termId) {
		String name = dslId + SEP_ID + termId;
		return get(name, dslId, termId);
	}
}