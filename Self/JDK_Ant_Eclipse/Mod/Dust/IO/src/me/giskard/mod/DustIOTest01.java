package me.giskard.mod;

import org.json.simple.parser.JSONParser;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.dust.io.DustIOConsts;
import me.giskard.dust.io.DustIOUtils;
import me.giskard.dust.io.abnf.AbnfParserPrototype;
import me.giskard.dust.io.json.DustIODomReaderState;
import me.giskard.dust.io.json.DustIOJsonReader;

public class DustIOTest01 implements DustIOConsts, GiskardConsts.MiNDAgent {

	void runTest() throws Exception {

		String fDom = "Knowledge/Json/System.json";
		Object rDom = testDom(fDom);
		Giskard.log(MiNDEventLevel.Info, "DOM File read success", fDom, ", result:\n", rDom);

//	String fileSax = "/Users/lkedves/work/Giskard/data/National_Wild_and_Scenic_River_Lines_(Feature_Layer).geojson";
//	String fileSax = "/Users/lkedves/work/Giskard/data/Current_Invasive_Plants_(Feature_Layer).geojson";
//	String fileSax = "/Users/lkedves/git/rtms/milestone00/RtmsFrontend/out/scanAll.json";

//		String fSax = "Knowledge/Json/countries.geojson";
//		Object rSax = testSax(fSax);
//		Giskard.log(MiNDEventLevel.Info, "SAX File read success", fSax, ", result:\n", rSax);

		String fAbnf = "Knowledge/Abnf/abnf.abnf";
//		String fileName = "Knowledge/Abnf/json.abnf";
		Object rAbnf = testAbnf(fAbnf);
		Giskard.log(MiNDEventLevel.Info, "ABNF File read success", fAbnf, ", result:\n", rAbnf);

		TestIOReader.testReader("test.txt", "UTF8");
	}

	public Object testSax(String fName) throws Exception {
		JSONParser p = new JSONParser();
		DustIOJsonReader.JsonContentDispatcher h = new DustIOJsonReader.JsonContentDispatcher(GiskardUtils.LOGGER);

		p.parse(DustIOUtils. getReader(fName), h);
		return h;
	}

	public Object testDom(String fName) throws Exception {
		DustIODomReaderState rs = new DustIODomReaderState(DustIOUtils.getReader(fName));

		while (rs.step()) {

		}

		return rs;
	}

	public Object testAbnf(String fName) throws Exception {
		fName = DustIOUtils.extendName(fName);

		Giskard.log(MiNDEventLevel.Trace, "Start reading file", fName, "...");
		AbnfParserPrototype pp = new AbnfParserPrototype(fName);
		return pp;
	}

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		MiNDResultType ret = MiNDResultType.Accept;

		runTest();

		return ret;
	}

}
