package me.giskard.dust.machine;

import me.giskard.dust.Dust;
import me.giskard.dust.utils.DustUtils;
import me.giskard.event.DustEventHandles;
import me.giskard.mind.DustMindHandles;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DustMachineNarrative implements Dust.MindMachine, DustMindHandles, DustEventHandles {
	
	final ThreadLocal<DustMachineKnowledgeItem> dialogs = new ThreadLocal<DustMachineKnowledgeItem>() {
		@Override
		protected DustMachineKnowledgeItem initialValue() {
			return new DustMachineKnowledgeItem();
		}
	};

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
			int ii = lid.lastIndexOf(":");
//			ret = resolve(lid.substring(0, ii), lid.substring(ii + 1));
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

	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
