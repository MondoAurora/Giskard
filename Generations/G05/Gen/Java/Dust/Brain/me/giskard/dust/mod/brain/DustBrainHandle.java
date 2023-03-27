package me.giskard.dust.mod.brain;

import me.giskard.mind.GiskardConsts;

public final class DustBrainHandle implements DustBrainConsts, GiskardConsts.MindHandle, Comparable<DustBrainHandle> {
	static Object idlock = new Object();
	static long nextId = 0;

	static HandleFormatter FORMATTER = DustBrainConsts.DEF_FORMATTER;

	final Long id;
	Object unitToken;
	Object itemId;

	public DustBrainHandle() {
		synchronized (idlock) {
			this.id = nextId++;
		}
	}

	@Override
	public Object getId() {
		return id;
	}
	
	@Override
	public Object getUnitToken() {
		return unitToken;
	}
	
	@Override
	public Object getItemId() {
		return null;
	}
	
	public void setUnitToken(Object unitToken) {
		this.unitToken = unitToken;
	}
	
	public void setItemId(Object itemId) {
		this.itemId = itemId;
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