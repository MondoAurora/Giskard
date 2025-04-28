package me.giskard.dust.sandbox;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsFactory;
import me.giskard.event.DustEventTokens;
import me.giskard.mind.DustMindTokens;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustSandboxMachine implements Dust.MindDialog, DustSandboxConsts, DustMindTokens, DustEventTokens {

	private DustUtilsFactory<DustSandboxHandle, Map> knowledge = new DustUtilsFactory(MAP_CREATOR);

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
			return new DustUtilsFactory(crUnit) {
				protected void initNew(Object item, Object key, Object... hints) {
					String uref = hints[0] + ":" + key;

					try {
						unitLoader.loadUnits(DustSandboxMachine.this, uref);
					} catch (Exception e) {
						DustException.wrap(e, "Autoloading failed for unit", uref);
					}
				};
			};
		}
	};

	private DustUtilsFactory<String, DustUtilsFactory> handleCatalog = new DustUtilsFactory(crAuthor);

	private DustCreator crLang = new DustCreator() {
		@Override
		public DustUtilsFactory create(Object key, Object... hints) {
			return new DustUtilsFactory(MAP_CREATOR);
		}
	};
	private DustUtilsFactory vocabulary = new DustUtilsFactory(crLang);

	private String language;
	private DustSandboxUnitLoader unitLoader;

	public DustSandboxMachine(String language, File fUnit) throws Exception {
		this.language = language;
		this.unitLoader = new DustSandboxUnitLoader(fUnit);
	}

	@Override
	public MindToken agentInit() throws Exception {
		Dust.broadcast(TOKEN_EVENT_TYPE_TRACE, "Machine run called");
		return TOKEN_MIND_RESULT_ACCEPT;
	}

	public String getLanguage() {
		return language;
	}

	public DustSandboxHandle resolve(String uref, String id) {
		String[] spl = uref.split(":");

		DustUtilsFactory<String, DustSandboxHandle> hf = (DustUtilsFactory<String, DustSandboxHandle>) ((DustUtilsFactory) handleCatalog.get(spl[0])).get(spl[1],
				spl[0]);

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
	}

	public void setText(EnumMap<TextInfo, Object> txtData) {
		DustUtilsFactory fType = (DustUtilsFactory) vocabulary.get(txtData.get(TextInfo.txtLang));
		Map texts = (Map) fType.get(txtData.get(TextInfo.txtType));

		texts.put(txtData.get(TextInfo.owner), txtData.get(TextInfo.text));

		if (null == DustSandboxHandle.TO_STRING) {
			DustSandboxHandle.TO_STRING = texts;
		}
	}

	@Override
	public void broadcast(MindToken event, Object... params) {
		StringBuilder sb = DustUtils.sbAppend(null, " ", false, params);
		System.out.println(event + " " + sb);
	}

	@Override
	public <RetType> RetType access(MindToken cmd, Object val, Object... path) {
		Object ret = null;

		if (null == cmd) {
			String lid = (String) val;
			int ii = lid.lastIndexOf(":");
			ret = resolve(lid.substring(0, ii), lid.substring(ii + 1));
		}

		return (RetType) ret;
	}

	public void test() {
		Dust.broadcast(TOKEN_EVENT_TYPE_WARNING, "pompom");
	}

	public Iterable<String> getAuthors() {
		return handleCatalog.keys();
	}

	public DustUtilsFactory<String, DustUtilsFactory> getAuthorUnits(String author) {
		return handleCatalog.peek(author);
	}

	public void loadAllUnits() throws Exception {
		unitLoader.loadAllUnits(this);
	}
}
