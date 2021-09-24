package me.giskard.sandbox.mind.plain;

import me.giskard.sandbox.mind.GSBMind;
import me.giskard.sandbox.mind.plain.GSBMindPlainConsts.PlainException;

public class GSBMindPlain extends GSBMind {
	
	GSBMindPlainModel model;
	GSBMindPlainIdea idea;
	
	public GSBMindPlain() {
		model = new GSBMindPlainModel();
		idea = new GSBMindPlainIdea();
	}

	@Override
	protected Object access_(MindAccessCmd cmd, Object value, Object... path) {
		return model.access(cmd, value, path);
	}

	@Override
	protected <RetType> RetType wrapException_(Throwable orig, Object... params) {
		if ( orig instanceof PlainException ) {
			throw (PlainException) orig;
		} else {
			throw new PlainException(orig, params);
		}
	}

	@Override
	protected void log_(MindEventLevel level, Object... params) {
		// TODO Auto-generated method stub
		
	}

}
