package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.ObjectInputFilter.Config;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import asymmetric.RSA;
import commonpanel.SetKeyAsymmetricPanel;
import config.UIConfig;
import hash.MD5;
import hash.SHA;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class AsymmetricPanelUI extends JPanel {
	String notify = ""; 
	String result = "";
	File fileSourceRSA;
	File fileDestRSA;
	RSA rsa;
	
	JLabel lblNameFileSourceRSA;
	JPanel panelContent;
	JPanel panelRSAstring;
	JPanel panelRSAFile;
	CardLayout cardLayout;
	SetKeyAsymmetricPanel setKeyPanel;
	JTextArea textAreaResultRSAstring;
	JTextArea textAreaInputRSAstring;
	
	UIConfig uiConfig = new UIConfig();
	
	public AsymmetricPanelUI() {
		rsa = new RSA();
		setLayout(null);
		setBounds(0, 0, 800, 750);
		JPanel panelNavigation = new JPanel();
		panelNavigation.setBackground(uiConfig.getNavigationBgColor());
		panelNavigation.setBounds(0, 0, 200, 750);
		add(panelNavigation);
		panelNavigation.setLayout(null);

		JLabel lblMP = new JLabel("Thuật toán");
		lblMP.setOpaque(true);
		lblMP.setBackground(new Color(221, 120, 254));
		lblMP.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMP.setBounds(0, 0, 200, 50);
		panelNavigation.add(lblMP);

		JButton btnRSAstring = new JButton("RSA");
		btnRSAstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "RSA");
			}
		});
		btnRSAstring.setHorizontalAlignment(SwingConstants.LEFT);
		btnRSAstring.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRSAstring.setBackground(uiConfig.getSubmenunavigationColor());
		btnRSAstring.setBorderPainted(false);
		btnRSAstring.setBounds(0, 50, 200, 50);
		panelNavigation.add(btnRSAstring);

// Content Panel
		panelContent = new JPanel();
		panelContent.setBackground(Color.WHITE);
		panelContent.setBounds(200, 0, 600, 750);
		add(panelContent);
		cardLayout = new CardLayout();
		panelContent.setLayout(cardLayout);
		
// RSA Panel		

		panelRSAstring = new JPanel();
		panelRSAstring.setBackground(new Color(255, 255, 255));
		panelRSAstring.setBounds(200, 0, 600, 750);
		panelRSAstring.setLayout(null);
		panelContent.add(panelRSAstring, "RSA");

		Label label = new Label("Input:");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(28, 77, 541, 54);
		panelRSAstring.add(label);

		Label label_1 = new Label("Output Base64:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(28, 393, 541, 54);
		panelRSAstring.add(label_1);

		Button buttonEncryptRSAstring = new Button("Encrypt");
		buttonEncryptRSAstring.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptRSAstring.setBackground(uiConfig.getPrimaryButtonColor());
		buttonEncryptRSAstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					JOptionPane.showMessageDialog(null, "Error: key is null");
				}else {
					if(setKeyPanel.getPrivateKey() == null) {
						JOptionPane.showMessageDialog(null, "Error: key is null");
					}else {
						String plainText = textAreaInputRSAstring.getText();
						try {
							String cipherText = rsa.encryptRSAtoBase64(plainText, setKeyPanel.getPublicKey());
							textAreaResultRSAstring.setText(cipherText);
						} catch (Exception e2) {
							e2.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error " +  e2.getMessage());
						}
					}
				}
			}
		});
		buttonEncryptRSAstring.setBounds(28, 326, 149, 41);
		panelRSAstring.add(buttonEncryptRSAstring);
		
		Button buttonDecryptRSAstring = new Button("Decrypt");
		buttonDecryptRSAstring.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecryptRSAstring.setBackground(uiConfig.getPrimaryButtonColor());
		buttonDecryptRSAstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					JOptionPane.showMessageDialog(null, "Error: key is null");
				}else {
					if(setKeyPanel.getPrivateKey() == null) {
						JOptionPane.showMessageDialog(null, "Error: key is null");
					}else {
						String plainText = textAreaInputRSAstring.getText();
						try {
							String cipherText = rsa.decryptRSAtoBase64(plainText, setKeyPanel.getPrivateKey());
							textAreaResultRSAstring.setText(cipherText);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error " + e1.getMessage());
						}
					}
				}
			}
		});
		buttonDecryptRSAstring.setBounds(207, 326, 149, 41);
		panelRSAstring.add(buttonDecryptRSAstring);
		
		JLabel lblNewLabel = new JLabel("RSA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(28, 0, 274, 60);
		panelRSAstring.add(lblNewLabel);
				
		JButton btnSetKeyRSA = new JButton("Set key");
		btnSetKeyRSA.setBackground(new Color(196, 235, 214));
		btnSetKeyRSA.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKeyRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					setKeyPanel = new SetKeyAsymmetricPanel();
				}else {
					setKeyPanel.setVisible(true);
				}
			}
		});
		btnSetKeyRSA.setBounds(398, 16, 149, 41);
		panelRSAstring.add(btnSetKeyRSA);
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		textAreaInputRSAstring = new JTextArea();
		textAreaInputRSAstring.setBorder(border);
		textAreaInputRSAstring.setLineWrap(true);
		textAreaInputRSAstring.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputRSAstring.setBounds(29, 135, 540, 150);
		panelRSAstring.add(textAreaInputRSAstring);
		
		textAreaResultRSAstring = new JTextArea();
		textAreaResultRSAstring.setLineWrap(true);
		textAreaResultRSAstring.setBorder(border);
		textAreaResultRSAstring.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultRSAstring.setBounds(28, 448, 541, 150);
		panelRSAstring.add(textAreaResultRSAstring);
		
		JScrollPane scrollPane1 = new JScrollPane(textAreaInputRSAstring);
		scrollPane1.setBounds(28, 130, 540, 150);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane2 = new JScrollPane(textAreaResultRSAstring);
		scrollPane2.setBounds(28, 450, 540, 150);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelRSAstring.add(scrollPane1, BorderLayout.CENTER);
		panelRSAstring.add(scrollPane2, BorderLayout.CENTER);
		
// RSA File Panel	
		
		panelRSAFile = new JPanel();
		panelRSAFile.setBackground(new Color(255, 255, 255));
		panelRSAFile.setBounds(200, 0, 600, 750);
		panelRSAFile.setLayout(null);
		
		JLabel lblNewLabel2 = new JLabel("RSA File");
		lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel2.setBounds(25, 0, 340, 60);
		panelRSAFile.add(lblNewLabel2);
		panelContent.add(panelRSAFile, "RSA File");
		
		lblNameFileSourceRSA = new JLabel("Vui lòng chọn file");
		lblNameFileSourceRSA.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameFileSourceRSA.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNameFileSourceRSA.setBounds(25, 113, 340, 40);
		panelRSAFile.add(lblNameFileSourceRSA);
		
		 lblNameFileSourceRSA.setBorder(border);

		
		JButton btnChooseFileSourceRSA = new JButton("Choose source file");
		btnChooseFileSourceRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					fileSourceRSA = fileChooser.getSelectedFile();
					fileName = fileSourceRSA.getName();
					lblNameFileSourceRSA.setText(fileName);
				}else if(response == JFileChooser.CANCEL_OPTION) {
					return;
				}else if(response == JFileChooser.ERROR_OPTION) {
					return;
				}
			}
		});
		btnChooseFileSourceRSA.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnChooseFileSourceRSA.setBounds(399, 113, 163, 40);
		panelRSAFile.add(btnChooseFileSourceRSA);
		
		JLabel lblNameFileDestRSA = new JLabel("Vui lòng chọn file");
		lblNameFileDestRSA.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameFileDestRSA.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNameFileDestRSA.setBounds(26, 200, 340, 40);
		lblNameFileDestRSA.setBorder(border);
		panelRSAFile.add(lblNameFileDestRSA);
		
		JButton btnChooseFileDestRSA = new JButton("Choose dest file");
		btnChooseFileDestRSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					fileDestRSA = fileChooser.getSelectedFile();
					fileName = fileDestRSA.getName();
					lblNameFileDestRSA.setText(fileName);
				}else if(response == JFileChooser.CANCEL_OPTION) {
					return;
				}else if(response == JFileChooser.ERROR_OPTION) {
					return;
				}
			}
		});
		btnChooseFileDestRSA.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnChooseFileDestRSA.setBounds(400, 200, 162, 40);
		panelRSAFile.add(btnChooseFileDestRSA);
		
		JButton btnSetKeyRSAFile = new JButton("Set key");
		btnSetKeyRSAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					setKeyPanel = new SetKeyAsymmetricPanel();
				}else {
					setKeyPanel.setVisible(true);
				}
			}
		});
		btnSetKeyRSAFile.setBounds(399, 16, 163, 41);
		panelRSAFile.add(btnSetKeyRSAFile);
		
		Button buttonEncryptRSAFile = new Button("Encrypt");
		buttonEncryptRSAFile.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptRSAFile.setBackground(uiConfig.getPrimaryButtonColor());
		buttonEncryptRSAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					JOptionPane.showMessageDialog(null, "Error: key is null");
				}else {
					if(setKeyPanel.getPrivateKey() == null) {
						JOptionPane.showMessageDialog(null, "Error: key is null");
					}else {
						if(fileSourceRSA != null && fileSourceRSA.exists()) {
							if(fileDestRSA != null) {
								try {
									rsa.encryptFile(setKeyPanel.getPublicKey(), fileSourceRSA.getAbsolutePath(), fileDestRSA.getAbsolutePath());
									JOptionPane.showMessageDialog(null, "File encrypted successfully");
								} catch (Exception e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
								}
							}else {
								JOptionPane.showMessageDialog(null, "Error: File dest not exists!");
							}
						}else {
							JOptionPane.showMessageDialog(null, "Error: File source not exists!");
						}
					}
				}
			}
		});
		buttonEncryptRSAFile.setBounds(25, 285, 149, 41);
		panelRSAFile.add(buttonEncryptRSAFile);
		
		Button buttonDecryptRSAFile = new Button("Decrypt");
		buttonDecryptRSAFile.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecryptRSAFile.setBackground(uiConfig.getPrimaryButtonColor());
		buttonDecryptRSAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(setKeyPanel == null) {
					JOptionPane.showMessageDialog(null, "Error: key is null");
				}else {
					if(setKeyPanel.getPrivateKey() == null) {
						JOptionPane.showMessageDialog(null, "Error: key is null");
					}else {
						try {
							rsa.decryptFile(setKeyPanel.getPrivateKey(), fileSourceRSA.getAbsolutePath(), fileDestRSA.getAbsolutePath());
							JOptionPane.showMessageDialog(null, "File decrypted successfully");
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error " + e1.getMessage());
						}
					}
				}
			}
		});
		buttonDecryptRSAFile.setBounds(204, 285, 149, 41);
		panelRSAFile.add(buttonDecryptRSAFile);
		
		this.setVisible(true);
	}
}
