package ai.montru.java.dust;

import ai.montru.java.Dust;

public class DustRuntime implements Dust, Dust.Agent {
	
	public static boolean select(Token tLocal, Token tEntity, Object... path) {
		return false;
	}

	public static <RetType> RetType access(Token cmd, Token tEntity, Token tMember, RetType val, Object key) {
		return null;
	}

	@Override
	public Token agentAction(Token action) throws Exception {
		return null;
	}

	public DustRuntime() {
		// TODO Auto-generated constructor stub
	}
	
}
