package me.giskard.dust.sandbox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustSandboxMachine extends Dust.Machine implements DustSandboxConsts {
	
	DustUtilsFactory<DustSandboxHandle, Map> knowledge = new DustUtilsFactory(MAP_CREATOR);

	private DustCreator crHandle = new DustCreator() {
		@Override
		public DustSandboxHandle create(Object key, Object... hints) {
			return new DustSandboxHandle(hints[0] + ":" + key);
		}
	};

	private DustCreator crUnit = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(crHandle);
		}
	};

	private DustCreator crAuthor = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(crUnit);
		}
	};

	DustUtilsFactory handleCatalog = new DustUtilsFactory(crAuthor);
	
	
	
	private DustCreator crLang = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(MAP_CREATOR);
		}
	};

	DustUtilsFactory vocabulary = new DustUtilsFactory(crLang);
	
	

	public DustSandboxHandle resolve(String author, String unit, String id) {
		DustUtilsFactory<String, DustSandboxHandle> hf = (DustUtilsFactory<String, DustSandboxHandle>) ((DustUtilsFactory) handleCatalog
				.get(author)).get(unit);
		String uref = author + ":" + unit;

		DustSandboxHandle h = hf.get(id, uref);

		return h;
	}

	public void set(DustSandboxHandle hTarget, DustSandboxHandle hAtt, char attType, Object key, Object val) {
		Map m = knowledge.get(hTarget);
		Object v = m.get(hAtt);

		if (null == v) {
			switch (attType) {
			case 's':
				v = new HashSet();
				break;
			case 'a':
				v = new ArrayList();
				break;
			case 'm':
				v = new HashMap();
				break;
			}

			if (null != v) {
				m.put(hAtt, v);
			}
		}

		switch (attType) {
		case 'o':
			m.put(hAtt, val);
			break;
		case 's':
			((Set) v).add(val);
			break;
		case 'a':
			ArrayList al = (ArrayList) v;
			int idx = (Integer) key;
			DustUtils.ensureSize(al, idx);
			al.set(idx, val);
			break;
		case 'm':
			((Map) v).put(key, val);
			break;
		}

//		Dust.log(null, "Set", hTarget, hAtt, key, val);
	}

	public void setText(EnumMap<TextInfo, Object> txtData) {
		DustUtilsFactory fType = (DustUtilsFactory) vocabulary.get(txtData.get(TextInfo.txtLang));
		Map texts = (Map) fType.get(txtData.get(TextInfo.txtType));
		
		texts.put(txtData.get(TextInfo.owner), txtData.get(TextInfo.text));
		
		if ( null == DustSandboxHandle.TO_STRING ) {
			 DustSandboxHandle.TO_STRING = texts;
		}
		
//		Dust.log(null, "Set text", txtData);
	}

	@Override
	protected <RetType> RetType access(MindAccess cmd, Object val, Object... path) {
		switch ( cmd ) {
		case Broadcast:
			StringBuilder sb = DustUtils.sbAppend(null, " ", false, path);
			System.out.println(val + " " + sb);
			break;
		case Check:
			break;
		case Commit:
			break;
		case Delete:
			break;
		case Get:
			break;
		case Insert:
			break;
		case Lookup:
			break;
		case Peek:
			break;
		case Reset:
			break;
		case Set:
			break;
		case Visit:
			break;
		default:
			break;
		}
		
		return null;
	}
}
