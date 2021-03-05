package me.giskard.dust.text;

import java.util.ArrayList;
import java.util.List;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public class DustTextMatcherConst implements DustTextConsts, GiskardConsts.MiNDAgent {
	
	int[] cpArr;
	int size;
	int idx;
	
	public DustTextMatcherConst() {
		
	}
	
	public DustTextMatcherConst(List<Integer> cps) {
		load(cps);
	}

	public DustTextMatcherConst(String string) {
		ArrayList<Integer> cps = new ArrayList<>();
	  int ch;
	  int i;
	  for (i = 0; i < string.length(); i += Character.charCount(ch)) {
	  	ch = string.codePointAt(i);
	    cps.add(ch);
	  }
	  load(cps);
	}

	void load(List<Integer> cps) {
		size = cps.size();
		cpArr = new int[size];
		for ( int i = 0; i < size; ++i ) {
			cpArr[i] = cps.get(i);
		}
	}

	@Override
	public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
		switch ( action ) {
		case Init:
			Giskard.log(MiNDEventLevel.TRACE, "DustTextMatcherConst agent initialized.");
			return MiNDResultType.ACCEPT;
		case Begin:
			idx = 0;
			return MiNDResultType.ACCEPT;
		case Process:
			int chr = (int) params[0];
			return (chr == cpArr[idx]) ? (++idx < size) ? MiNDResultType.ACCEPT_READ : MiNDResultType.ACCEPT_PASS : MiNDResultType.REJECT;
		default:
			return MiNDResultType.ACCEPT;
		}
	}

}
