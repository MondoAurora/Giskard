package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public final class DustBrainHandle implements DustBrainConsts, GiskardConsts.MindHandle, Comparable<DustBrainHandle> {
	static Object idlock = new Object();
	static long nextId = 0;

	static HandleFormatter FORMATTER = DustBrainConsts.DEF_FORMATTER;

	final Long id;
	Object ctxToken;

	public DustBrainHandle() {
		synchronized (idlock) {
			this.id = nextId++;
		}
	}

	@Override
	public Object getId() {
		return id;
	}
	
	public Object getCtxToken() {
		return ctxToken;
	}
	
	public void setCtxToken(Object ctxToken) {
		this.ctxToken = ctxToken;
	}

	@Override
	public int compareTo(DustBrainHandle o) {
		return id.compareTo(o.id);
	}

	@Override
	public String toString() {
		return FORMATTER.formatLabel(this);
	}
}