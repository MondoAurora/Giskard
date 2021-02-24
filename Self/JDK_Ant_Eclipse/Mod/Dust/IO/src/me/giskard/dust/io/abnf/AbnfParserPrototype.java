package me.giskard.dust.io.abnf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import me.giskard.Mind;
import me.giskard.MindConsts.MiNDEventLevel;
import me.giskard.utils.MindUtils;

public class AbnfParserPrototype {

	private static final String[] HEADERS = { "id:", "uses:" };
	private static boolean HIDE_COMMENTS = false;

/*
	abstract class ParserNode {
		protected final RuleDef rd;

		public ParserNode(RuleDef def) {
			this.rd = def;
		}
		
		void reset() {
			
		}
		
		abstract MiNDResultType processChar(Character chr);
	}

	abstract class ParserNodeParent<ItemType> extends ParserNode {
		protected int idx;
		protected ItemType[] members;
		
		public ParserNodeParent(RuleDef def) {
			super(def);
		}
		
		void reset() {
			idx = 0;
		}
		
		abstract MiNDResultType processChar(Character chr);
	}

	enum RuleMode {
		Alternate, Concatenate
	}

	class Nonterminal extends ParserNodeParent<ParserNode> {
		int repMin = -1;
		int repMax = -1;
		
		public Nonterminal(RuleDef def) {
			super(def);
		}

		@Override
		public MiNDResultType processChar(Character chr) {
			return MiNDResultType.REJECT;
		}
	}
	
	class TerminalChar extends ParserNode {
		Character chr = null;

		public TerminalChar(RuleDef def) {
			super(def);
			chr = rd.def.charAt(1);
		}

		@Override
		public MiNDResultType processChar(Character chr) {
			return this.chr.equals(chr) ? MiNDResultType.ACCEPT : MiNDResultType.REJECT;
		}
	}

	enum NumMode {
		Range, String
	}

	class TerminalNum extends ParserNodeParent<Character> {
		NumMode mode;

		public TerminalNum(RuleDef def) {
			super(def);
			int radix = 0;

			switch ( rd.def.charAt(1) ) {
			case 'b':
				radix = 2;
				break;
			case 'd':
				radix = 10;
				break;
			case 'x':
				radix = 16;
				break;
			}
			
			String rest = rd.def.substring(2);
			String[] parts = rest.split("-");
			int pl = parts.length;

			switch ( parts.length ) {
			case 1:
				parts = rest.split(".");
				pl = parts.length;
				mode = NumMode.String;
				break;
			case 2:
				mode = NumMode.Range;
				break;
			}

			members = new Character[pl];
			
			for ( int i = 0; i < pl; ++i ) {
				members[i] = (char) Integer.parseInt(parts[i], radix);
			}
		}

		@Override
		public MiNDResultType processChar(Character chr) {
			switch ( mode ) {
			case Range:
				return ((this.members[0] <= chr) && (chr <= this.members[1])) ? MiNDResultType.ACCEPT : MiNDResultType.REJECT;
			case String:
				if ( members[idx] != chr ) {
					return MiNDResultType.REJECT;
				}
				return (++idx < members.length ) ? MiNDResultType.ACCEPT_READ : MiNDResultType.ACCEPT;
			}
			
			return MiNDResultType.REJECT;
		}
	}
*/
	class RuleDef {
		String def;
		String comment;

		public RuleDef(String def, Object comment) {
			this.def = def;
			this.comment = MindUtils.toString(comment);
		}

		@Override
		public String toString() {
			return (HIDE_COMMENTS || MindUtils.isEmpty(comment)) ? def : def + "\n            ; " + comment;
		}
	}

	Map<String, RuleDef> rules = new TreeMap<>();
	ArrayList<String> terminals = new ArrayList<>();

	RuleDef start;
	File dir;
	String strExt;

	public AbnfParserPrototype(String fileName) {
		Set<String> toSee = new TreeSet<>();
		Set<String> seen = new TreeSet<>();

		File f = new File(fileName);
		dir = f.getParentFile();

		String fn = f.getName();
		int ext = fn.lastIndexOf(".");
		if ( -1 != ext ) {
			strExt = fn.substring(ext);
			fn = fn.substring(0, ext);
		}

		for (toSee.add(fn); readFile(toSee, seen);)
			;

	}

	private boolean readFile(Set<String> toSee, Set<String> seen) {
		if ( toSee.isEmpty() ) {
			return false;
		}

		String langName = toSee.iterator().next();
		File fAbnf = new File(dir, langName + strExt);
		seen.add(langName);
		toSee.remove(langName);

		Mind.log(MiNDEventLevel.TRACE, "Reading ABNF file", fAbnf.getAbsolutePath());

		boolean header = true;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fAbnf));
			StringBuilder rDef = null;
			StringBuilder rComment = null;
			String rName = null;

			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				if ( !MindUtils.isEmpty(line.trim()) ) {
					System.out.println(line);

					int cStart = line.indexOf(";");

					if ( header ) {
						if ( 0 == cStart ) {
							for (int hidx = HEADERS.length; hidx-- > 0;) {
								String hdr = HEADERS[hidx];
								int hOff = line.indexOf(hdr);
								if ( -1 != hOff ) {
									String str = line.substring(hOff + hdr.length()).trim();

									switch ( hidx ) {
									case 0:
										langName = str;
										break;
									case 1:
										for (String u : str.split(",")) {
											String un = u.trim();
											if ( !MindUtils.isEmpty(un) && !seen.contains(un) ) {
												toSee.add(un);
											}
										}
										break;
									}
								}
							}
						} else {
							header = false;
						}
					}

					if ( !header ) {
						boolean wsstart = Character.isWhitespace(line.charAt(0));
						String comment = null;
						if ( -1 != cStart ) {
							comment = line.substring(cStart + 1).trim();
							line = line.substring(0, cStart).trim();
						}

						int ruleSep = line.indexOf("=");

						if ( -1 != ruleSep ) {
							if ( null != rName ) {
								addRule(rName, rDef, rComment);
							}
							rName = line.substring(0, ruleSep).trim();
							rDef = new StringBuilder(line.substring(ruleSep + 1).trim());
							rComment = null;
						} else {
							if ( wsstart && (null != rName) && !MindUtils.isEmpty(line) ) {
								rDef.append(" ").append(line);
							}
						}

						if ( (null != rName) && !MindUtils.isEmpty(comment) ) {
							rComment = MindUtils.sbAppend(rComment, " ", false, comment);
						}
					}
				}
			}

			if ( null != rName ) {
				addRule(rName, rDef, rComment);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return !toSee.isEmpty();
	}

	public void addRule(String rName, StringBuilder rDef, StringBuilder rComment) {
		RuleDef rd = new RuleDef(rDef.toString(), rComment);
		String d = rd.def;

		rules.put(rName, rd);

		boolean terminal = false;

		if ( (d.length() == 3) && ('\"' == d.charAt(0)) && '\"' == d.charAt(2) ) {
			terminal = true;
		} else if ( 1 == rDef.toString().split(" ").length ) {
			if ( (-1 == d.indexOf('-')) && (-1 == d.indexOf('/')) ) {
				terminal = true;
			}
		}

		if ( terminal ) {
			terminals.add(rName);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = null;

		for (Map.Entry<?, ?> re : rules.entrySet()) {
			sb = MindUtils.sbAppend(sb, "", false, re.getKey(), " = ", re.getValue(), "\n");
		}

		sb = MindUtils.sbAppend(sb, "", false, "Terminals: ", terminals, "\n");

		return sb.toString();
	}
}
