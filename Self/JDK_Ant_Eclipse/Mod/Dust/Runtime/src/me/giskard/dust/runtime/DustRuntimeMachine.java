package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import me.giskard.Giskard;
import me.giskard.GiskardException;
import me.giskard.GiskardUtils;

public class DustRuntimeMachine implements DustRuntimeConsts, DustRuntimeNotifier {

	Activity createActivity(Dialog dlg) throws Exception {
		Activity ret = new Activity();
		ret.init(dlg);
		return ret;
	}

	class Dialog {
		DustRuntimeDataContext ctxDialog;
		Set<Activity> activities = new HashSet<>();

		public Dialog(Object hDlg) throws Exception {
			ctxDialog = new DustRuntimeDataContext(knowledge, HANDLE_DIALOG);
			DustRuntimeDataBlock bRoot = ctxDialog.getRootBlock();
			bRoot.access(MiNDAccessCommand.Set, HANDLE_DIALOG, MTMEM_GENERIC_ACTION_DIALOG, null);

			// deep copy of all entities

			int actCount = Giskard.access(MiNDAccessCommand.Get, 0, hDlg, MTMEM_DIALOG_CONTEXT_ACTIVITIES, KEY_SIZE);
			for (int i = 0; i < actCount; ++i) {
				Integer act = Giskard.access(MiNDAccessCommand.Get, null, hDlg, MTMEM_DIALOG_CONTEXT_ACTIVITIES, i);
				ctxDialog.rootBlock.access(MiNDAccessCommand.Set, act, MTMEM_GENERIC_ACTIVITY_INSTANCE, null);
				activities.add(createActivity(this));
			}
		}
	}

	class Activity {
		private Dialog dlg;

		private Invocation current;
		private Stack<Invocation> callStack;

		MiNDResultType state;

		public void init(Dialog dlg) throws Exception {
			this.dlg = dlg;
			current = invoke();
		}

		public MiNDResultType step(Actor actor) throws Exception {
//			MiNDResultType ret = MiNDResultType.Read;
//
//			state = GiskardUtils.isAgentReject(ret) ? ret : current.step(actor);

			state = current.step(actor);
			
			if ( !GiskardUtils.isAgentRead(state) ) {
				current.optEnd(actor);
				if ( (null != callStack) && !callStack.isEmpty() ) {
					current = callStack.pop();
					state = MiNDResultType.Read;
				}
			}

			return state;
		}

		Invocation invoke() throws Exception {
			Invocation ret = new Invocation();
			ret.init(this);
			return ret;
		}

		public Invocation getCurrent() {
			return current;
		}

		public Invocation relay(Object act) throws Exception {
			dlg.ctxDialog.rootBlock.access(MiNDAccessCommand.Set, act, MTMEM_GENERIC_ACTIVITY_INSTANCE, null);
			push(invoke());
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
	}

	public class Invocation {
		Activity activity;

		DustRuntimeDataBlock bThis;
		
		MiNDAgent agent;
		boolean blockAgent;
		boolean firstCall;

		Actor runningActor;
		MiNDResultType state;

		void init(Activity activity_) throws Exception {
			this.activity = activity_;

			DustRuntimeDataContext ctxDialog = activity.dlg.ctxDialog;
			Integer handle = ctxDialog.rootBlock.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTIVITY_INSTANCE, null);
			DustRuntimeDataBlock bTarget = ctxDialog.getEntity(handle);

			bThis = new DustRuntimeDataBlock(ctxDialog, bTarget);
		}
		
		public MiNDResultType optEnd(Actor actor) throws Exception {
			if ( !firstCall ) {
				firstCall = true;
				try {
					runningActor = actor;
					if ( blockAgent ) {
						((MiNDAgentBlock) agent).mindAgentEnd();
					}
				} finally {
					runningActor = null;
				}
			}
			
			return state;
		}

		MiNDResultType step(Actor actor) throws Exception {
			try {
				runningActor = actor;

				if ( null == agent ) {
					agent = getNativeConn().createNative(MTMEM_GENERIC_ACTION_THIS);
					if ( agent instanceof RuntimeAgent ) {
						((RuntimeAgent) agent).setInvocation(this);
					}
//					agent.mindAgentProcess(); opt init called in createNative
					firstCall = true;
					blockAgent = ( agent instanceof MiNDAgentBlock);
				}
				
				if ( firstCall ) {
					if ( blockAgent ) {
						((MiNDAgentBlock) agent).mindAgentBegin();
					}
					firstCall = false;
				}
				
				state = agent.mindAgentProcess();
			} finally {
				runningActor = null;
			}

			return state;
		}
	}

	public class Actor implements Runnable {
		DustRuntimeDataContext ctx;
		Activity last;
		Activity current;

		volatile boolean stopRequest;

		public Actor() {
			ctx = new DustRuntimeDataContext(knowledge, HANDLE_INVOCATION);
			ctx.rootBlock.access(MiNDAccessCommand.Set, HANDLE_THIS, MTMEM_GENERIC_ACTION_THIS, null);
		}

		public void optSyncCtx() {
			if ( current != last ) {
				DustRuntimeDataContext dc = current.dlg.ctxDialog;
				ctx.parentCtx = dc;
				ctx.entities.put(HANDLE_DIALOG, dc.rootBlock);
			}
			Invocation invoc = current.current;
			ctx.entities.put(HANDLE_THIS, invoc.bThis);
		}

		@Override
		public void run() {
			for (stopRequest = false; !stopRequest;) {
				current = getNextActivity(this);

				if ( null == current ) {
					synchronized (this) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							stopRequest = true;
						}
					}
				} else {
					optSyncCtx();
					try {
						MiNDResultType result = current.step(this);
						ctx.commit();

						if ( !GiskardUtils.isAgentRead(result) ) {
							// for now...
							stopRequest = true;
							last = null;
						} else {
							last = current;
						}
					} catch (Throwable e) {
						GiskardException.wrap(e);
					} finally {
						current = null;
					}
				}
			}
		}

		synchronized void stop() {
			stopRequest = true;
			notifyAll();
		}
	}

	class MachineListener implements MiNDAgent {
		@Override
		public MiNDResultType mindAgentProcess() throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

				Object chg = Giskard.access(MiNDAccessCommand.Get, null, MTMEM_GENERIC_ACTION_DIALOG, MTMEM_DIALOG_CONTEXT_CHANGE);
				Object pt = Giskard.access(MiNDAccessCommand.Get, null, chg, MTMEM_DIALOG_VISITINFO_TOKEN);

				if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEM_MACHINE_MACHINE_MODULES).entityHandle, pt) ) {
					String modName = Giskard.access(MiNDAccessCommand.Get, null, chg,
							MTMEM_DIALOG_VISITINFO_LINKNEW, MTMEM_TEXT_PLAINTEXT_STRING);
					String modVer = Giskard.access(MiNDAccessCommand.Get, null, chg, MTMEM_DIALOG_VISITINFO_LINKNEW,
							MTMEM_GENERIC_VERSIONED_SIGNATURE);

					if ( !MODULE_NAME.equals(modName) ) {
						nativeConn.addModule(modName, modVer);
					}
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEM_MACHINE_MODULE_NATIVES).entityHandle, pt) ) {
					Object agent = Giskard.access(MiNDAccessCommand.Get, null, chg, MTMEM_DIALOG_VISITINFO_KEYMAP);

					if ( null != agent ) {
						Class<?> c = Giskard.access(MiNDAccessCommand.Get, null, chg, MTMEM_DIALOG_VISITINFO_LINKNEW,
								MTMEM_GENERIC_VALUE_RAW);
						if ( null != c ) {
							nativeConn.addAgentClass((Integer) agent, c);
						}
					}
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEM_GENERIC_CONN_PROVIDES).entityHandle, pt) ) {
					String unitName = Giskard.access(MiNDAccessCommand.Get, null, chg,
							MTMEM_DIALOG_VISITINFO_LINKNEW, MTMEM_TEXT_PLAINTEXT_STRING);

					nativeConn.addUnitImpl(unitName);
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEM_MACHINE_MACHINE_DIALOGS).entityHandle, pt) ) {
					Object dlg = Giskard.access(MiNDAccessCommand.Get, null, chg, MTMEM_DIALOG_VISITINFO_LINKNEW);
//					Giskard.access(MiNDAccessCommand.Set, dlg, MTMEM_GENERIC_LINK_ONE);

					dialogs.add(new Dialog(dlg));
					singleActor = new Actor();
					singleActor.run();
				}

			return ret;
		}
	}

	DustRuntimeDataContext knowledge;
	DustRuntimeNativeConnector nativeConn;
	MachineListener ml = new MachineListener();

	NotifDispatcher mainNotifier;

	Set<Dialog> dialogs = new HashSet<>();
	Actor singleActor;

	public DustRuntimeMachine() {
		this.mainNotifier = new NotifDispatcher();
		this.knowledge = new DustRuntimeDataContext(null, HANDLE_MACHINE);

		MiNDToken[] bootTokens = { MTMEM_TEXT_PLAINTEXT_STRING, MTMEM_GENERIC_CONN_OWNER, MTMEM_MODEL_ENTITY_PRIMARYTYPE,
				MTMEM_MODEL_ENTITY_IDGLOBAL, MTMEM_MODEL_ENTITY_UNIT, MTTYP_NARRATIVE_AGENT, MTTYP_IDEA_MEMBER, MTTYP_GENERIC_TAG, MTTYP_IDEA_TYPE,
				MTTYP_MODEL_UNIT };

		Giskard.log(MiNDEventLevel.Trace, "Boot tokens registered", bootTokens);
	}

	public void init(MiNDAgent agent) throws Exception {
		nativeConn = new DustRuntimeNativeConnector(agent);
		mainNotifier.setListener(true, ml);
	}

	public DustRuntimeNativeConnector getNativeConn() {
		return nativeConn;
	}

	public DustRuntimeDataContext getContext() {
		return (null == singleActor) ? knowledge : singleActor.ctx;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return getContext().access(mainNotifier, cmd, val, valPath);
	}

	public Actor getActor() {
		return singleActor;
	}

	Activity getNextActivity(Actor actor) {
		Activity a = actor.last;

		if ( null == actor.last ) {
			a = dialogs.iterator().next().activities.iterator().next();
		}

		return a;
	}

}
