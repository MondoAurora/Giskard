package me.giskard.sandbox.book;

public class BookReaderUtils implements BookReaderConsts {
	public static String getPageId(int i) {
		return String.format(FMT_PAGE, i + 1);
	}
}
