package edu.gmu.cs332.hw5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author session
 */
public class ShapeBrowser {
	private static String labelPrefix = "Number of button clicks: ";
	private int numClicks = 0;

	public Component createComponents() {
		final JLabel label = new JLabel(labelPrefix + "0    ");

		JButton button = new JButton("I'm a Swing button!");
		button.setMnemonic(KeyEvent.VK_I);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numClicks++;
				label.setText(labelPrefix + numClicks);
			}
		});
		label.setLabelFor(button);

		/*
		 * An easy way to put space between a top-level container
		 * and its contents is to put the contents in a JPanel
		 * that has an "empty" border.
		 */
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(
										30, //top
										30, //left
										10, //bottom
										30) //right
										);
		pane.setLayout(new GridLayout(0, 1));
		pane.add(button);
		pane.add(label);

		return pane;
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(
				UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {}
		
		//Create the top-level container and add contents to it.
		JFrame frame = new JFrame("SwingApplication");
		ShapeBrowser app = new ShapeBrowser();
		Component contents = app.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);

		//Finish setting up the frame, and show it.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.pack();
		frame.setVisible(true);
	}
}
