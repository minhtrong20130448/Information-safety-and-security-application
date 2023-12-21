package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import config.UIConfig;
import hash.MD5;
import hash.SHA;

import java.awt.TextArea;
import java.awt.Label;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class HashPanelUI extends JPanel {
	String notify = "";
	String result = "";
	File fileMd5;
	File fileSHA;
	JLabel lblNameFileMD5;
	JPanel panelContent;
	JPanel panelMD5;
	JPanel panelMD5File;
	CardLayout cardLayout;
	JComboBox comboBoxSHAString;
	JComboBox comboBoxTypeSHAFile;
	MD5 md5;
	SHA sha;

	UIConfig uiConfig = new UIConfig();

	public HashPanelUI() {
		md5 = new MD5();
		sha = new SHA();
		setLayout(null);
		JPanel panelNavigation = new JPanel();
		panelNavigation.setBackground(uiConfig.getNavigationBgColor());
		panelNavigation.setBounds(0, 0, 200, 750);
		add(panelNavigation);
		panelNavigation.setLayout(null);

		JLabel lblMP = new JLabel("  MD5");

		lblMP.setOpaque(true);
		lblMP.setBackground(uiConfig.getMenunavigationColor());
		lblMP.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMP.setBounds(0, 0, 200, 50);
		panelNavigation.add(lblMP);

		JButton btnMD5 = new JButton("MD5 String");
		btnMD5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "MD5");
			}
		});
		btnMD5.setHorizontalAlignment(SwingConstants.LEFT);
		btnMD5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMD5.setBackground(uiConfig.getSubmenunavigationColor());
		btnMD5.setBorderPainted(false);
		btnMD5.setBounds(0, 51, 200, 50);
		panelNavigation.add(btnMD5);

		JButton btnMD5File = new JButton("MD5 File");

		btnMD5File.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "MD5 File");
			}
		});
		btnMD5File.setHorizontalAlignment(SwingConstants.LEFT);
		btnMD5File.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMD5File.setBackground(uiConfig.getSubmenunavigationColor());
		btnMD5File.setBorderPainted(false);
		btnMD5File.setBounds(0, 101, 200, 50);
		panelNavigation.add(btnMD5File);

		JLabel lblSha = new JLabel("  SHA");
		lblSha.setOpaque(true);
		lblSha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSha.setBackground(uiConfig.getMenunavigationColor());
		lblSha.setBounds(0, 153, 200, 50);
		panelNavigation.add(lblSha);

		JButton btnShaString = new JButton("SHA String");
		btnShaString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "SHA String");
			}
		});
		btnShaString.setHorizontalAlignment(SwingConstants.LEFT);
		btnShaString.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShaString.setBorderPainted(false);
		btnShaString.setBackground(uiConfig.getSubmenunavigationColor());
		btnShaString.setBounds(0, 204, 200, 50);
		panelNavigation.add(btnShaString);

		JButton btnSHAFile = new JButton("SHA File");
		btnSHAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "SHA File");
			}
		});
		btnSHAFile.setHorizontalAlignment(SwingConstants.LEFT);
		btnSHAFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSHAFile.setBorderPainted(false);
		btnSHAFile.setBackground(uiConfig.getSubmenunavigationColor());
		btnSHAFile.setBounds(0, 255, 200, 50);
		panelNavigation.add(btnSHAFile);

// Content Panel
		panelContent = new JPanel();
		panelContent.setBackground(Color.WHITE);
		panelContent.setBounds(200, 0, 600, 750);
		add(panelContent);
		cardLayout = new CardLayout();
		panelContent.setLayout(cardLayout);

// MD5 Panel		

		panelMD5 = new JPanel();
		panelMD5.setBackground(new Color(255, 255, 255));
		panelMD5.setBounds(200, 0, 600, 750);
		panelMD5.setLayout(null);
		panelContent.add(panelMD5, "MD5");

		Label label = new Label("Input:");
		label.setFont(new Font("Dialog", Font.BOLD, 15));
		label.setBounds(28, 77, 541, 54);
		panelMD5.add(label);

		Label label_1 = new Label("Output:");
		label_1.setFont(new Font("Dialog", Font.BOLD, 15));
		label_1.setBounds(28, 393, 541, 54);
		panelMD5.add(label_1);
		
		JTextArea textAreaInputMD5 = new JTextArea();
		textAreaInputMD5.setLineWrap(true);
		textAreaInputMD5.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputMD5.setBounds(28, 137, 541, 148);
		panelMD5.add(textAreaInputMD5);

		JTextArea textAreaResultMD5 = new JTextArea();
		textAreaResultMD5.setLineWrap(true);
		textAreaResultMD5.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultMD5.setBounds(28, 457, 541, 148);
		panelMD5.add(textAreaResultMD5);
		
		Button buttonHashMD5String = new Button("Hash");
		buttonHashMD5String.setBackground(uiConfig.getPrimaryButtonColor());
		buttonHashMD5String.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = textAreaInputMD5.getText();
				String result = md5.checksum(input);
				textAreaResultMD5.setText(result);
			}
		});
		buttonHashMD5String.setBounds(28, 326, 149, 41);
		panelMD5.add(buttonHashMD5String);

		JLabel lblNewLabel = new JLabel("MD5");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(28, 0, 541, 60);
		panelMD5.add(lblNewLabel);
		
		JScrollPane scrollPane1 = new JScrollPane(textAreaInputMD5);
		scrollPane1.setBounds(28, 135, 540, 150);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane2 = new JScrollPane(textAreaResultMD5);
		scrollPane2.setBounds(28, 455, 540, 150);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelMD5.add(scrollPane1, BorderLayout.CENTER);
		panelMD5.add(scrollPane2, BorderLayout.CENTER);

// MD5 File Panel	

		panelMD5File = new JPanel();
		panelMD5File.setBackground(new Color(255, 255, 255));
		panelMD5File.setBounds(200, 0, 600, 750);
		panelMD5File.setLayout(null);

		JLabel lblNewLabel2 = new JLabel("MD5 File");
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel2.setBounds(25, 10, 565, 50);
		panelMD5File.add(lblNewLabel2);
		panelContent.add(panelMD5File, "MD5 File");

		lblNameFileMD5 = new JLabel("Vui lòng chọn file");
		lblNameFileMD5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameFileMD5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNameFileMD5.setBounds(24, 101, 340, 40);
		panelMD5File.add(lblNameFileMD5);

		Border border = BorderFactory.createLineBorder(Color.black, 1);
		lblNameFileMD5.setBorder(border);

		JButton btnChooseFileMD5 = new JButton("Choose file");
		btnChooseFileMD5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				int response = fileChooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					fileMd5 = fileChooser.getSelectedFile();
					fileName = fileMd5.getName();
					lblNameFileMD5.setText(fileName);
				} else if (response == JFileChooser.CANCEL_OPTION) {
					return;
				} else if (response == JFileChooser.ERROR_OPTION) {
					return;
				}
			}
		});
		btnChooseFileMD5.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnChooseFileMD5.setBounds(398, 101, 120, 40);
		panelMD5File.add(btnChooseFileMD5);

		JTextArea textAreaResultMD5File = new JTextArea();
		textAreaResultMD5File.setLineWrap(true);
		textAreaResultMD5File.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultMD5File.setBounds(25, 310, 540, 150);
		panelMD5File.add(textAreaResultMD5File);
		
		JScrollPane scrollPane6 = new JScrollPane(textAreaResultMD5File);
		scrollPane6.setBounds(25, 310, 540, 150);
		scrollPane6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelMD5File.add(scrollPane6, BorderLayout.CENTER);
		
		JButton btnHash = new JButton("Hash");
		btnHash.setBackground(uiConfig.getPrimaryButtonColor());
		btnHash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileMd5 == null) {
					System.out.println("File not exists!");
					JOptionPane.showMessageDialog(null, "File not exists!");
				} else {
					System.out.println("File exists!");
					if (fileMd5.isFile()) {
						try {
							result = md5.hash(fileMd5.getAbsolutePath());
							textAreaResultMD5File.setText(result);
						} catch (Exception e2) {
							e2.getStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "This is not file!");
					}
				}
			}
		});
		btnHash.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHash.setBounds(25, 186, 120, 40);
		panelMD5File.add(btnHash);
		
		JLabel lblNewLabel_1_1 = new JLabel("Output: ");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(25, 263, 202, 40);
		panelMD5File.add(lblNewLabel_1_1);
		


// Panel SHA String		

		JPanel panelSHAString = new JPanel();
		panelSHAString.setLayout(null);
		panelSHAString.setBackground(Color.WHITE);
		panelContent.add(panelSHAString, "SHA String");

		JLabel lblSha_1 = new JLabel("SHA");
		lblSha_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblSha_1.setBounds(28, 0, 541, 60);
		panelSHAString.add(lblSha_1);

		Label lblInputSHAString = new Label("Input:");
		lblInputSHAString.setFont(new Font("Dialog", Font.BOLD, 15));
		lblInputSHAString.setBounds(28, 164, 541, 54);
		panelSHAString.add(lblInputSHAString);

		Label label_1_1 = new Label("Output:");
		label_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		label_1_1.setBounds(28, 480, 541, 54);
		panelSHAString.add(label_1_1);

		JTextArea textAreaInputSHAString = new JTextArea();
		textAreaInputSHAString.setLineWrap(true);
		textAreaInputSHAString.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputSHAString.setBounds(28, 218, 521, 148);
		panelSHAString.add(textAreaInputSHAString);
		
		JTextArea textAreaOutputSHAString = new JTextArea();
		textAreaOutputSHAString.setLineWrap(true);
		textAreaOutputSHAString.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaOutputSHAString.setBounds(28, 538, 521, 148);
		panelSHAString.add(textAreaOutputSHAString);
		
		JScrollPane scrollPane3 = new JScrollPane(textAreaInputSHAString);
		scrollPane3.setBounds(28, 230, 540, 150);
		scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane4 = new JScrollPane(textAreaOutputSHAString);
		scrollPane4.setBounds(28, 540, 540, 150);
		scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelSHAString.add(scrollPane3, BorderLayout.CENTER);
		panelSHAString.add(scrollPane4, BorderLayout.CENTER);
		
		Button buttonHashSHAString = new Button("Hash");
		buttonHashSHAString.setBackground(uiConfig.getPrimaryButtonColor());
		buttonHashSHAString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typeSHA = comboBoxSHAString.getSelectedItem().toString();
				String input = textAreaInputSHAString.getText();
				String output = sha.hash(input, typeSHA);
				textAreaOutputSHAString.setText(output);
			}
		});
		buttonHashSHAString.setBounds(28, 413, 149, 41);
		panelSHAString.add(buttonHashSHAString);

		comboBoxSHAString = new JComboBox();
		comboBoxSHAString.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxSHAString.setModel(new DefaultComboBoxModel(
				new String[] { "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512/224", "SHA-512/256" }));
		comboBoxSHAString.setBounds(28, 98, 307, 41);
		panelSHAString.add(comboBoxSHAString);
		


// Panle SHA File		

		JPanel panelSHAFile = new JPanel();
		panelSHAFile.setLayout(null);
		panelSHAFile.setBackground(Color.WHITE);
		panelContent.add(panelSHAFile, "SHA File");

		JLabel lblShaFile = new JLabel("SHA File");
		lblShaFile.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblShaFile.setBounds(25, 10, 565, 50);
		panelSHAFile.add(lblShaFile);

		JLabel lblNameFileSHA = new JLabel("Vui lòng chọn file");
		lblNameFileSHA.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameFileSHA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNameFileSHA.setBounds(25, 185, 382, 40);
		panelSHAFile.add(lblNameFileSHA);
		lblNameFileSHA.setBorder(border);

		JButton btnChooseFileSHA = new JButton("Chọn tệp");
		btnChooseFileSHA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				int response = fileChooser.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					fileSHA = fileChooser.getSelectedFile();
					fileName = fileSHA.getName();
					lblNameFileSHA.setText(fileName);
				} else if (response == JFileChooser.CANCEL_OPTION) {
					return;
				} else if (response == JFileChooser.ERROR_OPTION) {
					return;
				}
			}
		});
		btnChooseFileSHA.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChooseFileSHA.setBounds(445, 185, 120, 40);
		panelSHAFile.add(btnChooseFileSHA);

		
		JTextArea textAreaResultSHAFile = new JTextArea();
		textAreaResultSHAFile.setLineWrap(true);
		textAreaResultSHAFile.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultSHAFile.setBounds(25, 384, 540, 148);
		panelSHAFile.add(textAreaResultSHAFile);
		
		
		JScrollPane scrollPane5 = new JScrollPane(textAreaResultSHAFile);
		scrollPane5.setBounds(28, 380, 540, 150);
		scrollPane5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelSHAFile.add(scrollPane5, BorderLayout.CENTER);
		
		JButton btnHashSHAFile = new JButton("Hash");
		btnHashSHAFile.setBackground(uiConfig.getPrimaryButtonColor());
		btnHashSHAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileSHA == null) {
					JOptionPane.showMessageDialog(null, "File not exists!");
				} else {
					String typeSHA = comboBoxTypeSHAFile.getSelectedItem().toString();
					if (fileSHA.isFile()) {
						try {
							String output = sha.hashFile(fileSHA.getAbsolutePath(), typeSHA);
							textAreaResultSHAFile.setText(output);
						} catch (Exception e2) {
							e2.getStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "This is not file!");
					}
				}
			}
		});
		btnHashSHAFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHashSHAFile.setBounds(26, 270, 120, 40);
		panelSHAFile.add(btnHashSHAFile);

		comboBoxTypeSHAFile = new JComboBox();
		comboBoxTypeSHAFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxTypeSHAFile.setModel(new DefaultComboBoxModel(
				new String[] { "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512/224", "SHA-512/256" }));
		comboBoxTypeSHAFile.setBounds(25, 103, 202, 41);
		panelSHAFile.add(comboBoxTypeSHAFile);

		JLabel lblNewLabel_1 = new JLabel("Output: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(25, 336, 202, 40);
		panelSHAFile.add(lblNewLabel_1);


		this.setVisible(true);
	}
}
