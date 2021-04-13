package me.giskard.dust.runtime;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class DustRuntimeMachine implements DustRuntimeConsts, DustRuntimeNotifier {

	Activity createActivity(Dialog dlg) throws Exception {
		Activity ret = new Activity();
		ret.init(dlg);
		return ret;
	}

	class Dialog {
		private DustRuntimeContext ctxDialog;
		Set<Activity> activities = new HashSet<>();

		public Dialog() throws Exception {
			ctxDialog = new DustRuntimeContext(knowledge, HANDLE_DIALOG);
			DustRuntimeDataBlock bRoot = ctxDialog.getRootBlock();
			bRoot.access(MiNDAccessCommand.Set, HANDLE_DIALOG, MTMEMBER_ACTION_DIALOG, null);

			// deep copy of all entities

			int actCount = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_LINK_ONE, MTMEMBER_DIALOG_ACTIVITIES, KEY_SIZE);
			for (int i = 0; i < actCount; ++i) {
				Integer act = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_LINK_ONE, MTMEMBER_DIALOG_ACTIVITIES, i);
				ctxDialog.rootBlock.access(MiNDAccessCommand.Set, act, MTMEMBER_CALL_TARGET, null);
//				Giskard.access(MiNDAccessCommand.Get, MTMEMBER_CALL_TARGET, MTMEMBER_LINK_ONE, MTMEMBER_DIALOG_ACTIVITIES, i);
				activities.add(createActivity(this));
			}
		}
	}

	class Activity {
		private Dialog dlg;

		private Invocation current;
		private Stack<Invocation> callStack;

		public void init(Dialog dlg) throws Exception {
			this.dlg = dlg;
			current = invoke();
		}

		Invocation invoke() throws Exception {
			Invocation ret = new Invocation();
			ret.init(this);
			return ret;
		}

		public Invocation getCurrent() {
			return current;
		}

		public Invocation relay() throws Exception {
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
		DustRuntimeDataBlock bParam;
		MiNDAgent agent;

		Actor runningActor;
		MiNDResultType state;

		void init(Activity activity_) throws Exception {
			this.activity = activity_;

			DustRuntimeContext ctxDialog = activity.dlg.ctxDialog;
			Integer handle = ctxDialog.access(NULL_NOTIF, MiNDAccessCommand.Get, null, MTMEMBER_CALL_TARGET);
			DustRuntimeDataBlock bTarget = ctxDialog.getEntity(handle);

			bThis = new DustRuntimeDataBlock(null, bTarget);

			handle = ctxDialog.access(NULL_NOTIF, MiNDAccessCommand.Get, null, MTMEMBER_CALL_PARAM);
			bParam = (null != handle) ? ctxDialog.getEntity(handle) : null;
		}

		MiNDResultType step(Actor a) throws Exception {
			try {
				runningActor = a;

				if ( null == agent ) {
					agent = getNativeConn().createNative(MTMEMBER_ACTION_THIS);
					if ( agent instanceof RuntimeAgent ) {
						((RuntimeAgent) agent).setInvocation(this);
					}
					agent.process(MiNDAgentAction.Init);
				}
				state = agent.process(MiNDAgentAction.Process);
			} finally {
				runningActor = null;
			}
			
			return state;
		}
	}

	public class Actor implements Runnable {
		DustRuntimeContext ctx;
		Invocation last;
		Invocation current;

		volatile boolean stopRequest;

		public Actor() {
			ctx = new DustRuntimeContext(knowledge, HANDLE_INVOCATION);
			ctx.rootBlock.access(MiNDAccessCommand.Set, HANDLE_THIS, MTMEMBER_ACTION_THIS, null);
			ctx.rootBlock.access(MiNDAccessCommand.Set, HANDLE_PARAM, MTMEMBER_ACTION_PARAM, null);
		}

		public void syncCtx(Dialog dlg) {
			ctx.parentCtx = dlg.ctxDialog;
			ctx.entities.put(HANDLE_DIALOG, dlg.ctxDialog.rootBlock);
			ctx.entities.put(HANDLE_THIS, current.bThis);
			ctx.entities.put(HANDLE_PARAM, current.bParam);
		}

		public void execute(Dialog dlg) throws Exception {
			current = last = dlg.activities.iterator().next().invoke();
			syncCtx(dlg);
			run();
		}

		@Override
		public void run() {
			for (stopRequest = false; !stopRequest;) {
				current = getNextTask(this);

				if ( null == current ) {
					synchronized (this) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							stopRequest = true;
						}
					}
				} else {
					if ( last != current ) {
						syncCtx(current.activity.dlg);
					}

					try {
						current.step(this);
						ctx.commit();
						last = current;
						
						if ( GiskardUtils.isAgentRead(current.state) ) {
							current = null;
						} else {
							// for now...
							stopRequest = true;
						}
						
					} catch (Throwable e) {

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
		public MiNDResultType process(MiNDAgentAction action) throws Exception {
			MiNDResultType ret = MiNDResultType.Accept;

			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				break;
			case Process:
				Object pt = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_TOKEN);

				if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_MACHINE_MODULES).entityHandle, pt) ) {
					String modName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM,
							MTMEMBER_VISITINFO_LINKNEW, MTMEMBER_PLAIN_STRING);
					String modVer = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
							MTMEMBER_VERSIONED_SIGNATURE);

					if ( !MODULE_NAME.equals(modName) ) {
						nativeConn.addModule(modName, modVer);
					}
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_MODULE_NATIVES).entityHandle, pt) ) {
					Object agent = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_KEYMAP);

					if ( null != agent ) {
						Class<?> c = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW,
								MTMEMBER_VARIANT_VALUE);
						if ( null != c ) {
							nativeConn.addAgentClass((Integer) agent, c);
						}
					}
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_CONN_PROVIDES).entityHandle, pt) ) {
					String unitName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM,
							MTMEMBER_VISITINFO_LINKNEW, MTMEMBER_PLAIN_STRING);

					nativeConn.addUnitImpl(unitName);
				} else if ( GiskardUtils.isEqual(((DustRuntimeToken) MTMEMBER_MACHINE_DIALOGS).entityHandle, pt) ) {
//					String dlgName = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_PARAM,
//							MTMEMBER_VISITINFO_LINKNEW, MTMEMBER_PLAIN_STRING);

					Giskard.access(MiNDAccessCommand.Get, MTMEMBER_LINK_ONE, MTMEMBER_ACTION_PARAM, MTMEMBER_VISITINFO_LINKNEW);

					Dialog dlg = new Dialog();

					dialogs.add(dlg);

					singleActor = new Actor();
					singleActor.execute(dlg);
				}
				break;
			case Release:
				break;
			}

			return ret;
		}
	}

	DustRuntimeContext knowledge;
	DustRuntimeNativeConnector nativeConn;
	MachineListener ml = new MachineListener();

	NotifDispatcher mainNotifier;

	Set<Dialog> dialogs = new HashSet<>();
	Actor singleActor;

	public DustRuntimeMachine() {
		this.mainNotifier = new NotifDispatcher();
		this.knowledge = new DustRuntimeContext(null, HANDLE_MACHINE);

		MiNDToken[] bootTokens = { MTMEMBER_PLAIN_STRING, MTMEMBER_CONN_OWNER, MTMEMBER_ENTITY_PRIMARYTYPE,
				MTMEMBER_ENTITY_STOREID, MTMEMBER_ENTITY_STOREUNIT, MTTYPE_AGENT, MTTYPE_MEMBER, MTTYPE_TAG, MTTYPE_TYPE,
				MTTYPE_UNIT };

		Giskard.log(MiNDEventLevel.Trace, "Boot tokens registered", bootTokens);
	}

	public void init(MiNDAgent agent) throws Exception {
		nativeConn = new DustRuntimeNativeConnector(agent);
		mainNotifier.setListener(true, ml);
	}

	public DustRuntimeNativeConnector getNativeConn() {
		return nativeConn;
	}

	public DustRuntimeContext getContext() {
		return (null == singleActor) ? knowledge : singleActor.ctx;
	}

	public <RetType> RetType access(MiNDAccessCommand cmd, Object val, Object... valPath) {
		return getContext().access(mainNotifier, cmd, val, valPath);
	}

	Invocation getNextTask(Actor actor) {
		return actor.last;
	}

}
