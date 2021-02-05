package me.giskard.tokens;

import me.giskard.Mind;
import me.giskard.MindConsts;

public interface DustTokensMind extends MindConsts {
	MiNDToken MT_DIALOG_UNIT = Mind.defineToken(MiNDTokenType.UNIT, "MiND_DIALOG");
	
	MiNDToken MT_DIALOG_ACCESSCOMMAND = Mind.defineToken(MiNDTokenType.TAG, "AccessCommand", MT_DIALOG_UNIT, MiNDAccessCommand.class);
	MiNDToken MT_DIALOG_ACCESSCOMMAND_CHK = Mind.defineToken(MiNDTokenType.TAG, "AccessCommand", MT_DIALOG_ACCESSCOMMAND, MiNDAccessCommand.CHK);
	
//	MiNDEntity MT_EVENT_LEVEL_CRITICAL = Mind.getEntity("Event", "EventLvlCritical");
//
//	MiNDEntity MT_MIND_DIALOG_DATACMD_CHK = Mind.getEntity("Dialog", "DataCmd.CHK");
//	MiNDEntity MT_MIND_DIALOG_DATACMD_GET = Mind.getEntity("Dialog", "DataCmd.GET");
//	MiNDEntity MT_MIND_DIALOG_DATACMD_SET = Mind.getEntity("Dialog", "DataCmd.SET");
//	MiNDEntity MT_MIND_DIALOG_DATACMD_ADD = Mind.getEntity("Dialog", "DataCmd.ADD");
//	MiNDEntity MT_MIND_DIALOG_DATACMD_DEL = Mind.getEntity("Dialog", "DataCmd.DEL");
//	MiNDEntity MT_MIND_DIALOG_DATACMD_VISIT = Mind.getEntity("Dialog", "DataCmd.VISIT");
//
//	enum DataCmd implements EntityWrapper {
//		CHK(MT_MIND_DIALOG_DATACMD_CHK), GET(MT_MIND_DIALOG_DATACMD_GET), SET(MT_MIND_DIALOG_DATACMD_SET),
//		ADD(MT_MIND_DIALOG_DATACMD_ADD), DEL(MT_MIND_DIALOG_DATACMD_DEL), VISIT(MT_MIND_DIALOG_DATACMD_VISIT);
//
//		final MiNDEntity entity;
//
//		private DataCmd(MiNDEntity entity) {
//			this.entity = entity;
//		}
//
//		@Override
//		public MiNDEntity getEntity() {
//			return entity;
//		}
//	};

}
