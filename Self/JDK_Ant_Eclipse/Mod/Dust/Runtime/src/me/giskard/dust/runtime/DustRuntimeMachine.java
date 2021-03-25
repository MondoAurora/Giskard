package me.giskard.dust.runtime;

import java.util.Stack;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustRuntimeMachine implements DustRuntimeConsts {
	
	/*
	 * Main purposes are not yet visible:
	 *  - Thread management
	 *  - Dialog thread connection, priorities, watchdogs
	 */

	static class Dialog {
		private DustRuntimeMachine machine;
		private DustRuntimeContext ctx;

		private Invocation current;
		private Stack<Invocation> callStack;

		public Dialog(DustRuntimeMachine machine_) throws Exception {
			this.machine = machine_;
			ctx = new DustRuntimeContext(machine.knowledge, HANDLE_DIALOG);
//			ctx.getRootBlock().access(MiNDAccessCommand.Set, HANDLE_DIALOG, (DustRuntimeToken) MTMEMBER_ACTION_DIALOG, null);

			current = invoke();
		}

		public DustRuntimeMachine getMachine() {
			return machine;
		}

		Invocation invoke() throws Exception {
			Invocation ret = new Invocation();
			ret.setDialog(this);
			return ret;
		}

		public Invocation getCurrent() {
			return current;
		}

		public Invocation relay() throws Exception {
			push(invoke());
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
				ctx.access(MiNDAccessCommand.Set, GisToolsTokenTranslator.toToken(ret), MTMEMBER_ACTION_DIALOG,
						MTMEMBER_ENTITY_TAGS);

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

		public DustRuntimeContext getContext() {
			return (null == current) ? ctx : current.ctx;
		}
	}

	public static class Invocation implements MiNDAgent {
		Dialog dlg;
		DustRuntimeContext ctx;
		DustRuntimeMachine machine;
		MiNDAgent agent;

		void setDialog(Dialog dlg_) throws Exception {
			this.dlg = dlg_;
			machine = dlg.getMachine();

			DustRuntimeContext ctxSrc = dlg.getContext();

			ctx = new DustRuntimeContext(ctxSrc, HANDLE_INVOCATION);

			DustRuntimeDataBlock bRoot = ctx.getRootBlock();
			// bRoot.put((DustTokenMember) MTMEMBER_ACTION_DIALOG, dlg.ctx.getRootBlock());

			Integer handle = ctxSrc.access(MiNDAccessCommand.Get, null, MTMEMBER_CALL_TARGET);
			DustRuntimeDataBlock bTarget = ctxSrc.getEntity(handle);
			DustRuntimeDataBlock e = new DustRuntimeDataBlock(ctx, bTarget);
			bRoot.access(MiNDAccessCommand.Set, e.getHandle(), (DustRuntimeToken.Member) MTMEMBER_ACTION_THIS, null);

			handle = ctxSrc.access(MiNDAccessCommand.Get, null, MTMEMBER_CALL_PARAM);
			if ( null != handle ) {
				bRoot.access(MiNDAccessCommand.Set, handle, (DustRuntimeToken.Member) MTMEMBER_ACTION_PARAM, null);
			}
		}

		@Override
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			if ( null == agent ) {
				agent = machine.getNativeConn().access(MiNDAccessCommand.Add, null, MTMEMBER_ACTION_THIS);
				if ( agent instanceof DustRuntimeAgentControl ) {
					((DustRuntimeAgentControl) agent).dialog = dlg;
				}
			}

			return agent.process(action);
		}
	}

	DustRuntimeContext knowledge;
	Dialog dialog;
	DustRuntimeNativeConnector nativeConn;

	public DustRuntimeMachine() {
		this.knowledge = new DustRuntimeContext();

		MiNDToken[] bootTokens = { MTMEMBER_PLAIN_STRING, MTMEMBER_CONN_OWNER, MTMEMBER_ENTITY_PRIMARYTYPE,
				MTMEMBER_ENTITY_STOREID, MTMEMBER_ENTITY_STOREUNIT, MTTYPE_AGENT, MTTYPE_MEMBER, MTTYPE_TAG, MTTYPE_TYPE,
				MTTYPE_UNIT };

		Giskard.log(MiNDEventLevel.TRACE, "Boot tokens registered", bootTokens);

	}

	public void init(MiNDAgent agent) throws Exception {
		nativeConn = new DustRuntimeNativeConnector(agent);
	}

	public DustRuntimeNativeConnector getNativeConn() {
		return nativeConn;
	}

	public DustRuntimeContext getContext() {
		return (null == dialog) ? knowledge : dialog.current.ctx;
	}

	public MiNDResultType invoke() throws Exception {
		if ( null == dialog ) {
			dialog = new Dialog(this);
			return dialog.execute();
		} else {
			dialog.relay();
			return MiNDResultType.ACCEPT;
		}
	}

}
