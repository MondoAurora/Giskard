package me.giskard.dust.text;

import java.util.ArrayList;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextMatcherConst implements DustTextConsts, GiskardConsts.MiNDAgentBlock {

	int[] cpArr;
	int size;
	int idx;

	void load(String string) {
		ArrayList<Integer> cps = new ArrayList<>();
		int ch;
		for (int i = 0; i < string.length(); i += Character.charCount(ch)) {
			ch = string.codePointAt(i);
			cps.add(ch);
		}

		size = cps.size();
		cpArr = new int[size];
		for (int i = 0; i < size; ++i) {
			cpArr[i] = cps.get(i);
		}
	}

	@Override
	public void mindAgentBegin() throws Exception {
		String str = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_THIS, MTMEM_TEXT_PLAINTEXT_STRING);
		load(str);
		idx = 0;
	}

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		int chr = Giskard.access(MiNDAccessCommand.Get, -1, MTMEM_GENERIC_VALUE_RAW);
		return (chr == cpArr[idx]) ? (++idx < size) ? MiNDResultType.Read : MiNDResultType.Accept : MiNDResultType.Reject;
	}

	@Override
	public void mindAgentEnd() throws Exception {
	}
}
