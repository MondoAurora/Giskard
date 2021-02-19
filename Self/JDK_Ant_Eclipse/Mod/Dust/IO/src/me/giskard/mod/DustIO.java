package me.giskard.mod;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.parser.JSONParser;

import me.giskard.Mind;
import me.giskard.MindConsts;
import me.giskard.dust.io.json.DustIODomReaderState;
import me.giskard.dust.io.json.DustIOJsonReader;
import me.giskard.utils.MindUtils;

public class DustIO implements MindConsts.MiNDAgent {
	void initModule() throws Exception {
	String fileName = "Knowledge/Json/System.json";
//String fileName = "/Users/lkedves/work/Giskard/data/countries.geojson";
//String fileName = "/Users/lkedves/work/Giskard/data/National_Wild_and_Scenic_River_Lines_(Feature_Layer).geojson";
//		String fileName = "/Users/lkedves/work/Giskard/data/Current_Invasive_Plants_(Feature_Layer).geojson";
//	String fileName = "/Users/lkedves/git/rtms/milestone00/RtmsFrontend/out/scanAll.json";

		if ( !fileName.startsWith("/") ) {
			String root = MindUtils.getRoot();

			if ( !MindUtils.isEmpty(root) ) {
				fileName = root + "/" + fileName;
			}
		}

		Reader r = new FileReader(fileName);
		
		Mind.log(MiNDEventLevel.TRACE, "Reading JSON file", fileName);

//		Object result = testSax(r);
		Object result = testDom(r);
		
		Mind.log(MiNDEventLevel.INFO, "File read success", fileName, result);
	}

	public Object testSax(Reader r) throws Exception {
		JSONParser p = new JSONParser();
		DustIOJsonReader.JsonContentDispatcher h = new DustIOJsonReader.JsonContentDispatcher(MindUtils.LOGGER);

		p.parse(r, h);
		return h;
	}

	public Object testDom(Reader r) throws Exception {
		DustIODomReaderState rs = new DustIODomReaderState(r);
		
		while ( rs.step() ) {
			
		}

		return rs;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			initModule();
			break;
		default:
			break;
		}
		return MiNDResultType.ACCEPT;
	}
}
