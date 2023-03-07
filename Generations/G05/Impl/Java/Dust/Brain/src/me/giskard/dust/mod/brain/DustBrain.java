package me.giskard.dust.mod.brain;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import me.giskard.mind.GiskardMind;
import me.giskard.mind.GiskardUtils;

@SuppressWarnings("rawtypes")
public class DustBrain extends DustBrainBase {
	File root;

	public void setRoot(File root) throws Exception {
		this.root = root;
		
		GiskardMind.dump(root.getCanonicalPath(), root.isDirectory());
	}
	
	@Override
	protected Map loadContext(String token) throws Exception {
		File f = new File(root, token + EXT_JSON);

		JSONParser p = new JSONParser();
		JSONArray a = (JSONArray) p.parse(new FileReader(f));
		
		return (Map) a.get(1);
	}
	
	@Override
	protected void testSomething() throws Exception {
		super.testSomething();
		BootGui.showGui(bootConn);
	}
	
	@Override
	public MindStatus agentExecAction(MindAction action) throws Exception {
		log(action);
		
		switch ( action ) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			initBrain();
			break;
		case Process:
			break;
		case Release:
			break;
		}
		
		return MindStatus.Accept;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <RetType> RetType access(MindAccess cmd, Object val, Object root, Object... path) {
		
		Object ret = val;
		
		return (RetType) ret;
	}

	@Override
	public void log(Object event, Object... params) {
		GiskardUtils.dump(", ", false, event, params);
	}

}
