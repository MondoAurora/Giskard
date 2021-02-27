package me.giskard.mod;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFrame;

import me.giskard.dust.io.DustIOConsts;
import me.giskard.dust.io.DustIOUtils;

// https://docs.oracle.com/javase/tutorial/i18n/text/stream.html

public class TestIOReader implements DustIOConsts {
	static class SizeCalculator implements CodepointVisitor {
		FontMetrics fontM;
		int size;

		public SizeCalculator(FontMetrics fontM) {
			this.fontM = fontM;
		}

		int getSize(String string) {
			size = 0;
			DustIOUtils.visitString(string, this);
			return size;
		}

		@Override
		public boolean process(int cp) {
			size += fontM.charWidth(cp);
			return true;
		}
	};
	
	static class ShowString extends JFrame {
		private static final long serialVersionUID = 1L;

		FontMetrics fontM;
		String outString;

		ShowString(String target, String title) {
			super(title);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			outString = target;
			
			Font font = new Font("Monospaced", Font.PLAIN, 36);
			fontM = getFontMetrics(font);
			setFont(font);

			int size = new SizeCalculator(fontM).getSize(outString);
			size += 24;

			setSize(size, fontM.getHeight() + 60);
			setLocation(getSize().width / 2, getSize().height / 2);
			setVisible(true);
		}

		public void paint(Graphics g) {
			Insets insets = getInsets();
			int x = insets.left;
			int y = insets.top;
			g.drawString(outString, x + 6, y + fontM.getAscent() + 14);
		}
	}

	public static void testReader(String testFile, String encoding) throws Exception {
		String jaString = new String("\u65e5\u672c\u8a9e\u6587\u5b57\u5217");

		FileOutputStream fos = new FileOutputStream(testFile);
		Writer out = new OutputStreamWriter(fos, encoding);
		out.write(jaString);
		out.close();

		StringBuffer buffer = new StringBuffer();
		FileInputStream fis = new FileInputStream(testFile);
		InputStreamReader in = new InputStreamReader(fis, encoding);
		int ch;
		while ((ch = DustIOUtils.read(in)) > -1) {
			buffer.append((char) ch);
		}
		in.close();
		
		String displayString = jaString + " " + buffer.toString();
		new ShowString(displayString, "Conversion Demo");
	}
}
