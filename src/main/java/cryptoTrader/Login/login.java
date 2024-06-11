package cryptoTrader.Login;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cryptoTrader.gui.MainUI;

/**
 * This class implements the ActionListener interface. Each login object has a
 * login frame, a login panel, a user label and text field, a password label and
 * text field, and a login button
 * 
 * @author Group 14
 */
public class login implements ActionListener {

	// login frame
	private static JFrame frame;

	// login panel
	private static JPanel panel;

	// user label
	private static JLabel userlalJLabel;

	// user text field
	private static JTextField userTextField;

	// password label
	private static JLabel passwordJLabel;

	// password text field
	private static JPasswordField passwordField;

	// login button
	private static JButton loginButton;

	/**
	 * Main method creates the login frame
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		frame = new JFrame();
		panel = new JPanel();

		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		userlalJLabel = new JLabel("User");
		userlalJLabel.setBounds(70, 20, 80, 25);
		panel.add(userlalJLabel);

		userTextField = new JTextField();
		userTextField.setBounds(150, 20, 165, 25);
		panel.add(userTextField);

		passwordJLabel = new JLabel("Password");
		passwordJLabel.setBounds(70, 60, 80, 25);
		panel.add(passwordJLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(150, 60, 165, 25);
		panel.add(passwordField);

		loginButton = new JButton("Login");
		loginButton.setBounds(150, 100, 80, 25);
		loginButton.addActionListener(new login());
		panel.add(loginButton);

		frame.setVisible(true);
	}

	/**
	 * Method is the action performed when the button is pressed. Checks
	 * database.txt to see if a recognized username and password was inputted.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String userString = userTextField.getText();
		String passwordString = passwordField.getText();

		BufferedReader inReader = null;
		String lineString = "";

		Boolean successBoolean = false;

		try {
			inReader = new BufferedReader(new FileReader("database.txt"));
			while ((lineString = inReader.readLine()) != null) {
				String[] userPasswordStrings = lineString.split(",");

				if (userString.equals(userPasswordStrings[0]) && passwordString.equals(userPasswordStrings[1])) {
					successBoolean = true;
					notificationFrame("Success! You have logged in.");
					// Opens MainUI when the user has successfully logged in
					MainUI mainUI = new MainUI();
					mainUI.main(null);
				}
			}

			if (successBoolean == false) {
				notificationFrame("Login Failed. Please try again.");
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Method creates a frame that displays a notification message
	 * 
	 * @param notificationString the notification message to be displayed
	 */
	private void notificationFrame(String notificationString) {

		JFrame notificationFrame = new JFrame();
		JPanel notificationPanel = new JPanel();

		notificationFrame.setSize(400, 100);
		notificationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		notificationFrame.add(notificationPanel);

		JLabel failedMessageString = new JLabel(notificationString);
		failedMessageString.setBounds(10, 20, 80, 25);
		notificationPanel.add(failedMessageString);

		notificationFrame.setVisible(true);
	}

}
