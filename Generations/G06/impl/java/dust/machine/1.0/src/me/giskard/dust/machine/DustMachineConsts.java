package me.giskard.dust.machine;

import me.giskard.dust.DustConsts;

public interface DustMachineConsts extends DustConsts {
	interface KnowledgeItem {
		<RetType> RetType get(MindHandle hAtt);
		void set(MindHandle hAtt, Object o);
	}
}
