package me.giskard.sandbox.book;

import java.util.regex.Pattern;

public interface BookReaderConsts {
	public interface ImageEventProcessor {
		void textScanned(String txt);
	}

	enum ReadStep {
		OrigFiles, PdfScan, PageTexts, MergePageText, PageContents, Translate
	}

	enum TextTag {
		h1, h2, h3, h4, h5, ol("li"), ul("li"), table("tr", "td");
		
		public final String[] repl;
		
		private TextTag(String... repl) {
			this.repl = repl;
		}
		
		public final String apply(String str) {
			String begin;
			String end; 
			
			String ret = str;
			
			if (repl.length == 0) {
				begin = end = name();
			} else {
				StringBuilder b = null;
				StringBuilder e = null;

				int l = repl.length - 1;
				
				for (int i = 0; i <= l; ++i ) {
					if ( null == b ) {
						b = new StringBuilder();
					} else {
						b.append("><");
					}
					b.append(repl[i]);
					
					if ( null == e ) {
						e = new StringBuilder();
					} else {
						e.append("></");
					}
					e.append(repl[l - i]);
				}
				
				begin = b.toString();
				end = e.toString();
				
				ret = "<" + name() + ">\n" + ret + "\n</" + name() + ">\n";
			}
			
			ret = ret.replace("<p", "<" + begin);
			ret = ret.replace("</p>", "</" + end + ">");
			
			return ret;
		}
	}

	public static final String WDIR = "work";
	public static final String EXT_PDF = ".pdf";
	public static final String EXT_PNG = ".png";
	public static final String EXT_TXT = ".txt";
	public static final String EXT_HTML = ".html";
	
	public static final String PREF_PAGE = "montru-layout-page";
	public static final String PREF_PARA = "montru-text-statement";
	public static final String PREF_IMG = "montru-bin-img";
	public static final String FMT_AREA = "data-montru-graph-area=\"({0},{1},{2},{3})\"";
	
	String FMT_PAGE = "Page_%04d";
	Pattern PT_PAGEID = Pattern.compile("Page_(\\d+).*");
}
