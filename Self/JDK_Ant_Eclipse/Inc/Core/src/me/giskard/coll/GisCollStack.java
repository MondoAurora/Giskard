package me.giskard.coll;

import java.util.ArrayList;

import me.giskard.coll.GisCollConsts.MiNDBuilder;

public class GisCollStack<Val> implements GisCollConsts.MiNDColl<Integer, Val> {
	MiNDBuilder<Integer, Val> itemBuilder;
	ArrayList<Val> items = new ArrayList<>();
	int ptr = -1;

	public GisCollStack(MiNDBuilder<Integer, Val> itemBuilder) {
		this.itemBuilder = itemBuilder;
	}

	public Val get() {
		return (0 <= ptr) ? items.get(ptr) : null;
	}

	public Val step(int diff) {
		if ( 0 != diff ) {
			int c = ptr;
			ptr += diff;

			if ( 0 < diff ) {
				for (int i = items.size(); i <= ptr; ++i) {
					items.add(itemBuilder.create(i));
				}
			} else {
				for (int i = c; i > ptr; --i) {
					itemBuilder.release(i, items.get(i));
				}
			}
		}

		return get();
	}

	@Override
	public boolean isEmpty() {
		return -1 == ptr;
	}

	@Override
	public int getCount() {
		return ptr + 1;
	}

	@Override
	public void clear() {
		ptr = -1;
		int i = 0;
		for (Val v : items) {
			itemBuilder.release(i, v);
			++i;
		}
	}
}