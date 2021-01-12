package ai.montru.java.dust;

import ai.montru.java.Dust;

public abstract class DustWrapperAgent<WrappedType> implements Dust, Dust.Agent {
	private WrappedType wrappedInstance;
	
	protected abstract WrappedType createWrappedInstance();
	
	protected synchronized WrappedType getWrappedInstance() {
		if ( null == wrappedInstance ) {
			wrappedInstance = createWrappedInstance();
		}
		return wrappedInstance;
	}
	
}
