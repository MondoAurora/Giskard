package me.giskard.dust.runtime.machine;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeConsts;
import me.giskard.dust.runtime.DustRuntimeUtils;
import me.giskard.dust.runtime.knowledge.DustKnowledgeBlock;
import me.giskard.dust.runtime.knowledge.DustKnowledgeContext;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tools.GisToolsModuleServices;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustMachineAgora
		implements DustMachineConsts, DustRuntimeConsts.DustMachine, DustTokensMachine, DustTokensMind, DustTokensGeneric {

	class Dialog {

		public class Invocation implements MiNDAgent {
			DustContext ctx;
			MiNDAgent agent;

			public Invocation(MiNDToken tAgent, MiNDToken tParam) throws Exception {

				ctx = new DustKnowledgeContext((DustKnowledgeContext) ctxDlg);
				ctx.put(MTMEMBER_ACTION_DIALOG, bDlg);
				
				DustContext ctxSrc = (null == current) ? knowledge : current.ctx;

				DustKnowledgeBlock b;
				b = ctxSrc.access(MiNDAccessCommand.Get, null, tAgent);
				ctx.put(MTMEMBER_ACTION_THIS, new DustKnowledgeBlock(b));

				b = ctxSrc.access(MiNDAccessCommand.Get, null, tParam);
				ctx.put(MTMEMBER_ACTION_PARAM, b);

				ctx.put(MTMEMBER_ACTION_LOCAL, new DustKnowledgeBlock((DustKnowledgeContext) ctx));

				agent = nativeConnector.access(MiNDAccessCommand.Add, null, tAgent);
				
				if ( agent instanceof DustMachineControl ) {
					((DustMachineControl) agent).dialog = Dialog.this;
				}
			}

			@Override
			public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
				return agent.process(action, params);
			}
		}

		private DustContext ctxDlg;
		private DustKnowledgeBlock bDlg;

		private Invocation current;
		private Stack<Invocation> callStack;

		public Dialog(MiNDToken tAgent, MiNDToken tParam) throws Exception {
			ctxDlg = new DustKnowledgeContext((DustKnowledgeContext) knowledge);
			ctxDlg.put(MTMEMBER_ACTION_DIALOG, bDlg = new DustKnowledgeBlock((DustKnowledgeContext) ctxDlg));

			current = new Invocation(tAgent, tParam);
		}
		
		public Invocation getCurrent() {
			return current;
		}

		public Invocation relay(MiNDToken tAgent, MiNDToken tParam) throws Exception {
			push(new Invocation(tAgent, tParam));
			current.process(MiNDAgentAction.Init);
			return current;
		}

		public Invocation push(Invocation invocation) throws Exception {
			if ( null == callStack ) {
				callStack = new Stack<>();
			}
			callStack.push(current);
			current = invocation;
			
			return current;
		}

		public MiNDResultType execute() throws Exception {
			MiNDResultType ret = current.process(MiNDAgentAction.Init);

			ret = current.process(MiNDAgentAction.Begin);
			do {
				ret = current.process(MiNDAgentAction.Process);
				ctxDlg.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_DIALOG);

				if ( !GiskardUtils.isAgentRead(ret) ) {
					if ( (null != callStack) && !callStack.isEmpty() ) {
						current = callStack.pop();
					} else {
						break;
					}
				}
				
			} while (true);

			current.process(MiNDAgentAction.End);
			current.process(MiNDAgentAction.Release);
			
			return ret;
		}
	}

	private DustMachineModule modMind;
	private Map<String, DustMachineModule> modules = new TreeMap<>();

	DustContext knowledge;
	Dialog dialog;
	NativeConnector nativeConnector;

	@Override
	public void init(DustContext knowledge_, MiNDAgent agent) throws Exception {
		this.knowledge = knowledge_;

		modMind = new DustMachineModule(MODULE_NAME, agent);
		modules.put(MODULE_NAME, modMind);

		nativeConnector = DustRuntimeUtils.createRuntimeComponent(CLASSPATH_NATIVECONNECTOR);
	}

	@Override
	public Object addModule(String modName, String ver) throws Exception {
		if ( modules.containsKey(modName) ) {
			GiskardException.wrap(null, "Module already loaded", modName);
		}

		ClassLoader clMod = GisToolsModuleServices.getClassLoader(modName, ver);

		if ( null == clMod ) {
			Giskard.log(MiNDEventLevel.INFO, "would download module from server", modName, ver);
			return null;
		}

		Giskard.log(MiNDEventLevel.TRACE, "Adding module", modName, ver);
		DustMachineModule mod = new DustMachineModule(modName, clMod);
		modules.put(modName, mod);

		optLoadNativeConn();

		return mod;
	}

	@Override
	public DustContext getContext() {
		return (null == dialog) ? knowledge : dialog.current.ctx;
	}

	@Override
	public MiNDResultType invoke(Object... agentPath) throws Exception {
		int pl = agentPath.length;
		MiNDToken tAgent = (0 < pl) ? (MiNDToken) agentPath[0] : MTMEMBER_ACTION_TARGET;
		MiNDToken tParam = (1 < pl) ? (MiNDToken) agentPath[1] : MTMEMBER_ACTION_PARAM;
		
		if ( null == dialog ) {
			dialog = new Dialog(tAgent, tParam);
			return dialog.execute();
		} else {
			dialog.relay(tAgent, tParam);
			return MiNDResultType.ACCEPT;
		}
	}

	public void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

}
