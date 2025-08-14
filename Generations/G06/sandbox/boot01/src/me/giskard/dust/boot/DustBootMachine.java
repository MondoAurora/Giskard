package me.giskard.dust.boot;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;

public class DustBootMachine extends DustConsts.MindDialog implements DustBootConsts {

	@Override
	public <RetType> RetType access(MindHandle cmd, Object val, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void broadcast(MindHandle event, Object... params) {
		Dust.log(event, params);
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		DustBootTest02.test01();
//		DustBootTest01.test02();
//		DustBootTest01.test01();
		return null;
	}

}
