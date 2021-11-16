package ai.montru.dust.node;

import java.util.Map;

public interface DustNodeConsts extends DustNodeTokens {
	
	String CLASSNAME_MAP = "java.util.HashMap";
	String CLASSNAME_ARR = "java.util.ArrayList";
	String CLASSNAME_SET = "java.util.HashSet";

	interface DustRuntime extends GiskardAgent {
		Object resolve(GiskardEntityRef ref, boolean createIfMissing);
	}

	public interface EntityInitializer {
		@SuppressWarnings("rawtypes")
		void initNewEntity(GiskardEntityRef ref, Map eData);
	}

}
