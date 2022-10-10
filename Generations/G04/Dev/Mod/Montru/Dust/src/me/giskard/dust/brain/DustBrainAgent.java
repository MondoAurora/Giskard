package me.giskard.dust.brain;

import java.util.Iterator;
import java.util.Map;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;

public abstract class DustBrainAgent implements DustBrainConsts, GiskardConsts.MiNDAgent {

	public static class DialogIO extends DustBrainAgent {

		@Override
		public MiNDResultType mindAgentStep() throws Exception {
			String str = Giskard.access(MiNDAccessCommand.Peek, null, DIALOG_MEM_IO_MESSAGE, LANG_MEM_TEXT_STRING);
			Giskard.log(str);

			return MiNDResultType.Accept;
		}
	}

	public static class NarrativeFlow extends DustBrainAgent {

		@Override
		public MiNDResultType mindAgentStep() throws Exception {
			MiNDResultType ret;
			boolean endState = true;

			MiNDHandle hSelf = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_JOURNEY_AGENT);
			MiNDHandle hA = Giskard.access(MiNDAccessCommand.Peek, null, hSelf, GENERIC_MEM_GEN_CURSOR);

			if ( null == hA ) {
				hA = Giskard.access(MiNDAccessCommand.Peek, null, hSelf, NARRATIVE_MEM_FLOW_START);
			}

			Giskard.access(MiNDAccessCommand.Set, hA, null, NARRATIVE_MEM_JOURNEY_AGENT);
			MiNDAgent a = DustBrainUtilsDev.getAgent();

			if ( null != a ) {
				try {
					Giskard.log("Executing agent", a);
					ret = a.mindAgentStep();

					if ( ret.accept ) {
						endState = Giskard.access(MiNDAccessCommand.Check, hA, hSelf, NARRATIVE_MEM_FLOW_END);

						if ( endState ) {
							ret = MiNDResultType.Accept;
						} else {
							hA = Giskard.access(MiNDAccessCommand.Peek, hA, hA, NARRATIVE_MEM_FLOW_NEXT);
							Giskard.access(MiNDAccessCommand.Set, hA, hSelf, GENERIC_MEM_GEN_CURSOR);
							ret = MiNDResultType.Read;
						}
					}

				} catch (Throwable t) {

				} finally {
				}
			} else {
				Giskard.log("No agent in the journey");
			}
			Giskard.access(MiNDAccessCommand.Set, hSelf, null, NARRATIVE_MEM_JOURNEY_AGENT);

			return endState ? MiNDResultType.Accept : MiNDResultType.Read;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static class NarrativeVisitor extends DustBrainAgent {
		@Override
		public MiNDResultType mindAgentStep() throws Exception {
			MiNDHandle hSelf = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_JOURNEY_AGENT);

			Iterator vi = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_VISITOR_ITER_VAL);

			if ( null != vi ) {
				if ( vi.hasNext() ) {
					Boolean first = Giskard.access(MiNDAccessCommand.Insert, NARRATIVE_TAG_ACTION_PROCESS,
							NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_KNOWLEDGE_TAGS);
					if ( !first ) {
						Giskard.access(MiNDAccessCommand.Insert, GENERIC_TAG_CONTINUED, NARRATIVE_MEM_VISITOR_INFO,
								MODEL_MEM_KNOWLEDGE_TAGS);
					}

					Object o = vi.next();
					Object k;

					if ( o instanceof Map.Entry ) {
						Map.Entry e = (Map.Entry) o;
						k = e.getKey();
						o = e.getValue();
					} else {
						Long idx = Giskard.access(MiNDAccessCommand.Peek, -1L, NARRATIVE_MEM_VISITOR_INFO,
								MODEL_MEM_INFORMATION_KEY);
						k = idx + 1;
					}

					Giskard.access(MiNDAccessCommand.Set, k, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_KEY);
					Giskard.access(MiNDAccessCommand.Set, o, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_VALUE);
				} else {
					Giskard.access(MiNDAccessCommand.Set, null, null, NARRATIVE_MEM_VISITOR_ITER_VAL);
					Giskard.access(MiNDAccessCommand.Insert, NARRATIVE_TAG_ACTION_END, NARRATIVE_MEM_VISITOR_INFO,
							MODEL_MEM_KNOWLEDGE_TAGS);
				}
				return MiNDResultType.AcceptRead;
			}

			Iterator mi = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_VISITOR_ITER_MEM);
			MiNDHandle hCurrent = Giskard.access(MiNDAccessCommand.Peek, null, null, NARRATIVE_MEM_VISITOR_INFO,
					GENERIC_MEM_GEN_OWNER);

			if ( null == mi ) {
				while (null == hCurrent) {
					hCurrent = Giskard.access(MiNDAccessCommand.Peek, null, hSelf, NARRATIVE_MEM_VISITOR_TOVISIT, 0);
					if ( null == hCurrent ) {
						return MiNDResultType.Reject;
					}

					Boolean newItem = Giskard.access(MiNDAccessCommand.Insert, hCurrent, null, NARRATIVE_MEM_VISITOR_SEEN);
					if ( !newItem ) {
						hCurrent = null;
					} else {
						Giskard.access(MiNDAccessCommand.Set, hCurrent, null, NARRATIVE_MEM_VISITOR_INFO, GENERIC_MEM_GEN_OWNER);
						Giskard.access(MiNDAccessCommand.Set, null, null, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_MEMBER);
						Giskard.access(MiNDAccessCommand.Insert, NARRATIVE_TAG_ACTION_BEGIN, NARRATIVE_MEM_VISITOR_INFO,
								MODEL_MEM_KNOWLEDGE_TAGS);

						mi = Giskard.access(MiNDAccessCommand.Peek, null, hCurrent, null, KEY_ITERATOR);
						Giskard.access(MiNDAccessCommand.Set, mi, null, NARRATIVE_MEM_VISITOR_ITER_MEM);

						return MiNDResultType.AcceptRead;
					}
				}
			} else {
				MiNDHandle hMember = (MiNDHandle) mi.next();
				Giskard.access(MiNDAccessCommand.Set, hMember, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_MEMBER);
				vi = Giskard.access(MiNDAccessCommand.Peek, null, hCurrent, hMember, KEY_ITERATOR);

				MiNDHandle hCmd;
				if ( null == vi ) {
					Object o = Giskard.access(MiNDAccessCommand.Peek, null, hCurrent, hMember);
					Giskard.access(MiNDAccessCommand.Set, null, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_KEY);
					Giskard.access(MiNDAccessCommand.Set, o, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_INFORMATION_VALUE);
					hCmd = NARRATIVE_TAG_ACTION_PROCESS;
				} else {
					Giskard.access(MiNDAccessCommand.Set, vi, null, NARRATIVE_MEM_VISITOR_ITER_VAL);
					hCmd = NARRATIVE_TAG_ACTION_BEGIN;
				}

				Giskard.access(MiNDAccessCommand.Insert, hCmd, NARRATIVE_MEM_VISITOR_INFO, MODEL_MEM_KNOWLEDGE_TAGS);
			}

			return MiNDResultType.Reject;
		}
	}

//	public static class Reach extends DustBrainAgent {
//
//		public boolean isLearning(MiNDHandle hMember) {
//			Boolean learning = eJourney.access(MiNDAccessCommand.Check, GENERIC_TAG_LENIENT, MODEL_MEM_KNOWLEDGE_TAGS,
//					CollType.Set, null);
//
//			if ( Boolean.TRUE.equals(learning) ) {
//				return true;
//			}
//
//			return false;
//		}
//
//		public CollType getCollType(MiNDHandle hMember, MiNDAccessCommand cmd, Object key) {
//
//			if ( (null == key) && (cmd == MiNDAccessCommand.Peek) ) {
//				return CollType.One;
//			}
//
//			DustBrainKnowledge eMember = eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE,
//					CollType.Map, hMember);
//			MiNDHandle h = eMember.access(MiNDAccessCommand.Peek, null, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set,
//					IDEA_TAG_COLLTYPE);
//
//			CollType ret = null;
//
//			if ( null == h ) {
//				ret = DustBrainUtils.guessCollType(cmd, key);
//
//				if ( (null != ret) && isLearning(hMember) ) {
//					h = HANDLE2ENUM.getLeft(ret);
//					eMember.access(MiNDAccessCommand.Insert, h, MODEL_MEM_KNOWLEDGE_TAGS, CollType.Set, IDEA_TAG_COLLTYPE);
//				}
//			} else {
//				ret = HANDLE2ENUM.getRight(h);
//			}
//
//			return ret;
//		}
//
//
//		public DustBrainKnowledge resolveHandle(MiNDHandle h) {
//			return eJourney.access(MiNDAccessCommand.Peek, null, NARRATIVE_MEM_JOURNEY_LOCALKNOWLEDGE, CollType.Map, h);
//		}
//
//		@SuppressWarnings("unchecked")
//		public <RetType> RetType access_(MiNDAccessCommand cmd, Object val, Object... valPath) {
//			Object ret = null;
//			int last = valPath.length - 1;
//			int i = -1;
//
//			if ( 0 > last ) {
//				// probably create a temporary entity?
//			} else {
//				ret = valPath[0];
//
//				for (i = 1; i <= last;) {
//					DustBrainKnowledge e = resolveHandle((MiNDHandle) ret);
//					MiNDHandle h = (DustHandle) valPath[i++];
//					Object key = (i <= last) ? valPath[i] : null;
//
//					CollType ct = getCollType(h, MiNDAccessCommand.Peek, key);
//					if ( (null != ct) && ct.hasKey ) {
//						++i;
//					}
//
//					if ( null == e ) {
//						break;
//					} else {
//						ret = (i < last) ? e.access(MiNDAccessCommand.Peek, null, h, ct, key)
//								: e.access(cmd, val, h, getCollType(h, cmd, key), key);
//					}
//				}
//			}
//
//			if ( null == ret ) {
//				switch ( cmd ) {
//				case Check:
//				case Delete:
//					ret = false;
//					break;
//				case Get:
//					if ( i == last ) {
//						// also, put?
//					} else {
//						ret = val;
//					}
//					break;
//				case Insert:
//					ret = null;
//					break;
//				case Peek:
//					ret = val;
//					break;
//				case Set:
//					ret = null;
//					break;
//				}
//			}
//
//			return (RetType) ret;
//		}
//	}
}
