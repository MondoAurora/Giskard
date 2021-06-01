package me.giskard.dust.devjava;

import java.io.File;
import java.io.FileWriter;
import java.text.MessageFormat;

import me.giskard.Giskard;
import me.giskard.GiskardConsts;
import me.giskard.GiskardUtils;
import me.giskard.coll.GisCollConsts.MiNDCreator;
import me.giskard.coll.GisCollFactory;
import me.giskard.tokens.DustTokensTemp;
import me.giskard.tools.GisToolsTokenTranslator;

public class DustDevJavaAgentGenTokens implements DustDevJavaConsts, DustTokensTemp, GiskardConsts.MiNDAgent {

	static String GEN_PACKAGE = "me.giskard.gen.tokens";

	static String PATTERN_SRC = "package " + GEN_PACKAGE + ";\n\n" + "import me.giskard.Giskard;\n"
			+ "import me.giskard.GiskardConsts;\n\n" + "public interface DustTokens%s extends GiskardConsts { %s \n}\n";

	static String PATTERN_KEY = "MT{0}_{1}";
	static String PATTERN_TOKEN = "\n\tMiNDToken {0} = Giskard.defineToken(MiNDTokenType.{1});";

	class SourceWriter {
		class TokenInfo {
			final Object entity;
			final Object unit;

			final String name;

			MiNDTokenType tt;
			TokenInfo pti;
			String key;
			String line;

			public TokenInfo(Object entity) {
				this.entity = entity;
				this.unit = GiskardUtils.getIter(entity, MTMEMBER_CONN_OWNER);
				name = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_PLAIN_STRING);
			}

			public void setTokenType(MiNDTokenType tt) {
				this.tt = tt;
				String keyName = name;

				StringBuilder param = GiskardUtils.sbAppend(null, ", ", true, tt, "\"" + name + "\"");

				Object parent = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_CONN_OWNER);

				if ( null != parent ) {
					pti = factTokens.get(parent);

					if ( null != pti ) {
						GiskardUtils.sbAppend(param, ", ", true, pti.key);

						switch ( pti.tt ) {
						case Tag:
						case Type:
							keyName = pti.name + "_" + keyName;
							break;
						default:
							break;
						}
					}
				}

				if ( tt == MiNDTokenType.Member ) {
					Object vt = Giskard.access(MiNDAccessCommand.Get, MTTAG_VALTYPE_RAW, entity, MTMEMBER_ENTITY_TAGS);
					Object ct = Giskard.access(MiNDAccessCommand.Get, MTTAG_COLLTYPE_ONE, entity, MTMEMBER_ENTITY_TAGS);

					Object mvt = (MiNDValType) GisToolsTokenTranslator.toEnum((MiNDToken) vt);
					Object mct = (MiNDCollType) GisToolsTokenTranslator.toEnum((MiNDToken) ct);

					GiskardUtils.sbAppend(param, ", ", true, "MiNDValType." + mvt, "MiNDCollType." + mct);
				}

				key = MessageFormat.format(PATTERN_KEY, tt, keyName).toUpperCase();

				line = MessageFormat.format(PATTERN_TOKEN, key, param);
			}
		}

		class SourceUnit {
			TokenInfo ti;
			StringBuilder content;

			public SourceUnit(Object unit) {
				this.ti = factTokens.get(unit);

				String name = Giskard.access(MiNDAccessCommand.Get, null, unit, MTMEMBER_PLAIN_STRING);
				content = new StringBuilder(String.format(PATTERN_SRC, name, ti.line));
			}

			void add(TokenInfo ti) {
				if ( ti != this.ti ) {
					int lastRow = content.lastIndexOf(ti.pti.key);
					lastRow = content.indexOf("\n", lastRow);
					content.insert(lastRow, ti.line);
				}
			}

			public void save(File dir) throws Exception {
				File f = new File(dir, "DustTokens" + ti.name + ".java");
				
				FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            fw.write(content.toString());
        } finally {
          if ( fw != null ) {
            fw.close();
          }
        }
			}

			@Override
			public String toString() {
				return "\n====== DustTokens" + ti.name + ".java ======\n" + content + "\n";
			}
		}

		Object hRoot = MTMEMBER_ACTION_DIALOG;

		SourceUnit currUnit;

		GisCollFactory<Object, SourceUnit> factUnits = new GisCollFactory<Object, SourceUnit>(true,
				new MiNDCreator<Object, SourceUnit>() {
					@Override
					public SourceUnit create(Object key) {
						return new SourceUnit(key);
					}
				});

		GisCollFactory<Object, TokenInfo> factTokens = new GisCollFactory<Object, TokenInfo>(true,
				new MiNDCreator<Object, TokenInfo>() {
					@Override
					public TokenInfo create(Object key) {
						TokenInfo ret = null;

						MiNDToken pt = Giskard.access(MiNDAccessCommand.Get, null, key, MTMEMBER_ENTITY_PRIMARYTYPE);
						Object o = GisToolsTokenTranslator.toEnum(pt);

						if ( o instanceof MiNDTokenType ) {
							ret = new TokenInfo(key);
							ret.setTokenType((MiNDTokenType) o);
							if ( ret.tt != MiNDTokenType.Unit ) {
								factUnits.get(ret.unit).add(ret);
							}
						}

						return ret;
					}
				});

		public void step() throws Exception {
			Object entity = Giskard.access(MiNDAccessCommand.Get, null, hRoot, MTMEMBER_LINK_ONE);

			if ( null == entity ) {
				save();
			} else {
				MiNDToken pt = Giskard.access(MiNDAccessCommand.Get, null, entity, MTMEMBER_ENTITY_PRIMARYTYPE);
				Object o = GisToolsTokenTranslator.toEnum(pt);

				if ( o instanceof MiNDTokenType ) {
					factTokens.get(entity);
				}
			}
		}

		private void save() throws Exception {
			String root = GEN_PACKAGE.replace('.', '/');
			root = "Self/JDK_Ant_Eclipse/Inc/Gen/dev/" + root;
			
			File rootDir = new File(GiskardUtils.getRoot(), root);
			
			if ( !rootDir.exists() ) {
				rootDir.mkdirs();
			}
			
			for (Object unit : factUnits.keys()) {
				SourceUnit u = factUnits.peek(unit);
				u.save(rootDir);
				Giskard.log(MiNDEventLevel.Info, u);
			}
		}
	}

	SourceWriter srcWriter;

	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		Object sw = Giskard.access(MiNDAccessCommand.Get, null, MTMEMBER_ACTION_DIALOG, MTMEMBER_TEMP_SERIALIZER);

		if ( null == sw ) {
			srcWriter = new SourceWriter();
			Giskard.access(MiNDAccessCommand.Set, srcWriter, MTMEMBER_ACTION_DIALOG, MTMEMBER_TEMP_SERIALIZER);
			Giskard.access(MiNDAccessCommand.Set, MTTAG_SERIALIZEMODE_LOAD, MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
		} else {
			srcWriter = (SourceWriter) sw;
			Giskard.access(MiNDAccessCommand.Set, MTTAG_SERIALIZEMODE_SAVE, MTMEMBER_ACTION_DIALOG, MTMEMBER_ENTITY_TAGS);
		}

		srcWriter.step();

		return MiNDResultType.Accept;
	}

}
