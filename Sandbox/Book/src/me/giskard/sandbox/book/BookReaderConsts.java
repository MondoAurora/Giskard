package me.giskard.sandbox.book;

public interface BookReaderConsts {
	enum ReadStep {
		OrigFiles, PdfScan, PageTexts, MergePageText, Translate
	}

	enum BookData {
		VolumeFile, VolumeText, VolumePages, VolumeStatements,
		PageImage, PageText
	}

	public static final String WDIR = "work";
	public static final String EXT_PDF = ".pdf";
	public static final String EXT_TXT = ".txt";
}
