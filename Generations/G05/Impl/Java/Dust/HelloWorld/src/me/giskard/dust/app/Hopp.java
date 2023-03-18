package me.giskard.dust.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

public class Hopp {

	public static void main(String[] args) {
		JFrame frm = new JFrame("heh");
		
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splMain.setLeftComponent(new JLabel("Egyes"));
		splMain.setRightComponent(new JLabel("Kettes"));
		splMain.setResizeWeight(0.3);
		splMain.setDividerLocation(0.3);

		Container cp = frm.getContentPane();

		cp.add(splMain, BorderLayout.CENTER);

//		JPanel pnlMain = new JPanel(new BorderLayout());
//		pnlMain.add(splMain, BorderLayout.CENTER);
//
//		setContentPane(pnlMain);

		Dimension paneSize = new Dimension(1000, 800);
		splMain.setPreferredSize(paneSize);

		frm.pack();
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}

}
