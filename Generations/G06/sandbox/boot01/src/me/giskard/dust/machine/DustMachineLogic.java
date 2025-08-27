package me.giskard.dust.machine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.giskard.dust.Dust;
import me.giskard.dust.DustConsts;
import me.giskard.dust.DustException;
import me.giskard.dust.utils.DustUtils;
import me.giskard.dust.utils.DustUtilsConsts;
import me.giskard.dust.utils.DustUtilsEnumTranslator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DustMachineLogic extends DustConsts.MindDialog implements DustMachineConstsInt, DustMachineBootConsts, DustUtilsConsts {

	private static ThreadLocal<Map<MindHandle, Object>> DATA = new ThreadLocal<Map<MindHandle, Object>>() {
		protected java.util.Map<MindHandle, Object> initialValue() {
			return new HashMap<>();
		};
	};

	static Map getData() {
		return DATA.get();
	};

	static Map setData(Map data) {
		Map ret = DATA.get();
		DATA.set(data);
		return ret;
	}

	Map<MindHandle, Object> machineData;

	UnitLoader jsonFormatter;

	public DustMachineLogic(Map data, Map<MindHandle, Object> machineData) {
		setData(data);
		this.machineData = machineData;
		jsonFormatter = new DustMachineLogicFormatterJson();
	}

	@Override
	public MindHandle lookup(MindHandle unitHandle, String id, String lang, String token) {
		Map data = DATA.get();

		MindHandle ret = null;
		Map dialogIdeas = DustUtils.simpleGet(data, DIALOG_IDEAS);

		if (null == unitHandle) {
			Map units = DustUtils.simpleGet(data, MACHINE_UNITS);

			int sep = id.indexOf(DUST_SEP_ID);

			String k = null;

			if (-1 != sep) {
				k = id.substring(sep + 1);
				id = id.substring(0, sep);
			}

			ret = DustUtils.safeGet(units, id, new DustCreator<DustHandle>() {
				@Override
				public DustHandle create(Object key, Object... hints) {
					DustHandle ret = new DustHandle((String) key);
					DustMachineUtils.storeHandle(ret, dialogIdeas);
					return ret;
				}
			});

			if (null == Dust.access(ACCESS_PEEK, null, ret, PERS_ID)) {
				try {
					jsonFormatter.optLoadUnit(id);
				} catch (Exception e) {
					DustException.swallow(e, "Reading unit", id);
				}
			}

			if (null == k) {
				return ret;
			} else {
				unitHandle = ret;
				id = k;
			}
		}

		Map<MindHandle, Object> unitData = DustUtils.simpleGet(dialogIdeas, unitHandle, unitHandle);
		Map<String, MindHandle> unitHandles = DustUtils.simpleGet(unitData, UNIT_HANDLES);

		if ("?".equals(id)) {
			id = DustMachineUtils.nextId(unitData, UNIT_NEXT_ID);
		}

		ret = DustUtils.safeGet(unitHandles, id, new DustCreator<DustHandle>() {
			@Override
			public DustHandle create(Object key, Object... hints) {
				DustHandle ret = new DustHandle((MindHandle) hints[0], (String) key);
				DustMachineUtils.storeHandle(ret, dialogIdeas);
				return ret;
			}
		}, unitHandle);

		if (!DustUtils.isEmpty(token)) {
			Map vocabulary = DustUtils.simpleGet(data, DIALOG_VOCABULARY);
			DustMachineUtils.storeToken((DustHandle) ret, dialogIdeas, vocabulary, lang, token);
		}

		return ret;
	}

	@Override
	public <RetType> RetType access(MindHandle cmd, Object val, Object root, Object... path) {
		Map data = DATA.get();

		Object ret = null;
		MindAccess access = DustUtilsEnumTranslator.getEnum(cmd, null);

		boolean createIfMissing = DustMachineUtils.isCreator(access);

		Object curr = (null == root) ? data : root;
		DustHandle hLastItem = null;

		Object prev = null;
		Object lastKey = null;

		Object prevColl = null;
		MindCollType collType = null;

		boolean setLastAtt = true;
		DustHandle hLastAtt = null;
		Map kAtt = null;

		for (Object p : path) {
			if (curr instanceof DustHandle) {
				hLastItem = (DustHandle) curr;
				curr = resolveHandleToIdea(data, hLastItem, createIfMissing);
				setLastAtt = true;
			} else if (null == curr) {
				if (createIfMissing) {
					curr = (p instanceof Integer) ? new ArrayList() : new HashMap();

					if (null != prevColl) {
						switch (collType) {
						case Arr:
							DustUtils.safePut((ArrayList) prevColl, (Integer) lastKey, val, false);
							break;
						case Map:
							((Map) prevColl).put(lastKey, curr);
							break;
						case One:
							break;
						case Set:
							((Set) prevColl).add(curr);
							break;
						}
					}
				} else {
					break;
				}
			}

			prev = curr;
			collType = DustMachineUtils.getCollType(prev);
			prevColl = (null == collType) ? null : prev;

			lastKey = p;

			if (setLastAtt) {
				setLastAtt = false;
				if (p instanceof DustHandle) {
					hLastAtt = (DustHandle) p;
					kAtt = resolveHandleToIdea(data, hLastAtt, false);
				}
			}

			if (curr instanceof ArrayList) {
				ArrayList al = (ArrayList) curr;
				Integer idx = (Integer) p;

				if ((KEY_SIZE == idx)) {
					curr = al.size();
				} else if ((KEY_ADD == idx) || (idx >= al.size())) {
					curr = null;
				} else {
					curr = al.get(idx);
				}
			} else if (curr instanceof Map) {
				curr = DustUtils.isEqual(KEY_SIZE, p) ? ((Map) curr).size() : ((Map) curr).get(p);
			} else {
				curr = null;
			}

			if ((null == curr) && createIfMissing && (null != hLastAtt)) {
				DustHandle factoryMsg = DustUtils.simpleGet(machineData, MEMBER_FACTORIES, hLastAtt);
				if (null != factoryMsg) {
					curr = doCommit(data, factoryMsg);
				}
			}
		}

		switch (access) {
		case Check:
			ret = DustUtils.isEqual(val, curr);
			break;
		case Commit:
			ret = doCommit(data, (DustHandle) curr);
			break;
		case Delete:
			break;
		case Get:
			ret = (null == curr) ? val : curr;
			break;
		case Insert:
			if (!DustUtils.isEqual(curr, val) && (null != prevColl)) {
				switch (collType) {
				case Arr:
					DustUtils.safePut((ArrayList) prevColl, (Integer) lastKey, val, false);
					break;
				case Map:
					Set s = (curr instanceof Set) ? (Set) curr : new HashSet();
					s.add(val);
					((Map) prevColl).put(lastKey, s);
					break;
				case One:
					break;
				case Set:
					((Set) prevColl).add(curr);
					break;
				}
			}
			break;
		case Peek:
			ret = (null == curr) ? val : curr;
			break;
		case Reset:
			if (curr instanceof Map) {
				((Map) curr).clear();
			} else if (curr instanceof Collection) {
				((Collection) curr).clear();
			}
			break;
		case Set:
			if ((null != lastKey) && (null != prevColl)) {
				Dust.log(null, "       ", access, hLastItem, lastKey, val);
				switch (collType) {
				case Arr:
					DustUtils.safePut((ArrayList) prevColl, (Integer) lastKey, val, true);
					break;
				case Map:
					if (!DustUtils.isEqual(curr, val)) {
						((Map) prevColl).put(lastKey, val);
					}
					break;
				case One:
					break;
				case Set:
					((Set) prevColl).add(curr);
					break;
				}
			}

			break;
		case Visit:
			doVisit(val, hLastItem, lastKey, curr);
			break;
		}

		return (RetType) ret;
	}

	private Object doCommit(Map data, DustHandle hMessage) {
		Object ret = null;

		Map item = resolveHandleToIdea(data, hMessage, false);
		Collection<DustHandle> listeners = DustUtils.simpleGet(item, IDEA_LISTENERS);

		if (null != listeners) {

			for (DustHandle l : listeners) {
				Map listener = resolveHandleToIdea(data, l, false);

				MindLogic binLogic = DustUtils.simpleGet(listener, AGENT_BINARY);
				boolean firstCall = (null == binLogic);
				if (firstCall) {
					DustHandle hLogic = DustUtils.simpleGet(listener, AGENT_LOGIC);
					// TODO safeGet here
					Map resolved = DustUtils.safeGet(machineData, LOGIC_BINARY, MAP_CREATOR);
					
					binLogic = DustUtils.safeGet(resolved, hLogic, new DustCreator<MindLogic>() {
						@SuppressWarnings("deprecation")
						@Override
						public MindLogic create(Object key, Object... hints) {
							Map<String, DustHandle> modules = DustUtils.simpleGet(machineData, MACHINE_MODULES);
							
							for ( DustHandle hModule : modules.values() ) {
								String cName = DustUtils.simpleGet(resolveHandleToIdea(data, hModule, false), LOGIC_CLASSNAME, hLogic);
								if ( null != cName ) {
									try {
										return (MindLogic) Class.forName(cName).newInstance();
									} catch (Throwable e) {
										DustException.swallow(e, "creating logic", cName);
									}
								}
							}
							return null;
						}
					});
//					binLogic = access(MIND_TAG_ACCESS_GET, null, machineData, LOGIC_BINARY, hLogic);
					listener.put(AGENT_BINARY, binLogic);
				}

				if ( null != binLogic ) 
				{
					Object hSelf = data.get(AGENT_SELF);
					Object hParam = data.get(AGENT_PARAM);
					try {
						if (firstCall) {
							binLogic.logicProcess(MIND_TAG_ACTION_INIT);
						}
						binLogic.logicProcess(MIND_TAG_ACTION_PROCESS);
					} catch (Exception e) {
						DustException.wrap(e, "While calling", binLogic, listener, hMessage);
					} finally {
						data.put(AGENT_SELF, hSelf);
						data.put(AGENT_PARAM, hParam);
					}
				}
			}
		}

		return ret;
	}

	private Map resolveHandleToIdea(Map data, DustHandle hItem, boolean createIfMissing) {
		Map m = DustUtils.simpleGet(data, DIALOG_IDEAS);
		m = DustUtils.safeGet(m, hItem.unit, (createIfMissing ? MAP_CREATOR : null));
		return (null == m) ? null : DustUtils.safeGet(m, hItem, (createIfMissing ? MAP_CREATOR : null));
	}

	private void doVisit(Object val, DustHandle hLastItem, Object lastKey, Object curr) {
		// TODO visit
	}

	@Override
	public void broadcast(MindHandle event, Object... params) {
		Dust.log(event, params);
	}

	@Override
	public MindHandle logicProcess(MindHandle action) throws Exception {
		return null;
	}

}
