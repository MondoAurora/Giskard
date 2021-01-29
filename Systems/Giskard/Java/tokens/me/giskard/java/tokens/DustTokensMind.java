package me.giskard.java.tokens;

import me.giskard.java.Mind;
import me.giskard.java.MindConsts;

public interface DustTokensMind extends MindConsts {
	MiNDEntity MT_EVENT_LEVEL_CRITICAL = Mind.getEntity("Event", "EventLvlCritical");

	MiNDEntity MT_MIND_DIALOG_DATACMD_CHK = Mind.getEntity("Dialog", "DataCmd.CHK");
	MiNDEntity MT_MIND_DIALOG_DATACMD_GET = Mind.getEntity("Dialog", "DataCmd.GET");
	MiNDEntity MT_MIND_DIALOG_DATACMD_SET = Mind.getEntity("Dialog", "DataCmd.SET");
	MiNDEntity MT_MIND_DIALOG_DATACMD_ADD = Mind.getEntity("Dialog", "DataCmd.ADD");
	MiNDEntity MT_MIND_DIALOG_DATACMD_DEL = Mind.getEntity("Dialog", "DataCmd.DEL");
	MiNDEntity MT_MIND_DIALOG_DATACMD_VISIT = Mind.getEntity("Dialog", "DataCmd.VISIT");

	enum DataCmd implements EntityWrapper {
		CHK(MT_MIND_DIALOG_DATACMD_CHK), GET(MT_MIND_DIALOG_DATACMD_GET), SET(MT_MIND_DIALOG_DATACMD_SET),
		ADD(MT_MIND_DIALOG_DATACMD_ADD), DEL(MT_MIND_DIALOG_DATACMD_DEL), VISIT(MT_MIND_DIALOG_DATACMD_VISIT);

		final MiNDEntity entity;

		private DataCmd(MiNDEntity entity) {
			this.entity = entity;
		}

		@Override
		public MiNDEntity getEntity() {
			return entity;
		}
	};

}
