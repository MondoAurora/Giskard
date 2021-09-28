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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

import me.giskard.sandbox.utils.GSBUtils;

public class BookReader implements BookReaderConsts {

	String bookName;
	File dirOutRoot;

	ArrayList<String> fileNames;

	public BookReader(String srcPath) throws Exception {
		File src = new File(srcPath);
		fileNames = new ArrayList<>();
		bookName = src.getName();

		if ( src.isFile() ) {
			fileNames.add(src.getName());
			bookName = bookName.substring(0, bookName.length() - EXT_PDF.length());
		} else {
			for (File f : src.listFiles()) {
				String fn = f.getName();
				if ( isPdf(fn) ) {
					fileNames.add(fn);
				}
			}
		}

		fileNames.sort(null);

		dirOutRoot = new File(WDIR + "/" + bookName);
		File dir = new File(dirOutRoot, ReadStep.OrigFiles.name());

		if ( !dir.exists() ) {
			dir.mkdirs();

			if ( src.isFile() ) {
				Files.copy(src.toPath(), new File(dir, src.getName()).toPath());
			} else {
				for (String fn : fileNames) {
					Files.copy(new File(src, fn).toPath(), new File(dir, fn).toPath());
				}
			}
		}
	}

	public String getBookName() {
		return bookName;
	}

	public Collection<String> getFileNames(Collection<String> target) {
		if ( null == target ) {
			target = new ArrayList<>(fileNames);
		} else {
			target.clear();
			target.addAll(fileNames);
		}
		return target;
	}

	boolean isPdf(String fName) {
		return fName.toLowerCase().endsWith(EXT_PDF);
	}

	public File getDir(ReadStep rs, String fName, boolean update) throws Exception {
		File d = new File(dirOutRoot, rs.name());

		if ( null != fName ) {
			String name = fName;
			if ( isPdf(fName) ) {
				name = name.substring(0, name.length() - EXT_PDF.length());
			}
			d = new File(d, name);
		}

		if ( !d.exists() ) {
			d.mkdirs();
			update = 0 < rs.ordinal();
		}

		if ( update && (null != fName) ) {
			process(rs, fName);
		}

		return d;
	}

	void process(ReadStep rs) throws Exception {
		for (String fName : fileNames) {
			getDir(rs, fName, false);
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
			mergeTexts(source, target, fName);
			break;
		case Translate:
			break;
		}
	}

	private void mergeTexts(File source, File target, String fName) throws Exception {
		PrintStream out = new PrintStream(new FileOutputStream(new File(target, fName.replace(EXT_PDF, EXT_TXT))));

		String[] fNames = source.list();
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

		for (String fn : fNames) {
			List<String> ll = Files.readAllLines(Paths.get(source.getAbsolutePath(), fn));

			out.append("<< PAGE " + fn + " >>\n");

			boolean lastIsEmpty = false;
			boolean readHdr = true;
			boolean collector = true;

			for (String l : ll) {
				l = l.trim();

				if ( GSBUtils.isEmpty(l) || ptSingleNum.matcher(l).matches() ) {
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

						out.append(strPara).append("\n\n");
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
						out.append(l).append("\n");
					}
				}
			}

			out.flush();
		}

		out.append(sbPara).append("\n");

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
					String pn = String.format("Page_%04d.png", i);

					File file = new File(dirTarget, pn);
					ImageIO.write(img, "png", file);
				}
			}
		}
	}

	void ocrDir(File dirImages, File dirTarget) throws Exception {
		String[] cmd = new String[] { "/usr/local/bin/tesseract", "", "" };

		for (File f : dirImages.listFiles()) {
			cmd[1] = f.getAbsolutePath();
			cmd[2] = new File(dirTarget, f.getName()).getAbsolutePath();

			Runtime.getRuntime().exec(cmd);
		}
	}

	public static void main(String[] args) throws Exception {
		BookReader rdr = new BookReader(args[0]);
		
//		RecRename rr =  new RecRename("Page_(\\d*)(.*)");
//		rr.rename(rdr.dirOutRoot);
//		System.out.println(rr);

		rdr.process(ReadStep.valueOf(args[1]));

		BookReaderSwing gui = new BookReaderSwing(rdr);
		gui.displayFrame();
	}

	@SuppressWarnings("unchecked")
	public void getVolumeInfo(int idx, Map<BookData, Object> volumeData) throws Exception {
		String f = fileNames.get(idx);

		File dir = getDir(ReadStep.MergePageText, null, false);
		File src = new File(dir, f.replace(EXT_PDF, EXT_TXT));

		volumeData.put(BookData.VolumeText, src);

		dir = getDir(ReadStep.OrigFiles, null, false);
		src = new File(dir, f);

		volumeData.put(BookData.VolumeFile, src);

		ArrayList<Map<BookData, Object>> pages = (ArrayList<Map<BookData, Object>>) volumeData.get(BookData.VolumePages);
		pages.clear();

		File dirImgs = getDir(ReadStep.PdfScan, f, false);
		String[] imgs = dirImgs.list();
		File dirTxts = getDir(ReadStep.PageTexts, f, false);
		String[] txts = dirTxts.list();

		Arrays.sort(imgs);
		Arrays.sort(txts);

		for (int i = 0; i < imgs.length; ++i) {
			Map<BookData, Object> pd = new HashMap<>();

			pd.put(BookData.PageImage, new File(dirImgs, imgs[i]));
			pd.put(BookData.PageText, new File(dirTxts, txts[i]));

			pages.add(pd);
		}
	}

}
