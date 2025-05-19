package me.giskard.dust.machine;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.machine.sandbox.SandboxHandleFormatter;
import me.giskard.dust.machine.sandbox.SandboxUnitLoader;
import me.giskard.dust.utils.DustUtils;
import me.giskard.event.DustEventTokens;
import me.giskard.mind.DustMindTokens;

@SuppressWarnings({ "unchecked" })
public class DustMachineLogicNode implements DustMachineConsts, Dust.MindDialog, DustMindTokens, DustEventTokens {

	DustMachineToken hUnitHandles;
	DustMachineToken hUnitContent;

//	DustMachineKnowledgeItem mainKnowledge;
	SandboxUnitLoader unitLoader;
	SandboxHandleFormatter sbFmt;
	
	DustMachineLogicIdea lIdea;

	final ThreadLocal<Map<MindToken, Object>> dialogs = new ThreadLocal<Map<MindToken, Object>>() {
		@Override
		protected Map<MindToken, Object> initialValue() {
			return new TreeMap<>();
		}
	};

	public DustMachineLogicNode(DustMachineLogicIdea lIdea, Map<MindToken, Object> bootKnowledge, DustMachineToken hUnitHandles, DustMachineToken hUnitContent) {
		this.lIdea = lIdea;

		dialogs.set(bootKnowledge);

		this.hUnitHandles = hUnitHandles;
		this.hUnitContent = hUnitContent;

		unitLoader = new SandboxUnitLoader(this);
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
			String[] spl = lid.split(DUST_SEP_ID);

			Map<MindToken, Object> kiMachine = dialogs.get();

			boolean inUnit = spl.length > 2;
			DustMachineToken h = lIdea.get(kiMachine, hUnitHandles, MindCollType.Map, inUnit ? spl[0] + DUST_SEP_ID + spl[1] : lid, new DustCreator<DustMachineToken>() {
				@Override
				public DustMachineToken create(Object key, Object... hints) {
					DustMachineToken hUnit = new DustMachineToken(kiMachine, (String) key);

					loadUnit(hUnit);

					return hUnit;
				}
			});

			if (inUnit) {
				Map<MindToken, Object> kiUnit = resolveItem(kiMachine, h);
				h = resolveHandle(kiUnit, lid);
			}

			ret = h;
		}

		return (RetType) ret;
	}

	protected void loadUnit(DustMachineToken hUnit) {
		Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Now would load", hUnit);
		try {
			unitLoader.loadUnits(hUnit.toString());
			Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Load success", hUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DustMachineToken resolveHandle(Map<MindToken, Object> kiUnit, String lookupId) {
		DustMachineToken h = lIdea.get(kiUnit, hUnitHandles, MindCollType.Map, lookupId, new DustCreator<DustMachineToken>() {
			@Override
			public DustMachineToken create(Object key, Object... hints) {
				DustMachineToken hUnit = new DustMachineToken(kiUnit, (String) key);
				return hUnit;
			}
		});

		return h;
	}

	public Map<MindToken, Object> resolveItem(Map<MindToken, Object> kiUnit, DustMachineToken h) {
		Map<MindToken, Object> ki = lIdea.get(kiUnit, hUnitContent, MindCollType.Map, h, new DustCreator<Map<MindToken, Object>>() {
			@Override
			public Map<MindToken, Object> create(Object key, Object... hints) {
				return new TreeMap<>();
			}
		});

		return ki;
	}

	@Override
	public MindToken agentInit() throws Exception {
		DustMachineBoot.loadBootModules();

		sbFmt = new SandboxHandleFormatter(unitLoader);
		DustMachineToken.setFormatter(sbFmt);

		Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Machine initialized");
		return TOKEN_MIND_RESULT_ACCEPT;
	}

	public void set(DustMachineToken hTarget, DustMachineToken hAtt, Object val, MindCollType ct, Object key) {
		Map<MindToken, Object> ki = resolveItem(hTarget.getUnitItem(), hTarget);

		lIdea.set(ki, hAtt, val, ct, key);

	}

}
