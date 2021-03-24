package me.giskard.dust.runtime.machine;

import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;
import me.giskard.dust.runtime.DustRuntimeMeta.DustTokenMember;
import me.giskard.dust.runtime.knowledge.DustKnowledgeBlock;
import me.giskard.dust.runtime.knowledge.DustKnowledgeContext;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;
import me.giskard.tokens.DustTokensMind;
import me.giskard.tokens.DustTokensText;
import me.giskard.tools.GisToolsModuleServices;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustMachineAgora
		implements DustMachineConsts, DustTokensMachine, DustTokensMind, DustTokensGeneric, DustTokensText {

	class Dialog {
		private DustKnowledgeContext ctx;

		private Invocation current;
		private Stack<Invocation> callStack;

		public Dialog() throws Exception {
			ctx = new DustKnowledgeContext((DustKnowledgeContext) knowledge, HANDLE_DIALOG);
//			ctx.put(MTMEMBER_ACTION_DIALOG, bDlg = new DustKnowledgeBlock((DustKnowledgeContext) ctx));

			current = new Invocation(this);
		}
		
		public Invocation getCurrent() {
			return current;
		}

		public Invocation relay() throws Exception {
			push(new Invocation(this));
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
				ctx.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);

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

		public DustKnowledgeContext getContext() {
			return (null == current) ? (DustKnowledgeContext) knowledge : current.ctx;
		}
	}
	
	public class Invocation implements MiNDAgent {
		Dialog dlg;
		DustKnowledgeContext ctx;
		MiNDAgent agent;

		public Invocation(Dialog dlg_) throws Exception {
			setDialog( dlg_);
		}
		
		void setDialog(Dialog dlg_) throws Exception {
			this.dlg = dlg_;

			DustKnowledgeContext ctxSrc = dlg.getContext();

			ctx = new DustKnowledgeContext(ctxSrc, HANDLE_INVOCATION);
			
			DustKnowledgeBlock bRoot = ctx.getRootBlock();
//			bRoot.put((DustTokenMember) MTMEMBER_ACTION_DIALOG, dlg.ctx.getRootBlock());

			Integer handle = ctxSrc.access(MiNDAccessCommand.Get, null, MTMEMBER_CALL_TARGET);
			DustKnowledgeBlock bTarget = ctxSrc.getEntity(handle);
			DustKnowledgeBlock e = new DustKnowledgeBlock(ctx, bTarget);
			bRoot.access(MiNDAccessCommand.Set, e.getHandle(), (DustTokenMember) MTMEMBER_ACTION_THIS, null);

			handle = ctxSrc.access(MiNDAccessCommand.Get, null, MTMEMBER_CALL_PARAM);
			if ( null != handle ) {
				bRoot.access(MiNDAccessCommand.Set, handle, (DustTokenMember) MTMEMBER_ACTION_PARAM, null);
			}
		}
		
		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			if ( null == agent ) {
				agent = nativeConnector.access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_THIS);
				if ( agent instanceof DustMachineControl ) {
					((DustMachineControl) agent).dialog = dlg;
				}
			}
			
			return agent.process(action);
		}
	}

	private Map<String, DustMachineModule> modules = new TreeMap<>();

	DustKnowledgeContext knowledge;
	Dialog dialog;
	DustMachineNativeConnector nativeConnector;
	
	public DustMachineAgora() {
		this.knowledge = new DustKnowledgeContext();

		MiNDToken[] bootTokens = { 
				MTMEMBER_PLAIN_STRING, MTMEMBER_CONN_OWNER, MTMEMBER_ENTITY_PRIMARYTYPE, MTMEMBER_ENTITY_STOREID, MTMEMBER_ENTITY_STOREUNIT,
				MTTYPE_AGENT, MTTYPE_MEMBER, MTTYPE_TAG, MTTYPE_TYPE, MTTYPE_UNIT};
		
		Giskard.log(MiNDEventLevel.TRACE, "Boot tokens registered", bootTokens);

	}

	public void init(MiNDAgent agent) throws Exception {
		DustMachineModule modMind = new DustMachineModule(MODULE_NAME, agent);
		modules.put(MODULE_NAME, modMind);

		nativeConnector = new DustMachineNativeConnector();
	}

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

	public DustKnowledgeContext getContext() {
		return (null == dialog) ? knowledge : dialog.current.ctx;
	}

	public MiNDResultType invoke() throws Exception {
		if ( null == dialog ) {
			dialog = new Dialog();
			return dialog.execute();
		} else {
			dialog.relay();
			return MiNDResultType.ACCEPT;
		}
	}

	public void optLoadNativeConn() throws Exception {
		if ( null != nativeConnector ) {
			nativeConnector.process(MiNDAgentAction.Init);
		}
	}

}
