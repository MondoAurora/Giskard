package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public interface DustBrainBootstrap extends DustBrainConsts {

	String CN_KNOWLEDGE_ITEM = "me.giskard.dust.mod.brain.logic.DustBrainKnowledge";

	String CN_KNOWLEDGE = "me.giskard.dust.mod.brain.logic.DustBrainLogicKnowledge";
	String CN_CONTEXT = "me.giskard.dust.mod.brain.logic.DustBrainLogicUnit";
	String CN_DIALOG = "me.giskard.dust.mod.brain.logic.DustBrainLogicDialog";

	String PATH_UNITS = "../../../../Store/Units";

	String LANG_BOOT = "en";

	enum BootAuthor {
		authGiskard("Giskard"), authDust("Dust"),

		;

		public final String id;

		private BootAuthor(String id) {
			this.id = id;
		}
	}

	enum BootUnit {
		unitMiND(BootAuthor.authGiskard, 1), unitText(BootAuthor.authGiskard, 1), unitStream(BootAuthor.authGiskard, 1), unitBrain(BootAuthor.authDust, 1), unitBrainJava(BootAuthor.authDust, 1)

		;

		public final BootAuthor author;
		public final String id;
		public final Number verMajor;

		private BootUnit(BootAuthor author, Number verMajor) {
			this.author = author;
			this.id = name().substring(4);
			this.verMajor = verMajor;
		}

		public String getToken() {
			return GiskardUtils.sbAppend(null, SEP, true, author.id, id, verMajor).toString();
		}

		public static String getToken(Object authorId, Object unit, Object verMajor) {
			return GiskardUtils.sbAppend(null, SEP, true, authorId, unit, verMajor).toString();
		}
	}

	enum BootToken implements MindHandle {
//@formatter:off

		typTag(BootUnit.unitMiND), 
		tagValtype(BootUnit.unitMiND), tagValtypeInt(BootUnit.unitMiND), tagValtypeReal(BootUnit.unitMiND), tagValtypeHandle(BootUnit.unitMiND), tagValtypeBin(BootUnit.unitMiND), 
		tagColl(BootUnit.unitMiND), tagCollOne(BootUnit.unitMiND), tagCollSet(BootUnit.unitMiND), tagCollArr(BootUnit.unitMiND), tagCollMap(BootUnit.unitMiND),
		tagProxy(BootUnit.unitMiND), tagProxyUnit(BootUnit.unitMiND), tagProxyAuthor(BootUnit.unitMiND), 
		tagTransient(BootUnit.unitMiND), 
		
		typUnit(BootUnit.unitMiND), memUnitAuthor(BootUnit.unitMiND), memUnitLocalKnowledge(BootUnit.unitMiND), memUnitNextId(BootUnit.unitMiND), memUnitVersionMajor(BootUnit.unitMiND), 
		typAuthor(BootUnit.unitMiND), typProxy(BootUnit.unitMiND), memProxyRemote(BootUnit.unitMiND), 
		typKnowledge(BootUnit.unitMiND), memKnowledgeID(BootUnit.unitMiND), memKnowledgeHandle(BootUnit.unitMiND), memKnowledgeUnit(BootUnit.unitMiND), 
		memKnowledgeType(BootUnit.unitMiND), memKnowledgeTags(BootUnit.unitMiND), memKnowledgeToken(BootUnit.unitMiND), 
		memKnowledgeOwner(BootUnit.unitMiND), memKnowledgeRequires(BootUnit.unitMiND), memKnowledgeExtends(BootUnit.unitMiND), 
		typType(BootUnit.unitMiND), 
		typMember(BootUnit.unitMiND), memMemberHandleType(BootUnit.unitMiND), memMemberKeyType(BootUnit.unitMiND), 
		logDialog(BootUnit.unitMiND), memDialogUnit(BootUnit.unitMiND), 
		typLogic(BootUnit.unitMiND), 
		logMediator(BootUnit.unitMiND), memMediatorRemote(BootUnit.unitMiND), memMediatorLocalToRemote(BootUnit.unitMiND), memMediatorRemoteToLocal(BootUnit.unitMiND), 
		
		typText(BootUnit.unitText), memTextString(BootUnit.unitText), 
		typVocabulary(BootUnit.unitText), typLanguage(BootUnit.unitText), memLanguageWords(BootUnit.unitText), memLanguageVocabularies(BootUnit.unitText),
		
		logBrain(BootUnit.unitBrain), memBrainUnits(BootUnit.unitBrain), memBrainLanguages(BootUnit.unitBrain), memBrainDialogs(BootUnit.unitBrain), memBrainThreads(BootUnit.unitBrain), 

		theBrain(BootUnit.unitBrainJava),
	
		;
//@formatter:on

		public final BootUnit unit;

		private Object id;

		private BootToken(BootUnit unit) {
			this.unit = unit;
		}

		// to be used by the generated code
		private BootToken(BootUnit unit, Object id) {
			this.unit = unit;
			this.id = id;
		}

		@Override
		public String toString() {
			return GiskardUtils.sbAppend(null, SEP, false, unit, name()).toString();
		}

		@Override
		public Object getUnitToken() {
			return unit.getToken();
		}

		@Override
		public Object getId() {
			return id;
		}
	}

}
