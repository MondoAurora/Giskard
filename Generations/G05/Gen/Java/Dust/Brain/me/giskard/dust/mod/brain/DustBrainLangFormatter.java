package me.giskard.dust.mod.brain;

public class DustBrainLangFormatter implements DustBrainConsts, DustBrainBootstrap, DustBrainConsts.HandleFormatter {

	final KnowledgeItem brain;
	final KnowledgeItem unitLang;

	public DustBrainLangFormatter(KnowledgeItem brain, String lang) {
		this.brain = brain;

		DustBrainHandle handle = brain.access(MindAccess.Peek, BootToken.memBrainLanguages, MindColl.Map, lang, null, null);
		unitLang = DustBrainBootUtils.resolveHandle(brain, handle, null);
	}

	@Override
	public String formatLabel(MindHandle h) {
		String ret = null;

		Object unitToken = h.getUnitToken();

		if ( null == unitToken ) {
			KnowledgeItem item = DustBrainBootUtils.resolveHandle(brain, h, null);
			if ( null != item ) {
				ret = item.access(MindAccess.Peek, BootToken.memKnowledgeToken, MindColl.One, null, null, null);
			} else {
				item = DustBrainBootUtils.resolveHandle(unitLang, h, null);
				if ( null != item ) {
					ret = item.access(MindAccess.Peek, BootToken.memKnowledgeToken, MindColl.One, null, null, null);
					if ( null == ret ) {
						ret = item.access(MindAccess.Peek, BootToken.memTextString, MindColl.One, null, null, null);
					}
				}
			}
		} else {
			DustBrainHandle vocHandle = unitLang.access(MindAccess.Peek, BootToken.memLanguageVocabularies, MindColl.Map, unitToken, null, null);
			KnowledgeItem voc = DustBrainBootUtils.resolveHandle(unitLang, vocHandle, null);

			DustBrainHandle wordHandle = voc.access(MindAccess.Peek, BootToken.memMediatorRemoteToLocal, MindColl.Map, h, null, null);
			KnowledgeItem word = DustBrainBootUtils.resolveHandle(unitLang, wordHandle, null);

			ret = word.access(MindAccess.Peek, BootToken.memTextString, MindColl.One, null, null, null);
		}
		return (null == ret) ? DustBrainHandle.DEF_FORMATTER.formatLabel(h) : ret;
	}
}