package me.giskard.dust.machine;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.event.DustEventHandles;
import me.giskard.mind.DustMindHandles;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustMachineNarrative implements Dust.MindMachine, DustMindHandles, DustEventHandles {

	DustMachineHandle hUnitHandles = new DustMachineHandle("giskard.me:mind_1.0:1");
	DustMachineHandle hUnitContent = new DustMachineHandle("giskard.me:mind_1.0:2");

//	DustMachineKnowledgeItem mainKnowledge;

	final ThreadLocal<DustMachineKnowledgeItem> dialogs = new ThreadLocal<DustMachineKnowledgeItem>() {
		@Override
		protected DustMachineKnowledgeItem initialValue() {
			return new DustMachineKnowledgeItem(null);
		}
	};

	public DustMachineNarrative(DustMachineKnowledgeItem bootKnowledge) {
		dialogs.set(bootKnowledge);
//		this.mainKnowledge = bootKnowledge;
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

			DustMachineKnowledgeItem kiUnit = dialogs.get();

			if (spl.length > 2) {
				kiUnit = resolveItem(kiUnit, spl[0] + DUST_SEP_ID + spl[1]);
//				lid = spl[2];
			}

			ret = resolveHandle(kiUnit, lid);
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

	private DustMachineHandle resolveHandle(DustMachineKnowledgeItem kiUnit, String lookupId) {
		Map mh = kiUnit.get(hUnitHandles);
		if ( null == mh ) {
			mh = new TreeMap<>();
			kiUnit.set(hUnitHandles, mh);
		}
		DustMachineHandle h = (DustMachineHandle) mh.get(lookupId);

		if ( null == h ) {
			h = new DustMachineHandle(lookupId);
			mh.put(lookupId, h);
		}
		
		return h;
	}
	
	private DustMachineKnowledgeItem resolveItem(DustMachineKnowledgeItem kiUnit, String lookupId) {
		DustMachineHandle h = resolveHandle(kiUnit, lookupId);
		
		Map mc = kiUnit.get(hUnitContent);
		if ( null == mc ) {
			mc = new HashMap<>();
			kiUnit.set(hUnitContent, mc);
		}
		DustMachineKnowledgeItem ret = (DustMachineKnowledgeItem) mc.get(h);
		
		if ( null == ret ) {
			ret = new DustMachineKnowledgeItem(h);
			mc.put(h, ret);
		}
		
		return ret;
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
			Dust.log(EVENT_TAG_TYPE_ERROR, "Unimplemented action in Machine");
			return MindResponse.Error;
		} else {
			Dust.log(EVENT_TAG_TYPE_INFO, log);
			return MindResponse.Accept;
		}
	}

}
