package me.giskard.dust.machine;

import me.giskard.dust.Dust;
import me.giskard.dust.machine.sandbox.SandboxHandleFormatter;
import me.giskard.dust.machine.sandbox.SandboxUnitLoader;
import me.giskard.dust.utils.DustUtils;
import me.giskard.event.DustEventTokens;
import me.giskard.mind.DustMindTokens;

@SuppressWarnings({ "unchecked" })
public class DustMachineNarrative implements DustMachineConsts, Dust.MindDialog, DustMindTokens, DustEventTokens {

	DustMachineHandle hUnitHandles;
	DustMachineHandle hUnitContent;

//	DustMachineKnowledgeItem mainKnowledge;
	SandboxUnitLoader unitLoader;
	SandboxHandleFormatter sbFmt;

	final ThreadLocal<DustMachineKnowledgeItem> dialogs = new ThreadLocal<DustMachineKnowledgeItem>() {
		@Override
		protected DustMachineKnowledgeItem initialValue() {
			return new DustMachineKnowledgeItem(null);
		}
	};

	public DustMachineNarrative(DustMachineKnowledgeItem bootKnowledge, DustMachineHandle hUnitHandles, DustMachineHandle hUnitContent) {
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

			DustMachineKnowledgeItem kiMachine = dialogs.get();

			boolean inUnit = spl.length > 2;
			DustMachineHandle h = kiMachine.get(hUnitHandles, MindCollType.Map, inUnit ? spl[0] + DUST_SEP_ID + spl[1] : lid, new DustCreator<DustMachineHandle>() {
				@Override
				public DustMachineHandle create(Object key, Object... hints) {
					DustMachineHandle hUnit = new DustMachineHandle(kiMachine, (String) key);

					loadUnit(hUnit);

					return hUnit;
				}
			});

			if (inUnit) {
				DustMachineKnowledgeItem kiUnit = resolveItem(kiMachine, h);
				h = resolveHandle(kiUnit, lid);
			}

			ret = h;
		}

		return (RetType) ret;
	}

	protected void loadUnit(DustMachineHandle hUnit) {
		Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Now would load", hUnit);
		try {
			unitLoader.loadUnits(hUnit.toString());
			Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Load success", hUnit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DustMachineHandle resolveHandle(DustMachineKnowledgeItem kiUnit, String lookupId) {
		DustMachineHandle h = kiUnit.get(hUnitHandles, MindCollType.Map, lookupId, new DustCreator<DustMachineHandle>() {
			@Override
			public DustMachineHandle create(Object key, Object... hints) {
				DustMachineHandle hUnit = new DustMachineHandle(kiUnit, (String) key);
				return hUnit;
			}
		});

		return h;
	}

	public DustMachineKnowledgeItem resolveItem(DustMachineKnowledgeItem kiUnit, DustMachineHandle h) {
		DustMachineKnowledgeItem ki = kiUnit.get(hUnitContent, MindCollType.Map, h, new DustCreator<DustMachineKnowledgeItem>() {
			@Override
			public DustMachineKnowledgeItem create(Object key, Object... hints) {
				return new DustMachineKnowledgeItem((DustMachineHandle) key);
			}
		});

		return ki;
	}

	@Override
	public MindToken agentInit() throws Exception {
		DustMachineBoot.loadBootModules();

		sbFmt = new SandboxHandleFormatter(unitLoader);
		DustMachineHandle.setFormatter(sbFmt);

		Dust.broadcast(TOKEN_EVENT_TYPE_INFO, "Machine initialized");
		return TOKEN_MIND_RESULT_ACCEPT;
	}

	public void set(DustMachineHandle hTarget, DustMachineHandle hAtt, Object val, MindCollType ct, Object key) {
		DustMachineKnowledgeItem ki = resolveItem(hTarget.getUnitItem(), hTarget);

		ki.set(hAtt, val, ct, key);

	}

}
