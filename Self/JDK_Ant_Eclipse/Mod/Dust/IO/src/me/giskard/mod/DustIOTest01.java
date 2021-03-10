package me.giskard.mod;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.parser.JSONParser;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.dust.io.DustIOConsts;
import me.giskard.dust.io.abnf.AbnfParserPrototype;
import me.giskard.dust.io.json.DustIODomReaderState;
import me.giskard.dust.io.json.DustIOJsonReader;

public class DustIOTest01 implements DustIOConsts, GiskardConsts.MiNDAgent {
	
	void runTest() throws Exception {

		String fDom = "Knowledge/Json/System.json";
		Object rDom = testDom(fDom);
		Giskard.log(MiNDEventLevel.INFO, "DOM File read success", fDom, ", result:\n", rDom);

//	String fileSax = "/Users/lkedves/work/Giskard/data/National_Wild_and_Scenic_River_Lines_(Feature_Layer).geojson";
//	String fileSax = "/Users/lkedves/work/Giskard/data/Current_Invasive_Plants_(Feature_Layer).geojson";
//	String fileSax = "/Users/lkedves/git/rtms/milestone00/RtmsFrontend/out/scanAll.json";
		String fSax = "Knowledge/Json/countries.geojson";
		Object rSax = testSax(fSax);
		Giskard.log(MiNDEventLevel.INFO, "SAX File read success", fSax, ", result:\n", rSax);

		String fAbnf = "Knowledge/Abnf/abnf.abnf";
//		String fileName = "Knowledge/Abnf/json.abnf";
		Object rAbnf = testAbnf(fAbnf);
		Giskard.log(MiNDEventLevel.INFO, "ABNF File read success", fAbnf, ", result:\n", rAbnf);

		TestIOReader.testReader("test.txt", "UTF8");
	}

	public String extendName(String fileName) {
		if ( !fileName.startsWith("/") ) {
			String root = GiskardUtils.getRoot();

			if ( !GiskardUtils.isEmpty(root) ) {
				fileName = root + "/" + fileName;
			}
		}
		return fileName;
	}

	public Reader getReader(String fName) throws Exception {
		fName = extendName(fName);
		Giskard.log(MiNDEventLevel.TRACE, "Start reading file", fName, "...");
		return new FileReader(fName);
	}

	public Object testSax(String fName) throws Exception {
		JSONParser p = new JSONParser();
		DustIOJsonReader.JsonContentDispatcher h = new DustIOJsonReader.JsonContentDispatcher(GiskardUtils.LOGGER);

		p.parse(getReader(fName), h);
		return h;
	}

	public Object testDom(String fName) throws Exception {
		DustIODomReaderState rs = new DustIODomReaderState(getReader(fName));

		while (rs.step()) {

		}

		return rs;
	}

	public Object testAbnf(String fName) throws Exception {
		fName = extendName(fName);

		Giskard.log(MiNDEventLevel.TRACE, "Start reading file", fName, "...");
		AbnfParserPrototype pp = new AbnfParserPrototype(fName);
		return pp;
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		MiNDResultType ret = MiNDResultType.ACCEPT_PASS;
		
		switch ( action ) {
		case Process:
			runTest();
			ret = MiNDResultType.ACCEPT;
			break;

		default:
			break;
		}
		
		return ret;
	}

}
