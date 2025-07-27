package me.giskard.dust.boot;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public class DustBootMachine extends DustConsts.MindDialog implements DustBootConsts {

	@Override
	public <RetType> RetType access(MindToken cmd, Object val, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(MindToken event, Object... params) {
		Dust.log(event, params);
	}

	@Override
	public MindToken agentInit() throws Exception {
		DustBootTest01.test01();
		return TOKEN_ACCEPT;
	}

}
