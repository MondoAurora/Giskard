package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public interface DustBrainBootstrap extends DustBrainConsts {

	String CN_KNOWLEDGE_ITEM = "me.giskard.dust.mod.brain.logic.DustBrainKnowledge";

	String CN_KNOWLEDGE = "me.giskard.dust.mod.brain.logic.DustBrainLogicKnowledge";
	String CN_CONTEXT = "me.giskard.dust.mod.brain.logic.DustBrainLogicContext";
	String CN_DIALOG = "me.giskard.dust.mod.brain.logic.DustBrainLogicDialog";

	String PATH_UNITS = "../../../../Store/Units";

	String LANG_BOOT = "en";

	enum BootPublisher {
		pubGiskard("Giskard"), pubDust("Dust"), 
		
		;
		
		public final String id;

		private BootPublisher(String id) {
			this.id = id;
		}
	}

	enum BootContext {
		ctxMiND(BootPublisher.pubGiskard, 1), ctxText(BootPublisher.pubGiskard, 1), ctxStream(BootPublisher.pubGiskard, 1), 
		ctxBrain(BootPublisher.pubDust, 1), ctxBrainJava(BootPublisher.pubDust, 1)
		
		;
		
		public final BootPublisher publisher;
		public final String id;
		public final Number verMajor;
		
		private BootContext(BootPublisher publisher, Number verMajor) {
			this.publisher = publisher;
			this.id = name().substring(3);
			this.verMajor = verMajor;
		}
		
		public String getToken() {
			return GiskardUtils.sbAppend(null, SEP, true, publisher.id, id, verMajor).toString();
		}

		public static String getToken(Object publisherId, Object ctx, Object verMajor) {
			return GiskardUtils.sbAppend(null, SEP, true, publisherId, ctx, verMajor).toString();
		}
	}

	enum BootToken {
//@formatter:off

		typTag(BootContext.ctxMiND), 
		tagValtype(BootContext.ctxMiND), tagValtypeInt(BootContext.ctxMiND), tagValtypeReal(BootContext.ctxMiND), tagValtypeHandle(BootContext.ctxMiND), tagValtypeBin(BootContext.ctxMiND), 
		tagColl(BootContext.ctxMiND), tagCollOne(BootContext.ctxMiND), tagCollSet(BootContext.ctxMiND), tagCollArr(BootContext.ctxMiND), tagCollMap(BootContext.ctxMiND),

		typContext(BootContext.ctxMiND), memContextLocalKnowledge(BootContext.ctxMiND), 
		typKnowledge(BootContext.ctxMiND), memKnowledgeIdentifier(BootContext.ctxMiND), memKnowledgeHandle(BootContext.ctxMiND), memKnowledgeContext(BootContext.ctxMiND), 
		memKnowledgeType(BootContext.ctxMiND), memKnowledgeTags(BootContext.ctxMiND), memPublishedVersionMajor(BootContext.ctxMiND), 
		memKnowledgeOwner(BootContext.ctxMiND), memKnowledgeRequires(BootContext.ctxMiND), 
		typType(BootContext.ctxMiND), 
		typMember(BootContext.ctxMiND), memMemberHandleType(BootContext.ctxMiND), memMemberKeyType(BootContext.ctxMiND), 
		logDialog(BootContext.ctxMiND), memDialogContext(BootContext.ctxMiND), 
		typPublished(BootContext.ctxMiND), memKnowledgePublisher(BootContext.ctxMiND), 
		typMediator(BootContext.ctxMiND), memMediatorRemote(BootContext.ctxMiND), memMediatorLocalToRemote(BootContext.ctxMiND), memMediatorRemoteToLocal(BootContext.ctxMiND), 
		
		typText(BootContext.ctxText), memTextString(BootContext.ctxText), 
		typVocabulary(BootContext.ctxText), typLanguage(BootContext.ctxText), memLanguageWords(BootContext.ctxText), memLanguageVocabularies(BootContext.ctxText),
		
		logBrain(BootContext.ctxBrain), memBrainContexts(BootContext.ctxBrain), memBrainLanguages(BootContext.ctxBrain), memBrainDialogs(BootContext.ctxBrain), memBrainThreads(BootContext.ctxBrain), 

		theBrain(BootContext.ctxBrainJava),
	
		;
//@formatter:on
		
		public final BootContext context;

		private BootToken(BootContext context) {
			this.context = context;
		}

		@Override
		public String toString() {
			return GiskardUtils.sbAppend(null, ".", false, context, name()).toString();
		}
	}

}
