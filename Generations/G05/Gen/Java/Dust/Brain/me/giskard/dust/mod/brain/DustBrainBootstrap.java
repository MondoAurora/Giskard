package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public interface DustBrainBootstrap extends DustBrainConsts {

	String CN_KNOWLEDGE_ITEM = "me.giskard.dust.mod.brain.logic.DustBrainKnowledge";

	String CN_KNOWLEDGE = "me.giskard.dust.mod.brain.logic.DustBrainLogicKnowledge";
	String CN_CONTEXT = "me.giskard.dust.mod.brain.logic.DustBrainLogicUnit";
	String CN_DIALOG = "me.giskard.dust.mod.brain.logic.DustBrainLogicDialog";

	String PATH_UNITS = "../../../../Store/Units";

	String LANG_BOOT = "en";

	enum ModuleAuthor {
		authGiskard("Giskard"), authDust("Dust"),

		;

		public final String id;

		private ModuleAuthor(String id) {
			this.id = id;
		}
	}

	enum ModuleUnit {
		unitMiND(ModuleAuthor.authGiskard, 1), unitText(ModuleAuthor.authGiskard, 1), unitStream(ModuleAuthor.authGiskard, 1), unitBrain(ModuleAuthor.authGiskard, 1), unitBrainJava(ModuleAuthor.authDust, 1)

		;

		public final ModuleAuthor author;
		public final String id;
		public final Number verMajor;

		private ModuleUnit(ModuleAuthor author, Number verMajor) {
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

		typTag(ModuleUnit.unitMiND), 
//		tagValtype(ModuleUnit.unitMiND), tagValtypeInt(ModuleUnit.unitMiND), tagValtypeReal(ModuleUnit.unitMiND), tagValtypeHandle(ModuleUnit.unitMiND), tagValtypeBin(ModuleUnit.unitMiND), 
		tagColl(ModuleUnit.unitMiND), tagCollOne(ModuleUnit.unitMiND), tagCollSet(ModuleUnit.unitMiND), tagCollArr(ModuleUnit.unitMiND), tagCollMap(ModuleUnit.unitMiND),
		tagProxy(ModuleUnit.unitMiND), tagProxyUnit(ModuleUnit.unitMiND), tagProxyAuthor(ModuleUnit.unitMiND), 
		tagTransient(ModuleUnit.unitMiND), 
		
		typUnit(ModuleUnit.unitMiND), memUnitAuthor(ModuleUnit.unitMiND), memUnitLocalKnowledge(ModuleUnit.unitMiND), memUnitNextId(ModuleUnit.unitMiND), memUnitVersionMajor(ModuleUnit.unitMiND), 
		typAuthor(ModuleUnit.unitMiND), typProxy(ModuleUnit.unitMiND), memProxyRemote(ModuleUnit.unitMiND), 
		typKnowledge(ModuleUnit.unitMiND), memKnowledgeID(ModuleUnit.unitMiND), memKnowledgeHandle(ModuleUnit.unitMiND), memKnowledgeUnit(ModuleUnit.unitMiND), 
		memKnowledgeType(ModuleUnit.unitMiND), memKnowledgeTags(ModuleUnit.unitMiND), memKnowledgeToken(ModuleUnit.unitMiND), 
		memKnowledgeOwner(ModuleUnit.unitMiND), memKnowledgeRequires(ModuleUnit.unitMiND), memKnowledgeExtends(ModuleUnit.unitMiND), 
		typType(ModuleUnit.unitMiND), 
		typMember(ModuleUnit.unitMiND), memMemberHandleType(ModuleUnit.unitMiND), memMemberKeyType(ModuleUnit.unitMiND), 
		logDialog(ModuleUnit.unitMiND), memDialogUnit(ModuleUnit.unitMiND), 
		typLogic(ModuleUnit.unitMiND), 
		logMediator(ModuleUnit.unitMiND), memMediatorRemote(ModuleUnit.unitMiND), memMediatorLocalToRemote(ModuleUnit.unitMiND), memMediatorRemoteToLocal(ModuleUnit.unitMiND), 
		
		typText(ModuleUnit.unitText), memTextString(ModuleUnit.unitText), 
		typVocabulary(ModuleUnit.unitText), typLanguage(ModuleUnit.unitText), memLanguageWords(ModuleUnit.unitText), memLanguageVocabularies(ModuleUnit.unitText),
		
		logBrain(ModuleUnit.unitBrain), memBrainUnits(ModuleUnit.unitBrain), memBrainLanguages(ModuleUnit.unitBrain), memBrainDialogs(ModuleUnit.unitBrain), memBrainThreads(ModuleUnit.unitBrain), 

		theBrain(ModuleUnit.unitBrainJava),
	
		;
//@formatter:on

		public final ModuleUnit unit;

		private Object itemId;

		private BootToken(ModuleUnit unit) {
			this.unit = unit;
		}

		// to be used by the generated code
		private BootToken(ModuleUnit unit, Object id) {
			this.unit = unit;
			this.itemId = id;
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
			return toString();
		}

		@Override
		public Object getItemId() {
			return itemId;
		}
	}

}
