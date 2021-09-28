package me.giskard.java8;

import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;

public class GiskardJava extends Giskard.GiskardImplementation implements GiskardJavaConsts {

	final GiskardJavaModel model;
	final GiskardJavaIdea idea;

	public GiskardJava() {
		model = new GiskardJavaModel(this);
		idea = new GiskardJavaIdea(this);
	}

	@Override
	protected void init(String[] args) {
		model.access(MindNarrativeAccessCmd.Set, args, MachineNode.LaunchInfo, MachineLaunchInfo.Arguments);
		model.access(MindNarrativeAccessCmd.Set, System.getenv(), MachineNode.LaunchInfo, MachineLaunchInfo.Environment);
		model.access(MindNarrativeAccessCmd.Set, System.getProperties(), MachineNode.Runtime, MachineRuntime.Properties);

		Map<Object, Object> props = new TreeMap<>();
		for (Map.Entry<Object, Object> e : System.getProperties().entrySet()) {
			props.put(e.getKey(), e.getValue());
		}

		System.out.println("{");
		for (Map.Entry<Object, Object> e : props.entrySet()) {
			System.out.println("\t\"" + e.getKey() + "\": \"" + e.getValue() + "\",");
		}
		System.out.println("}");
	}

	@Override
	protected <RetType> RetType get(GiskardGetMode mode, RetType defValue, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected <RetType> RetType set(GiskardSetMode mode, RetType value, Object... path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean del(Object... path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void visit(GiskardVisitMode mode, GiskardEntity visitor, Object... path) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void log(GiskardLogLevel level, Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected <RetType> RetType wrapException(Throwable orig, Object... params) {
		if ( orig instanceof GisJavaException ) {
			throw (GisJavaException) orig;
		} else {
			throw new GisJavaException(orig, params);
		}
	}

	@Override
	public void agentProcess() throws Exception {
	}
}
