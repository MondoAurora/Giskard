package me.giskard.dust.mod.brain;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
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
	protected Map loadUnitContent(String token, String lang) throws Exception {
		File f = new File(root, token + SEP + lang + EXT_JSON);

		JSONParser p = new JSONParser();
		JSONArray a = (JSONArray) p.parse(new FileReader(f));

		return (Map) a.get(1);
	}
	
	@Override
	protected void saveLanguage(String lang) throws Exception {
		DustBrainHandle hUnit = brain.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memBrainLanguages), MindColl.Map, lang, null, null);
		saveUnit(lang, hUnit);
	}

	@Override
	protected void saveUnitContent(String token) throws Exception {
		DustBrainHandle hUnit = brain.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memBrainUnits), MindColl.Map, token, null, null);
		saveUnit(token, hUnit);
	}

	@SuppressWarnings("unchecked")
	protected void saveUnit(String name, DustBrainHandle hUnit) throws Exception {
		KnowledgeItem unit = DustBrainBootUtils.resolveHandle(brain, hUnit, null);

		Map content = new HashMap();

		Iterable<KnowledgeItem> unitItems = unit.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memUnitLocalKnowledge), MindColl.Map, KEY_ITER, null, null);

		Map urefs = new HashMap();

		for (KnowledgeItem item : unitItems) {
			boolean unitRef = item.access(MindAccess.Check, GiskardUtils.getHandle(BootToken.memKnowledgeTags), MindColl.Map, GiskardUtils.getHandle(BootToken.tagProxy),
					GiskardUtils.getHandle(BootToken.tagProxyUnit), null);

			if ( unitRef ) {
				Object id = item.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeID), MindColl.One, null, null, null);
				Object hu = item.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memProxyRemote), MindColl.One, null, null, null);

				urefs.put(hu, id);
			}
		}

		for (KnowledgeItem item : unitItems) {
			Map data = new HashMap();
			Object val = null;

			Iterable<DustBrainHandle> members = item.access(MindAccess.Peek, null, MindColl.Map, KEY_ITER, null, null);

			for (DustBrainHandle hMem : members) {

				if ( hMem == GiskardUtils.getHandle(BootToken.memKnowledgeID) ) {
					val = item.access(MindAccess.Peek, hMem, MindColl.One, null, null, null);
					content.put(GiskardUtils.toString(val), data);
				} else {
					KnowledgeItem mi = DustBrainBootUtils.resolveBrainHandle(brain, hMem);

					boolean tr = mi.access(MindAccess.Check, GiskardUtils.getHandle(BootToken.memKnowledgeTags), MindColl.Map, GiskardUtils.getHandle(BootToken.tagTransient),
							GiskardUtils.getHandle(BootToken.tagTransient), null);
					if ( tr ) {
						continue;
					}

					Object memId = optResolve(hUnit, unit, hMem, urefs, content);

					DustBrainHandle coll = mi.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeTags), MindColl.Map, GiskardUtils.getHandle(BootToken.tagColl),
							GiskardUtils.getHandle(BootToken.tagCollOne), null);
					BootToken bt = GiskardUtils.getEnum(coll, BootToken.tagCollOne);

					switch ( bt ) {
					case tagCollOne:
						val = item.access(MindAccess.Peek, hMem, MindColl.One, null, null, null);
						val = optResolve(hUnit, unit, val, urefs, content);
						break;
					case tagCollSet:
					case tagCollArr:
						ArrayList arr = new ArrayList();
						
						Iterable values = item.access(MindAccess.Peek, hMem, MindColl.Set, KEY_ITER, null, null);
						for ( Object v : values ) {
							arr.add(optResolve(hUnit, unit, v, urefs, content));
						}
						val = arr;
						
						break;
					case tagCollMap:
						Map map = new HashMap<>();
						
						Iterable mv = item.access(MindAccess.Peek, hMem, MindColl.Map, KEY_ITER, KEY_KEYS, null);
						for ( Object v : mv ) {
							val = item.access(MindAccess.Peek, hMem, MindColl.Map, v, null, null);
							map.put(optResolve(hUnit, unit, v, urefs, content), optResolve(hUnit, unit, val, urefs, content));
						}
						val = map;
						break;

					default:
						break;
					}

					if ( null != val ) {
						data.put(memId, val);
					}
				}
			}
		}

		ArrayList top = new ArrayList<>();
		Map header = new HashMap();

		header.put("syntax", "dust_json");
		header.put("version", "1_0");

		top.add(header);
		top.add(content);

		FileWriter f = new FileWriter(new File(root, name + EXT_JSON));
		JSONValue.writeJSONString(top, f);

		f.close();

	}

	@SuppressWarnings("unchecked")
	private Object optResolve(DustBrainHandle hUnit, KnowledgeItem unit, Object val, Map urefs, Map content) {
		Object ret = val;

		if ( val instanceof MindHandle ) {
			KnowledgeItem ki = DustBrainBootUtils.resolveBrainHandle(brain, (MindHandle) val);
			if ( null == ki ) {
				ki = DustBrainBootUtils.resolveBrainHandle(unit, (MindHandle) val);
			}
			String memId = GiskardUtils.toString(ki.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeID), MindColl.One, null, null, null));
			DustBrainHandle memUnit = ki.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeUnit), MindColl.One, null, null, null);

			if ( memUnit != hUnit ) {
				Object unitId = urefs.get(memUnit);
				
				if ( null == unitId ) {
					KnowledgeItem ku = DustBrainBootUtils.resolveBrainHandle(brain, memUnit);
					unitId = ku.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memKnowledgeToken), MindColl.One, null, null, null);
					urefs.put(memUnit, unitId);
				}
				
				memId = unitId + SEP + memId;
			}

			ret = memId;
		}

		return ret;
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
			BootGui.showGui(brain);
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
