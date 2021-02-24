package me.giskard.mod;

import java.io.Reader;

import org.json.simple.parser.JSONParser;

import me.giskard.Mind;
import me.giskard.MindConsts;
import me.giskard.dust.io.abnf.AbnfParserPrototype;
import me.giskard.dust.io.json.DustIODomReaderState;
import me.giskard.dust.io.json.DustIOJsonReader;
import me.giskard.utils.MindUtils;

public class DustIO implements MindConsts.MiNDAgent {
	void initModule() throws Exception {
//	String fileName = "Knowledge/Json/System.json";
//String fileName = "/Users/lkedves/work/Giskard/data/countries.geojson";
//String fileName = "/Users/lkedves/work/Giskard/data/National_Wild_and_Scenic_River_Lines_(Feature_Layer).geojson";
//		String fileName = "/Users/lkedves/work/Giskard/data/Current_Invasive_Plants_(Feature_Layer).geojson";
//	String fileName = "/Users/lkedves/git/rtms/milestone00/RtmsFrontend/out/scanAll.json";
		
		String fileName = "Knowledge/Abnf/abnf.abnf";
//		String fileName = "Knowledge/Abnf/json.abnf";

		if ( !fileName.startsWith("/") ) {
			String root = MindUtils.getRoot();

			if ( !MindUtils.isEmpty(root) ) {
				fileName = root + "/" + fileName;
			}
		}
		
		Mind.log(MiNDEventLevel.TRACE, "Start reading file", fileName, "...");

//		Reader r = new FileReader(fileName);
//		Object result = testSax(r);
//		Object result = testDom(r);
		Object result = testAbnf(fileName);
		
		Mind.log(MiNDEventLevel.INFO, "File read success", fileName, ", result:\n", result);
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

	public Object testAbnf(String name) throws Exception {
		AbnfParserPrototype pp = new AbnfParserPrototype(name);
		return pp;
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
