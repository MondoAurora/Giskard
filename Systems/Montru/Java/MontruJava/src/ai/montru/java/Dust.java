package ai.montru.java;

import java.util.HashMap;
import java.util.Map;

public interface Dust {
	String TOKEN_SEP = "::";

	public class Token {
		private static final Map<String, Token> MAP_TOKENS = new HashMap<>();
		private static final Map<Token, Enum<?>> MAP_ENUM = new HashMap<>();
		
		public final int unitIdx;
		public final String id;
		String str;
		
		public static String getTokenGuid(int unitIdx, String id) {
			return unitIdx + TOKEN_SEP + id;
		}
		
		public static Token getToken(String guid) {
			return MAP_TOKENS.get(guid);
		}
		
		public static Token getToken(Enum<?> unit, String id, Enum<?> enumKey) {
			String guid = getTokenGuid(unit.ordinal(), id);
			Token token = getToken(guid);
			
			if ( null == token ) {
				token = new Token(unit, id);
				if ( null != enumKey ) {
					MAP_ENUM.put(token, enumKey);
					token.str += "(" + enumKey.getClass().getSimpleName() + "." + enumKey.name() + ")";
				}
				MAP_TOKENS.put(guid, token);
			}
			
			return token;
		}
		
		public static Token getToken(Enum<?> unit, String id) {
			return getToken(unit, id, null);
		}
		
		public static Enum<?> getEnumKey(Token token) {
			return MAP_ENUM.get(token);
		}
		
		private Token(Enum<?> unit, String id) {
			this(unit.ordinal(), id);
			str += "(" + unit.name() + ")";
		}
		
		private Token(int unitIdx, String id) {
			this.unitIdx = unitIdx;
			this.id = id;
			
			str = "Token<" + getTokenGuid(unitIdx, id) + ">";
		}
		
		@Override
		public String toString() {
			return str;
		}
		
	}

	public interface Agent {
		Token agentAction(Token action) throws Exception;
	}
	
}
