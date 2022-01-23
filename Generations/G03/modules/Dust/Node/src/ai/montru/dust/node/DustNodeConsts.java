package ai.montru.dust.node;

import java.util.Map;

public interface DustNodeConsts extends DustNodeTokens {
	
	String CLASSNAME_MAP = "java.util.HashMap";
	String CLASSNAME_ARR = "java.util.ArrayList";
	String CLASSNAME_SET = "java.util.HashSet";

	public interface EntityInitializer {
		void initNewEntity(DustNodeEntityRef ref, Map<Object, Object> eData);
	}

}
