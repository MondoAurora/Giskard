package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public interface DustBrainConsts extends GiskardConsts {

	interface HandleFormatter {
		String formatLabel(DustBrainHandle h);
	}

	HandleFormatter DEF_FORMATTER = new HandleFormatter() {
		@Override
		public String formatLabel(DustBrainHandle h) {
			return "BH(" + h.id + ")";
		}
	};

	interface KnowledgeConnector {
		Object create(KnowledgeItem ctx, DustBrainHandle hMember, Object key, Object... params);

		void notifyChange(MindAccess cmd, DustBrainHandle hMember, Object key, Object old, Object curr);
	}

	interface KnowledgeVisitor {
		public <RetType> RetType access(MindAction action, KnowledgeItem item, DustBrainHandle hMember, MindColl coll, Object key, Object val);
	}

	interface KnowledgeItem {
		public <RetType> RetType access(MindAccess cmd, DustBrainHandle hMember, MindColl coll, Object key, Object val, KnowledgeConnector kc, Object... params);

		public void visit(DustBrainHandle hMember, MindColl coll, Object key, KnowledgeVisitor visitor);
	}

	interface LogicKnowledge {
		<RetType> RetType access(LogicContext ctx, MindAccess cmd, Object val, Object root, Object... path);
	}

	interface LogicContext {
		MindHandle createItem(MindHandle hTag);

		MindColl getMemberColl(MindHandle hMember);

		MindHandle getTagParent(MindHandle hTag);
	}

	interface LogicMediator {
		MindHandle getHandleByToken(Object token, KnowledgeItem kMediator, KnowledgeItem kContext);
	}

	interface LogicDialog {

	}
}
