package me.giskard.sandbox.book;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BookReaderUtils implements BookReaderConsts {
	public static String getPageId(int i) {
		return String.format(FMT_PAGE, i + 1);
	}
	
	public static String toHtmlRef(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
	}
}
