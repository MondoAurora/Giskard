package me.giskard.dust.machine;

import me.giskard.dust.Dust;
import me.giskard.dust.machine.sandbox.DustMachineSandboxUnitLoader;
import me.giskard.dust.utils.DustUtils;
import me.giskard.event.DustEventHandles;
import me.giskard.mind.DustMindHandles;

@SuppressWarnings({ "unchecked" })
public class DustMachineNarrative implements DustMachineConsts, Dust.MindMachine, DustMindHandles, DustEventHandles {

	DustMachineHandle hUnitHandles;
	DustMachineHandle hUnitContent;

//	DustMachineKnowledgeItem mainKnowledge;
	DustMachineSandboxUnitLoader unitLoader;

	final ThreadLocal<DustMachineKnowledgeItem> dialogs = new ThreadLocal<DustMachineKnowledgeItem>() {
		@Override
		protected DustMachineKnowledgeItem initialValue() {
			return new DustMachineKnowledgeItem(null);
		}
	};

	public DustMachineNarrative(DustMachineKnowledgeItem bootKnowledge, DustMachineHandle hUnitHandles,
			DustMachineHandle hUnitContent) {
		dialogs.set(bootKnowledge);
		
		this.hUnitHandles = hUnitHandles;
		this.hUnitContent = hUnitContent;
		
		unitLoader = new DustMachineSandboxUnitLoader(this);
	}

	@Override
	public <RetType> RetType access(MindAccess cmd, Object val, Object... path) {
		Object ret = null;

		switch (cmd) {
		case Broadcast:
			StringBuilder sb = DustUtils.sbAppend(null, " ", false, path);
			System.out.println(val + " " + sb);
			break;
		case Lookup:
			String lid = (String) val;
			String[] spl = lid.split(DUST_SEP_ID);

			DustMachineKnowledgeItem kiMachine = dialogs.get();

			boolean inUnit = spl.length > 2;
			DustMachineHandle h = kiMachine.get(hUnitHandles, MindCollType.Map, inUnit ? spl[0] + DUST_SEP_ID + spl[1] : lid,
					new DustCreator<DustMachineHandle>() {
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
		case Peek:
			break;
		case Reset:
			break;
		case Set:
			break;
		case Visit:
			break;
		}

		return (RetType) ret;
	}

	protected void loadUnit(DustMachineHandle hUnit) {
		Dust.log(DH_EVENT_TYPE_INFO, "Now would load", hUnit);
		try {
			unitLoader.loadUnits(hUnit.toString());
			Dust.log(DH_EVENT_TYPE_INFO, "Load success", hUnit);
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
		DustMachineKnowledgeItem ki = kiUnit.get(hUnitContent, MindCollType.Map, h,
				new DustCreator<DustMachineKnowledgeItem>() {
					@Override
					public DustMachineKnowledgeItem create(Object key, Object... hints) {
						return new DustMachineKnowledgeItem((DustMachineHandle) key);
					}
				});

		return ki;
	}

	@Override
	public MindResponse execute(MindAction action) throws Exception {
		String log = null;

		switch (action) {
		case Begin:
			break;
		case End:
			break;
		case Init:
			DustMachineBoot.loadBootModules();
			log = "Machine initialized";
			break;
		case Process:
			break;
		case Release:
			break;
		}

		if (DustUtils.isEmpty(log)) {
			Dust.log(DH_EVENT_TYPE_ERROR, "Unimplemented action in Machine");
			return MindResponse.Error;
		} else {
			Dust.log(DH_EVENT_TYPE_INFO, log);
			return MindResponse.Accept;
		}
	}

	public void set(DustMachineHandle hTarget, DustMachineHandle hAtt, Object val, MindCollType ct, Object key) {
		DustMachineKnowledgeItem ki = resolveItem(hTarget.kiUnit, hTarget);
		
		ki.set(hAtt, val, ct, key);
		
	}

}
