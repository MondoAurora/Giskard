package me.giskard;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.parser.JSONParser;

import me.giskard.dust.io.json.DustIOJsonReader;
import me.giskard.utils.MindUtils;

public class DustIO implements MindConsts.MiNDAgent {
	void initModule() throws Exception {
		String root = MindUtils.getRoot();

		if ( !MindUtils.isEmpty(root) ) {
			Reader r = new FileReader(root + "/Knowledge/Json/System.json");

			JSONParser p = new JSONParser();
			DustIOJsonReader.JsonContentDispatcher h = new DustIOJsonReader.JsonContentDispatcher(MindUtils.LOGGER);

			p.parse(r, h);
		}
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case INIT:
			initModule();
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
