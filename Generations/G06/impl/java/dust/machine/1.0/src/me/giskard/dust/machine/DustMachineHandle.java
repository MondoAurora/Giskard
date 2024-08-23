package me.giskard.dust.machine;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.dust.DustConsts;

public class DustMachineHandle extends DustConsts.MindHandle implements DustMachineConsts {
	private final Map<MindHandle, Object> data = new TreeMap<>();

	@Override
	public String toString() {
		return data.toString();
	}

	@Override
	public String getId() {
		return null;
	}

}
