package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardException;

public interface DustBrainBootstrap extends DustBrainConsts {

	String CN_KNOWLEDGE_ITEM = "me.giskard.dust.mod.brain.logic.DustBrainKnowledge";

	String CN_KNOWLEDGE = "me.giskard.dust.mod.brain.logic.DustBrainLogicKnowledge";
	String CN_CONTEXT = "me.giskard.dust.mod.brain.logic.DustBrainLogicContext";
	String CN_DIALOG = "me.giskard.dust.mod.brain.logic.DustBrainLogicDialog";

	enum BootToken {
		typTag, tagValtype, tagValtypeInt, tagValtypeReal, tagValtypeHandle, tagValtypeBin, tagColl, tagCollOne, tagCollSet, tagCollArr, tagCollMap,

		typContext, memContextLocalKnowledge, typKnowledge, memKnowledgeHandle, memKnowledgeContext, memKnowledgeType, memKnowledgeTags, memKnowledgeCommit, memKnowledgeOwner, typType, typMember,
		memMemberHandleType, memMemberKeyType, typPublished, memKnowledgePublisher, logMediator, memMediatorRemote, memMediatorLocalToRemote, memMediatorRemoteToLocal, typText, memTextString,
		memTextIdentifier, typVocabulary, memVocabularyWords,

		;

		private BrainHandle handle;

		public BrainHandle getHandle() {
			return handle;
		}

		public static BootToken fromHandle(BrainHandle handle) {
			return fromHandle(handle, null);
		}

		public static BootToken fromHandle(BrainHandle handle, BootToken def) {
			if ( null != handle ) {
				for (BootToken bt : values()) {
					if ( bt.handle == handle ) {
						return bt;
					}
				}
			}

			return def;
		}

		public void setHandle(BrainHandle handle) {
			if ( null != this.handle ) {
				if ( !this.handle.equals(handle) ) {
					GiskardException.wrap(null, "Multiple init of BootToken", this);
				}
			} else {
				this.handle = handle;
			}
		}
	}

}
