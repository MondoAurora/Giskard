package me.giskard.mod;

import java.awt.Font;
import java.awt.FontMetrics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import me.giskard.dust.io.DustIOConsts;
import me.giskard.dust.io.DustIOUtils;

// Based on https://docs.oracle.com/javase/tutorial/i18n/text/stream.html

public class TestIOReader implements DustIOConsts {
	
	static class ShowString extends JFrame {
		private static final long serialVersionUID = 1L;

		FontMetrics fontM;

		ShowString(String target, String title) {
			super(title);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			Font font = new Font("Monospaced", Font.PLAIN, 36);
			setFont(font);

			JLabel lbl = new JLabel(target);
			lbl.setFont(font);
			
			getContentPane().add(lbl);

			setLocation(100, 100);
			
			pack();
			setVisible(true);
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
