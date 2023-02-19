package me.giskard.dust.app;

import me.giskard.mind.GiskardApp;
import me.giskard.mind.GiskardMind;

public class DustHelloWorld extends GiskardApp {

	public DustHelloWorld() {
		super(
				new BrainBean(
						new PublishedBean("Dust", "Brain", 1, 0), "../../Store/Impl/Java/lib", "me.giskard.dust.mod.brain.DustBrain"), 
				new PublishedBean("Dust", "HelloWorld", 1, 0));
	}
	
	@Override
	protected void setBootInfo() throws Exception {
		MindHandle typVar = GiskardMind.access(MindAccess.Get, null, null, "typVar");
		MindHandle localStoreFolder = GiskardMind.access(MindAccess.Get, typVar, null, "localStoreFolder");
		MindHandle memMemberVarValue = GiskardMind.access(MindAccess.Get, null, null, "memMemberVarValue");
		GiskardMind.access(MindAccess.Set, "../../Store", localStoreFolder, memMemberVarValue);

		MindHandle typStream = GiskardMind.access(MindAccess.Get, null, null, "typStream");
		MindHandle appStreamRoot = GiskardMind.access(MindAccess.Get, typStream, null, "appStreamRoot");
		MindHandle memStreamPath = GiskardMind.access(MindAccess.Get, null, null, "memStreamPath");
		GiskardMind.access(MindAccess.Set, localStoreFolder, appStreamRoot, memStreamPath);

		MindHandle agtStreamProviderLocal = GiskardMind.access(MindAccess.Get, null, null, "agtStreamProviderLocal");
		MindHandle appStreamProvider = GiskardMind.access(MindAccess.Get, agtStreamProviderLocal, null, "appStreamProvider");
		MindHandle memStreamProviderLocalRoot = GiskardMind.access(MindAccess.Get, null, null, "memStreamProviderLocalRoot");
		GiskardMind.access(MindAccess.Set, appStreamRoot, appStreamProvider, memStreamProviderLocalRoot);
		MindHandle memStreamProviderContentRoots = GiskardMind.access(MindAccess.Get, null, null, "memStreamProviderContentRoots");
		MindHandle typContext = GiskardMind.access(MindAccess.Get, null, null, "typContext");
		MindHandle typModule = GiskardMind.access(MindAccess.Get, null, null, "typModule");
		GiskardMind.access(MindAccess.Insert, "Units", appStreamProvider, memStreamProviderContentRoots, typContext);
		GiskardMind.access(MindAccess.Insert, "Impl/Java/lib", appStreamProvider, memStreamProviderContentRoots, typModule);

		MindHandle agtStreamMediatorJson = GiskardMind.access(MindAccess.Get, null, null, "agtStreamMediatorJson");
		MindHandle appJson = GiskardMind.access(MindAccess.Get, agtStreamMediatorJson, null, "appJson");
		MindHandle memStreamMediatorCondition = GiskardMind.access(MindAccess.Get, null, null, "memStreamMediatorCondition");
		GiskardMind.access(MindAccess.Set, "*.json", appJson, memStreamMediatorCondition);

		MindHandle agtStreamPortal = GiskardMind.access(MindAccess.Get, null, null, "agtStreamPortal");
		MindHandle appStreamPortal = GiskardMind.access(MindAccess.Get, agtStreamPortal, null, "appStreamPortal");
		MindHandle memStreamPortalMediators = GiskardMind.access(MindAccess.Get, null, null, "memStreamPortalMediators");
		GiskardMind.access(MindAccess.Insert, appJson, appStreamPortal, memStreamPortalMediators);

		MindHandle typPublisher = GiskardMind.access(MindAccess.Get, null, null, "typPublisher");
		MindHandle pubDust = GiskardMind.access(MindAccess.Get, typPublisher, null, "pubDust");
		MindHandle memKnowledgeToken = GiskardMind.access(MindAccess.Get, null, null, "memKnowledgeToken");
		GiskardMind.access(MindAccess.Set, "Dust", pubDust, memKnowledgeToken);

		MindHandle modBrainJava = GiskardMind.access(MindAccess.Get, typModule, null, "modBrainJava");
		MindHandle memKnowledgePublisher = GiskardMind.access(MindAccess.Get, null, null, "memKnowledgePublisher");
		GiskardMind.access(MindAccess.Set, pubDust, modBrainJava, memKnowledgePublisher);
		GiskardMind.access(MindAccess.Set, "BrainJava", modBrainJava, memKnowledgeToken);

		MindHandle typJavaClass = GiskardMind.access(MindAccess.Get, null, null, "typJavaClass");
		MindHandle memJavaClassName = GiskardMind.access(MindAccess.Get, null, null, "memJavaClassName");

		MindHandle javaStreamProviderLocal = GiskardMind.access(MindAccess.Get, typJavaClass, null, "javaStreamProviderLocal");
		GiskardMind.access(MindAccess.Set, "", javaStreamProviderLocal, memJavaClassName);
		MindHandle javaStreamMediatorJson = GiskardMind.access(MindAccess.Get, typJavaClass, null, "javaStreamMediatorJson");
		GiskardMind.access(MindAccess.Set, "", javaStreamMediatorJson, memJavaClassName);
		MindHandle javaStreamPortal = GiskardMind.access(MindAccess.Get, typJavaClass, null, "javaStreamPortal");
		GiskardMind.access(MindAccess.Set, "", javaStreamPortal, memJavaClassName);

		MindHandle memModuleImplementations = GiskardMind.access(MindAccess.Get, null, null, "memModuleImplementations");
		GiskardMind.access(MindAccess.Insert, javaStreamProviderLocal, modBrainJava, memModuleImplementations, agtStreamProviderLocal);
		GiskardMind.access(MindAccess.Insert, javaStreamMediatorJson, modBrainJava, memModuleImplementations, agtStreamMediatorJson);
		GiskardMind.access(MindAccess.Insert, javaStreamPortal, modBrainJava, memModuleImplementations, agtStreamPortal);

		MindHandle typApp = GiskardMind.access(MindAccess.Get, null, null, "typApp");
		MindHandle main = GiskardMind.access(MindAccess.Get, typApp, null, "main");
		GiskardMind.access(MindAccess.Set, pubDust, main, memKnowledgePublisher);
		MindHandle memAppModules = GiskardMind.access(MindAccess.Get, null, null, "memAppModules");
		GiskardMind.access(MindAccess.Insert, modBrainJava, main, memAppModules);
		MindHandle memAppStreamProvider = GiskardMind.access(MindAccess.Get, null, null, "memAppStreamProvider");
		GiskardMind.access(MindAccess.Set, appStreamProvider, main, memAppStreamProvider);
		MindHandle memAppStreamPortal = GiskardMind.access(MindAccess.Get, null, null, "memAppStreamPortal");
		GiskardMind.access(MindAccess.Set, appStreamPortal, main, memAppStreamPortal);
		MindHandle memPublishedVersionMajor = GiskardMind.access(MindAccess.Get, null, null, "memPublishedVersionMajor");
		GiskardMind.access(MindAccess.Set, 1, main, memPublishedVersionMajor);
		GiskardMind.access(MindAccess.Set, "HelloWorld", main, memKnowledgeToken);
		
		MindHandle theBrain = GiskardMind.access(MindAccess.Get, null, null, "theBrain");
		MindHandle memBrainDialogs = GiskardMind.access(MindAccess.Get, null, null, "memBrainDialogs");
		GiskardMind.access(MindAccess.Insert, main, theBrain, memBrainDialogs);
	}

	public static void main(String[] args) throws Exception {
		new DustHelloWorld().boot();
	}

}
