package me.giskard.dust.io.pdf.apache;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import me.giskard.GiskardConsts.MiNDAgent;
import me.giskard.dust.io.DustIOConsts;
import me.giskard.dust.io.DustIOUtils;

public class DustIOPdfApacheReader implements MiNDAgent, DustIOConsts {
	@Override
	public MiNDResultType mindAgentProcess() throws Exception {
		readPdf();
		return MiNDResultType.Accept;
	}

	void readPdf() throws Exception {
		String fPdf = "Knowledge/Pdf/tkd/Taekwon-Do Encyclopedia 01.pdf";

		PDDocument document = PDDocument.load(new File(DustIOUtils.extendName(fPdf)));

		processDoc(document);

	}

	void processDoc(PDDocument document) throws Exception {
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		stripper.setSortByPosition(true);

		PDFTextStripper tStripper = new PDFTextStripper();
		tStripper.setStartPage(1);

		int numberOfPages = document.getNumberOfPages();
		tStripper.setEndPage(numberOfPages);

		String pdfFileInText = tStripper.getText(document);
		// System.out.println("Text:" + st);

		// split by whitespace
		String lines[] = pdfFileInText.split("\\r?\\n");
		for (String line : lines) {
			System.out.println(line);
		}

		List<RenderedImage> images = new ArrayList<>();
		PDPageTree pt = document.getPages();

		PrintStream out = null;

		for (int i = 0; i < pt.getCount(); ++i) {
			PDPage page = pt.get(i);
			PDResources resources = page.getResources();

			for (COSName xObjectName : resources.getXObjectNames()) {
				PDXObject xObject = resources.getXObject(xObjectName);

				if ( xObject instanceof PDImageXObject ) {
					BufferedImage img = ((PDImageXObject) xObject).getImage();
					images.add(img);

					String fName = DustIOUtils.extendName("Knowledge/Out/Pdf_page_" + i + ".png");
					File file = new File(fName);
					ImageIO.write(img, "png", file);

					String fTxt = DustIOUtils.extendName("Knowledge/Out/tess_" + i);

					@SuppressWarnings("unused")
					Process p = Runtime.getRuntime().exec(new String[] { "/usr/local/bin/tesseract", fName, fTxt });

					Path pTxt = Paths.get(fTxt + ".txt");

					if ( Files.exists(pTxt) ) {
						List<String> ll = Files.readAllLines(pTxt);

						if ( null == out ) {
							out = new PrintStream(new FileOutputStream(DustIOUtils.extendName("Knowledge/Out/all.txt")));
						} else {
							out.append("\n\n  -----------  \n\n");
						}

						for (String l : ll) {
							out.append(l).append("\n");
						}
						out.flush();
					}
				}
			}
		}

		out.close();

		images.clear();
	}

//	@Override
//	public String toString() {
//		StringBuilder sb = null;
//
//		for (Map.Entry<?, ?> re : rules.entrySet()) {
//			sb = GiskardUtils.sbAppend(sb, "", false, re.getKey(), " = ", re.getValue(), "\n");
//		}
//
//		sb = GiskardUtils.sbAppend(sb, "", false, "Terminals: ", terminals, "\n");
//
//		return sb.toString();
//	}
}
