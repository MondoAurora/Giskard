package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {

	interface HandleFormatter {
		String formatLabel(MindHandle h);
	}

	HandleFormatter DEF_FORMATTER = new HandleFormatter() {
		@Override
		public String formatLabel(MindHandle h) {
			return "BH(" + h.getId() + ")";
		}
	};

	interface KnowledgeConnector {
		Object create(KnowledgeItem unit, MindHandle hMember, Object key, Object... params);

		void notifyChange(MindAccess cmd, MindHandle hMember, Object key, Object old, Object curr);
	}

	interface KnowledgeVisitor {
		public <RetType> RetType access(MindAction action, KnowledgeItem item, MindHandle hMember, MindColl coll, Object key, Object val);
	}

	interface KnowledgeItem {
		public <RetType> RetType access(MindAccess cmd, MindHandle hMember, MindColl coll, Object key, Object val, KnowledgeConnector kc, Object... params);

		public void visit(MindHandle hMember, MindColl coll, Object key, KnowledgeVisitor visitor);
	}

	interface LogicKnowledge {
		<RetType> RetType access(LogicUnit unit, MindAccess cmd, Object val, Object root, Object... path);
	}

	interface LogicUnit {
		MindHandle createItem(MindHandle hTag);

		MindColl getMemberColl(MindHandle hMember);

		MindHandle getTagParent(MindHandle hTag);
	}

	interface LogicMediator {
		MindHandle getHandleByToken(Object token, KnowledgeItem kMediator, KnowledgeItem kUnit);
	}

	interface LogicDialog {

	}
}
