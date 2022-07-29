package me.giskard.sandbox.book;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import me.giskard.Giskard;
import me.giskard.GiskardUtils;

public class BookReader implements BookReaderConsts {

	String bookName;
	File dirOutRoot;

	ArrayList<File> docFiles;

	public BookReader(String srcPath) throws Exception {
		File src = new File(srcPath);
		docFiles = new ArrayList<>();
		bookName = src.getName();

		if ( src.isFile() ) {
			docFiles.add(src);
			bookName = GiskardUtils.optCutEnd(bookName, EXT_PDF);
		} else {
			for (File f : src.listFiles()) {
				String fn = f.getName();
				if ( GiskardUtils.endsWithNoCase(fn, EXT_PDF) ) {
					docFiles.add(f);
				}
			}
		}

		docFiles.sort(null);

		dirOutRoot = new File(WDIR + "/" + bookName);
		File dir = new File(dirOutRoot, ReadStep.OrigFiles.name());

		if ( !dir.exists() ) {
			dir.mkdirs();

			if ( src.isFile() ) {
				Files.copy(src.toPath(), new File(dir, src.getName()).toPath());
			} else {
				for (File fn : docFiles) {
					Files.copy(fn.toPath(), new File(dir, fn.getName()).toPath());
				}
			}
		}
	}

	public String getBookName() {
		return bookName;
	}

	public Collection<String> getVolumeNames(Collection<String> coll) {
		if ( null == coll ) {
			coll = new ArrayList<>(docFiles.size());
		} else {
			coll.clear();
		}
		for (File fn : docFiles) {
			coll.add(GiskardUtils.optCutEnd(fn.getName(), EXT_PDF));
		}

		return coll;
	}

	public File getFile(String ext, Object... path) {
		StringBuilder sb = GiskardUtils.sbAppend(null, "/", false, path);

		sb = GiskardUtils.sbAppend(sb, "", false, ext);

		return new File(dirOutRoot, sb.toString());
	}

	private File getImageDir(String volumeId) {
		return new File(dirOutRoot, ReadStep.PdfScan.name() + "/" + volumeId);
	}

	public int getPageCount(String volumeId) {
		File d = getImageDir(volumeId);
		return d.isDirectory() ? d.list().length : 0;
	}

	public File getPageImage(String volumeId, String pageId) {
		File f = null;

		File d = getImageDir(volumeId);
		if ( d.isDirectory() ) {
			f = new File(d, pageId + EXT_PNG);
			if ( !f.exists() ) {
				f = null;
			}
		}

		return f;
	}

	public File getDir(ReadStep rs, String fName, boolean update) throws Exception {
		File d = new File(dirOutRoot, rs.name());

		if ( null != fName ) {
			String name = fName;
			int dot = fName.lastIndexOf(".");
			if ( -1 != dot ) {
				name = name.substring(0, dot + 1);
			}
			d = new File(d, name);
		}

		if ( !d.exists() ) {
			d.getParentFile().mkdirs();
			update = 0 < rs.ordinal();
		}

		if ( update && (null != fName) ) {
			process(rs, fName);
		}

		return d;
	}

	void process(ReadStep rs) throws Exception {
		for (File fName : docFiles) {
			getDir(rs, fName.getName(), false);
		}
	}

	void process(ReadStep rs, String fName) throws Exception {
		File source;
		File target;

		switch ( rs ) {
		case OrigFiles:
			break;
		case PdfScan:
			source = getDir(ReadStep.OrigFiles, null, false);
			target = getDir(rs, fName, false);
			pdfScan(new File(source, fName), target);
			break;
		case PageTexts:
			source = getDir(ReadStep.PdfScan, fName, false);
			target = getDir(rs, fName, false);
			ocrDir(source, target);
			break;
		case MergePageText:
			source = getDir(ReadStep.PageTexts, fName, false);
			target = getDir(rs, null, false);
			mergeTexts(source, new File(target, fName));
			break;
		case PageContents:
//			source = getDir(ReadStep.MergePageText, fName, false);
//			target = getDir(rs, null, false);
//			optCopyContent(source, target, fName);
			break;
		case Translate:
			break;
		}
	}

	public File getMergedContent(String volumeId) throws Exception {
		File src = getFile(null, ReadStep.PageTexts, volumeId);
		File ret = getFile(EXT_HTML, ReadStep.PageContents, volumeId);

		mergeTexts(src, GiskardUtils.optBackup(ret));

		return ret;
	}

	private void mergeTexts(File sourceDir, File targetFile) throws Exception {
//		PrintStream out = new PrintStream(new FileOutputStream(new File(target, fName.replace(EXT_PDF, EXT_TXT))));
		PrintStream out = new PrintStream(new FileOutputStream(targetFile));

		String[] fNames = sourceDir.list();
		Arrays.sort(fNames);

		Pattern ptSingleNum = Pattern.compile("\\d+");
		Pattern ptHeader = Pattern.compile("([A-Z ]*)\\s*(\\(.*\\))?");
		Pattern ptHyphen = Pattern.compile(".*?([a-zA-Z]+)-$");
		Pattern ptHyphenNext = Pattern.compile("([a-zA-Z]+).*");

		Set<String> skipSegments = new HashSet<>();
		skipSegments.add("INDEX");
		skipSegments.add("SYNOPSIS");

		Set<String> mergeExceptions = new HashSet<>();
		mergeExceptions.add("TAEKWON-DO");
		mergeExceptions.add("TACKWON-DO");
		mergeExceptions.add("AIKI-DO");
		mergeExceptions.add("KICK-BOXING");

		mergeExceptions.add("SELF-CONTROL");
		mergeExceptions.add("SELF-CONFIDENCE");
		mergeExceptions.add("SELF-PROTECTION");
		mergeExceptions.add("IN-CHIEF");
		mergeExceptions.add("KOREA-JAPAN");

		mergeExceptions.add("EVER-INCREASING");
		mergeExceptions.add("COUNTER-CLOCKWISE");

		mergeExceptions.add("E-SOOK");
		mergeExceptions.add("MONG-YONG");
		mergeExceptions.add("KAI-SHEK");

		mergeExceptions.add("KNIFE-HAND");
		mergeExceptions.add("KNIFE-HANDS");
		mergeExceptions.add("L-STANCE");
		mergeExceptions.add("X-STANCE");
		mergeExceptions.add("L-STANCES");
		mergeExceptions.add("X-STANCES");
		mergeExceptions.add("U-SHAPE");
		mergeExceptions.add("W-SHAPE");

		mergeExceptions.add("MAK-GI");

		boolean skipping = false;
		StringBuilder sbPara = new StringBuilder();

		out.append("<!DOCTYPE html>\n<html lang=\"en\">\n<body>\n");
//		out.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head><style> ." + PREF_PAGE + " { visibility: hidden; } </style></head><body>\n");

		// h2.b { visibility: hidden; }
		int idxPara = 0;
		String paraHead = null;

		for (String fn : fNames) {
			List<String> ll = Files.readAllLines(Paths.get(sourceDir.getAbsolutePath(), fn));

			Matcher mp = PT_PAGEID.matcher(fn);

			String pageId = mp.matches() ? mp.group(1) : "???";

			out.append("\n<span hidden class=\"" + PREF_PAGE + "\" id=\"" + PREF_PAGE + "-" + pageId + "\"><br/>Page "
					+ Integer.valueOf(pageId) + "<br/> </span>\n");
			paraHead = null;

			boolean lastIsEmpty = false;
			boolean readHdr = true;
			boolean collector = true;
			idxPara = 0;

			for (String l : ll) {
				l = l.trim();

				if ( null == paraHead ) {
					paraHead = String.format("\n<p class=\"" + PREF_PARA + "\" id=\"" + PREF_PARA + "-%s-%04d\">", pageId,
							++idxPara);
				}

				if ( GiskardUtils.isEmpty(l) || ptSingleNum.matcher(l).matches() ) {
					if ( lastIsEmpty ) {
						continue;
					} else {
						lastIsEmpty = true;
					}
				} else {
					lastIsEmpty = false;
				}

				l = l.replace('|', 'I');

				if ( lastIsEmpty ) {
					if ( 0 < sbPara.length() ) {
						String strPara = sbPara.toString().trim();
						sbPara.setLength(0);

						if ( readHdr ) {
							readHdr = false;

							Matcher m = ptHeader.matcher(strPara);

							if ( m.matches() ) {
								skipping = false;

								String hdr = m.group(1).trim();

								if ( "TABLE OF CONTENTS".equals(hdr) ) {
									collector = false;
								} else if ( skipSegments.contains(hdr) ) {
									skipping = true;
									break;
								}
							} else if ( skipping ) {
								break;
							}
						}

						out.append(paraHead).append(strPara).append("</p>\n");
						paraHead = null;
					}
				} else {
					if ( collector ) {
						if ( 0 < sbPara.length() ) {
							Matcher m = ptHyphen.matcher(sbPara);

							if ( m.matches() ) {
								String lastWord = m.group(1).toUpperCase();

								Matcher n = ptHyphenNext.matcher(l);
								String nextWord = (n.matches() ? n.group(1) : l).toUpperCase();

								String chk = lastWord + "-" + nextWord;

								if ( !mergeExceptions.contains(chk) ) {
									System.out.println(chk);
									sbPara.setLength(sbPara.length() - 1);
								}
							} else {
								sbPara.append(" ");
							}
						}
						sbPara.append(l);
					} else {
						out.append(paraHead).append(l).append("</p>\n");
						paraHead = null;
					}
				}
			}

			out.flush();
		}

		out.append(paraHead).append(sbPara).append("</p>\n");
		out.append("</body>\n</html>");

		out.close();
	}

	void pdfScan(File fPdf, File dirTarget) throws Exception {
		PDDocument document = PDDocument.load(fPdf);

		PDPageTree pt = document.getPages();

		for (int i = 0; i < pt.getCount(); ++i) {
			PDPage page = pt.get(i);
			PDResources resources = page.getResources();

			for (COSName xObjectName : resources.getXObjectNames()) {
				PDXObject xObject = resources.getXObject(xObjectName);

				if ( xObject instanceof PDImageXObject ) {
					BufferedImage img = ((PDImageXObject) xObject).getImage();
					String pn = BookReaderUtils.getPageId(i) + EXT_PNG;

					File file = new File(dirTarget, pn);
					ImageIO.write(img, "png", file);
				}
			}
		}
	}

	public static String ocrFileDirect(File fImage) throws Exception {
		File fTarget = new File("ocr");
		ocrFile(fImage, fTarget).waitFor();
		
		StringBuilder sb = null;		
		for ( String l : Files.readAllLines(new File("ocr.txt").toPath()) ) {
			sb = GiskardUtils.sbAppend(sb, " ", false, l);
		}

		return GiskardUtils.toString(sb);
	}

	public static Process ocrFile(File fImage, File fTarget) throws Exception {
		String[] cmd = new String[] { "/usr/local/bin/tesseract", fImage.getAbsolutePath(), fTarget.getAbsolutePath() };
		return Runtime.getRuntime().exec(cmd);
	}

	public static void ocrDir(File dirImages, File dirTarget) throws Exception {
//		String[] cmd = new String[] { "/usr/local/bin/tesseract", "", "" };

		for (File f : dirImages.listFiles()) {
			ocrFile(f, new File(dirTarget, f.getName()));
//			cmd[1] = f.getAbsolutePath();
//			cmd[2] = new File(dirTarget, f.getName()).getAbsolutePath();
//
//			Runtime.getRuntime().exec(cmd);
		}
	}

	public static void main(String[] args) throws Exception {
		Giskard.main(args);
		
		BookReader rdr = new BookReader(args[0]);

//		RecRename rr =  new RecRename("Page_(\\d*)(.*)");
//		rr.rename(rdr.dirOutRoot);
//		System.out.println(rr);

//		rdr.process(ReadStep.valueOf(args[1]));

		BookReaderSwing gui = new BookReaderSwing(rdr);
		gui.displayFrame();
	}

	public void update(ReadStep step, String volumeId, Object content) {
		try {
			File ret = GiskardUtils.optBackup(getFile(EXT_HTML, step, volumeId));

			if ( content instanceof String ) {
				Files.write(ret.toPath(), ((String) content).getBytes());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public File getChildImage(String volumeId, String pageId, String imgName) {
		File dirImg = getFile(null, ReadStep.PageContents, volumeId);

		if ( !dirImg.isDirectory() ) {
			dirImg.mkdirs();
		}

		if ( null == imgName ) {
			imgName = PREF_IMG + "-" + pageId;
			Pattern ptImgName = Pattern.compile(imgName + "-(\\d+).*");

			int imgIdx = 0;
			for (String fn : dirImg.list()) {
				if ( fn.startsWith(imgName) ) {
					Matcher m = ptImgName.matcher(fn);
					if ( m.matches() ) {
						int idx = Integer.parseInt(m.group(1));
						if ( idx > imgIdx ) {
							imgIdx = idx;
						}
					}
				}
			}
			imgName += String.format("-%04d.png", imgIdx + 1);
		}

		return new File(dirImg, imgName);
	}

}
