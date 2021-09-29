package me.giskard.java8;

import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class GiskardJava extends Giskard.GiskardImplementation implements GiskardJavaConsts {

	PrintStream log;
	
	final GiskardJavaModel model;
	final GiskardJavaIdea idea;

	public GiskardJava() {
		model = new GiskardJavaModel(this);
		idea = new GiskardJavaIdea(this);
		
		log = System.out;
	}

	@Override
	protected void init(String[] args) throws Exception {
		
		registerEnums();
		
		Giskard.log(GiskardLogLevel.Trace, "Enum registration", this);
		
		
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
		
		Giskard.log(GiskardLogLevel.Info, "Launch arguments", Giskard.get(GiskardGetMode.Peek, null, MachineNode.LaunchInfo, MachineLaunchInfo.Arguments));

	}
	
	void registerEnums() throws Exception {
		String pName = getClass().getPackage().getName();
		for ( GiskardUnits gu : GiskardUnits.values() ) {
			Class<?> cc = Class.forName(pName + ".GiskardTokens" + gu.name());

			for ( Class<?> ec : cc.getClasses() ) {
				for ( Object o : ec.getEnumConstants() ) {
					idea.registerEnum(gu, (Enum<?>) o);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n");
		for (Map.Entry<?, ?> e : idea.mapAttributes.entrySet()) {
			sb.append("\t\"" + e.getKey() + "\": \"" + model.getEntityData((GiskardEntity) e.getValue()) + "\",\n");
		}
		sb.append("}");
		
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <RetType> RetType get(GiskardGetMode mode, RetType defValue, Object... path) {
		return (RetType) model.access(MindNarrativeAccessCmd.Get, defValue, path);
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
		StringBuilder sb = GiskardUtils.sbAppend(null, " ", GiskardJavaUtils.tsNow(), level);
		GiskardUtils.sbAppend(sb, " ", params);
		
		log.println(sb);
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
