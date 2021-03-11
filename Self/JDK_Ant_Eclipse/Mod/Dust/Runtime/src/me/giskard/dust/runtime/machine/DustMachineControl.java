package me.giskard.dust.runtime.machine;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.dust.runtime.knowledge.DustKnowledgeBlock;
import me.giskard.dust.runtime.knowledge.DustKnowledgeContext;
import me.giskard.tokens.DustTokensGeneric;
import me.giskard.tokens.DustTokensMachine;

public abstract class DustMachineControl implements DustMachineConsts, GiskardConsts.MiNDAgent, DustTokensMachine, DustTokensGeneric {

	public static class Invocation implements DustContext, MiNDAgent {
		DustContext ctx;
		Invocation root;
		MiNDAgent agent;
		
		public Invocation(DustMachineAgora agora, MiNDToken tAgent, MiNDToken tParam) throws Exception {
			DustContext cc = agora.knowledge;
			if (null == agora.invocation) {
				root = this;
				ctx = new DustKnowledgeContext((DustKnowledgeContext) agora.knowledge);
			} else {
				root = agora.invocation;
				ctx = new DustKnowledgeContext((DustKnowledgeContext) root.ctx);
			}
			
			
			DustKnowledgeBlock b;
			b = cc.access(MiNDAccessCommand.Get, null, tAgent);
			ctx.put(MTMEMBER_ACTION_THIS, new DustKnowledgeBlock(b));
			
			b = cc.access(MiNDAccessCommand.Get, null, tParam);
			ctx.put(MTMEMBER_ACTION_PARAM, b);

			ctx.put(MTMEMBER_ACTION_LOCAL, new DustKnowledgeBlock((DustKnowledgeContext) root.ctx));

			agent = agora.nativeConnector.access(MiNDAccessCommand.Add, null, tAgent);
		}

		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			return agent.process(action, params);
		}
		
		@Override
		public MiNDToken defineToken(MiNDTokenType type, String name, Object... params) {
			return ctx.defineToken(type, name, params);
		}

		@Override
		public void selectByPath(MiNDToken target, Object... path) {
			ctx.selectByPath(target, path);
		}

		@Override
		public <RetType> RetType access(MiNDAccessCommand cmd, Object val, MiNDToken target, Object... valPath) {
			return ctx.access(cmd, val, target, valPath);
		}

		@Override
		public void put(MiNDToken token, Object block) {
			ctx.put(token, block);
		}
		
		public void invoke(DustMachineAgora dustMachineAgora, MiNDToken tAgent, MiNDToken tParam) {
			// TODO Auto-generated method stub
			
		}

	}

	public static class Iteration extends DustMachineControl {
		int repMin;
		int repMax;

		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			MiNDResultType ret = MiNDResultType.ACCEPT;
			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				repMin = Giskard.access(MiNDAccessCommand.Get, 0, MTMEMBER_ACTION_THIS, MTMEMBER_RANGE_INT_MIN);
				repMax = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_RANGE_INT_MAX);
				break;
			case Process:
				int c = Giskard.access(MiNDAccessCommand.Get, -1, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
				
				if ( -1 == c ) {
					c = repMin;
				} else {
					++c;
				}
				
				Giskard.log(MiNDEventLevel.INFO, "Repeat called", c);
				
				Giskard.access(MiNDAccessCommand.Set, c, MTMEMBER_ACTION_THIS, MTMEMBER_ITERATOR_INDEX);
				ret = (c < repMax) ? MiNDResultType.READ : MiNDResultType.ACCEPT_PASS;
				
				break;
			case Release:
				break;
			}
			return ret;
		}
	}

	public static class Sequence extends DustMachineControl {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				break;
			case Process:
				break;
			case Release:
				break;
			}
			return null;
		}
	}

	public static class Selection extends DustMachineControl {
		@Override
		public MiNDResultType process(MiNDAgentAction action, Object... params) throws Exception {
			switch ( action ) {
			case Begin:
				break;
			case End:
				break;
			case Init:
				break;
			case Process:
				break;
			case Release:
				break;
			}
			return null;
		}
	}
}
