package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardUtils;

public class DustBrainLangFormatter implements DustBrainConsts, DustBrainBootstrap, DustBrainConsts.HandleFormatter {

	final KnowledgeItem ctxLang;

	public DustBrainLangFormatter(KnowledgeItem brain, String lang) {
		DustBrainHandle handle = brain.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memBrainLanguages), MindColl.Map, lang, null, null);
		ctxLang = DustBrainBootUtils.resolveHandle(brain, handle, null);
	}

	@Override
	public String formatLabel(DustBrainHandle h) {
		String ret = null;

		Object ctxToken = h.getCtxToken();

		if ( null != ctxToken ) {
			DustBrainHandle vocHandle = ctxLang.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memLanguageVocabularies), MindColl.Map, ctxToken, null, null);
			KnowledgeItem voc = DustBrainBootUtils.resolveHandle(ctxLang, vocHandle, null);

			DustBrainHandle wordHandle = voc.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memMediatorRemoteToLocal), MindColl.Map, h, null, null);
			KnowledgeItem word = DustBrainBootUtils.resolveHandle(ctxLang, wordHandle, null);

			ret = word.access(MindAccess.Peek, GiskardUtils.getHandle(BootToken.memTextString), MindColl.One, null, null, null);
		}
		return (null == ret) ? DustBrainHandle.DEF_FORMATTER.formatLabel(h) : ret;
	}
}