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

import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.commons.math3.linear.RealMatrix;

import commonpanel.SetKeySymmetryPanel;
import config.UIConfig;
import hash.MD5;
import hash.SHA;
import symmetry.AES;
import symmetry.DES;
import symmetry.Hill;
import symmetry.TripleDES;
import symmetry.Vigenere;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SymmetryPanelUI extends JPanel {
	String notify = "";
	String result = "";
	File fileMd5;
	File fileSHA;
	JPanel panelContent;
	JPanel panelDES;
	CardLayout cardLayout;
	JTextArea textAreaInputDESstring;
	JTextArea textAreaResultDESstring;
	Label labelWarningHill;

	SetKeySymmetryPanel setKeyDESPanel;
	SetKeySymmetryPanel setKeyHillPanel;
	SetKeySymmetryPanel setKeyVigenerePanel;
	SetKeySymmetryPanel setKey3DESPanel;
	SetKeySymmetryPanel setKeyAESPanel;
	UIConfig uiConfig = new UIConfig();

	public SymmetryPanelUI() {
		setLayout(null);
		setBounds(0, 0, 800, 750);
		JPanel panelNavigation = new JPanel();
		panelNavigation.setBackground(uiConfig.getNavigationBgColor());
		panelNavigation.setBounds(0, 0, 200, 750);
		add(panelNavigation);
		panelNavigation.setLayout(null);

		Border border = BorderFactory.createLineBorder(Color.black, 1);

		JLabel lblMP = new JLabel("Thuật toán");
		lblMP.setOpaque(true);
		lblMP.setBackground(uiConfig.getMenunavigationColor());
		lblMP.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMP.setBounds(0, 0, 200, 50);
		panelNavigation.add(lblMP);

		JButton btnMD5 = new JButton("DES");
		btnMD5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "DES String");
			}
		});
		btnMD5.setHorizontalAlignment(SwingConstants.LEFT);
		btnMD5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMD5.setBackground(uiConfig.getSubmenunavigationColor());
		btnMD5.setBorderPainted(false);
		btnMD5.setBounds(0, 50, 200, 50);
		panelNavigation.add(btnMD5);

		JButton btnShaString = new JButton("3DES");
		btnShaString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "3DES String");
			}
		});
		btnShaString.setHorizontalAlignment(SwingConstants.LEFT);
		btnShaString.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShaString.setBorderPainted(false);
		btnShaString.setBackground(uiConfig.getSubmenunavigationColor());
		btnShaString.setBounds(0, 150, 200, 50);
		panelNavigation.add(btnShaString);

		JButton btnVigenere = new JButton("Vigenere");
		btnVigenere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "Vinegere String");
			}
		});
		btnVigenere.setHorizontalAlignment(SwingConstants.LEFT);
		btnVigenere.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnVigenere.setBorderPainted(false);
		btnVigenere.setBackground(uiConfig.getSubmenunavigationColor());
		btnVigenere.setBounds(0, 200, 200, 50);
		panelNavigation.add(btnVigenere);

		JButton btnHillPanel = new JButton("Hill (Thư viện Math3)");
		btnHillPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "Hill String");
			}
		});
		btnHillPanel.setHorizontalAlignment(SwingConstants.LEFT);
		btnHillPanel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnHillPanel.setBorderPainted(false);
		btnHillPanel.setBackground(uiConfig.getSubmenunavigationColor());
		btnHillPanel.setBounds(0, 100, 200, 50);
		panelNavigation.add(btnHillPanel);

		JButton btnAES = new JButton("AES");
		btnAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContent, "AES String");
			}
		});
		btnAES.setHorizontalAlignment(SwingConstants.LEFT);
		btnAES.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAES.setBorderPainted(false);
		btnAES.setBackground(uiConfig.getSubmenunavigationColor());
		btnAES.setBounds(0, 250, 200, 50);
		panelNavigation.add(btnAES);

// Content Panel
		panelContent = new JPanel();
		panelContent.setBackground(Color.WHITE);
		panelContent.setBounds(200, 0, 600, 750);
		add(panelContent);
		cardLayout = new CardLayout();
		panelContent.setLayout(cardLayout);

// DES String Panel		

		panelDES = new JPanel();
		panelDES.setBackground(new Color(255, 255, 255));
		panelDES.setBounds(200, 0, 600, 750);
		panelDES.setLayout(null);
		panelContent.add(panelDES, "DES String");

		Label labelInputDESstring = new Label("Input:");
		labelInputDESstring.setFont(new Font("Dialog", Font.BOLD, 15));
		labelInputDESstring.setBounds(28, 77, 541, 47);
		panelDES.add(labelInputDESstring);

		Label labelResultDESstring = new Label("Output (Base64):");
		labelResultDESstring.setFont(new Font("Dialog", Font.BOLD, 15));
		labelResultDESstring.setBounds(28, 393, 541, 54);
		panelDES.add(labelResultDESstring);

		Button buttonEncryptDESstring = new Button("Encrypt");
		buttonEncryptDESstring.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptDESstring.setBackground(uiConfig.getPrimaryButtonColor());
		buttonEncryptDESstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyDESPanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					if (setKeyDESPanel.getDes() != null) {
						try {
							DES des = setKeyDESPanel.getDes();
							String plaintext = textAreaInputDESstring.getText();
							String ciphertext = des.encriptToBase64(plaintext, des.getKey());
							textAreaResultDESstring.setText(ciphertext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}
				}
			}
		});
		buttonEncryptDESstring.setBounds(28, 326, 149, 41);
		panelDES.add(buttonEncryptDESstring);

		Button buttonDecryptDEString = new Button("Decrypt");
		buttonDecryptDEString.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecryptDEString.setBackground(uiConfig.getPrimaryButtonColor());
		buttonDecryptDEString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyDESPanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					DES des = setKeyDESPanel.getDes();
					String plaintext = textAreaInputDESstring.getText();
					try {
						String ciphertext = des.decyptToBase64(plaintext, des.getKey());
						textAreaResultDESstring.setText(ciphertext);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		buttonDecryptDEString.setBounds(207, 326, 149, 41);
		panelDES.add(buttonDecryptDEString);

		JLabel lblNewLabel = new JLabel("DES");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(28, 0, 274, 60);
		panelDES.add(lblNewLabel);

		JButton btnSetKeyDESstring = new JButton("Set key");
		btnSetKeyDESstring.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKeyDESstring.setBackground(new Color(196, 235, 214));
		btnSetKeyDESstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyDESPanel == null) {
					setKeyDESPanel = new SetKeySymmetryPanel("DES");
				} else {
					setKeyDESPanel.setVisible(true);
				}
			}
		});
		btnSetKeyDESstring.setBounds(398, 16, 149, 41);
		panelDES.add(btnSetKeyDESstring);

		textAreaInputDESstring = new JTextArea();
		textAreaInputDESstring.setLineWrap(true);
		textAreaInputDESstring.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputDESstring.setBounds(28, 130, 540, 150);
		textAreaInputDESstring.setBorder(border);
		panelDES.add(textAreaInputDESstring);

		textAreaResultDESstring = new JTextArea();
		textAreaResultDESstring.setLineWrap(true);
		textAreaResultDESstring.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultDESstring.setBounds(28, 450, 541, 150);
		textAreaResultDESstring.setBorder(border);
		panelDES.add(textAreaResultDESstring);

		JScrollPane scrollPane = new JScrollPane(textAreaInputDESstring);
		scrollPane.setBounds(28, 130, 540, 150);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane2 = new JScrollPane(textAreaResultDESstring);
		scrollPane2.setBounds(28, 450, 540, 150);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelDES.add(scrollPane, BorderLayout.CENTER);
		panelDES.add(scrollPane2, BorderLayout.CENTER);

// Panel Hill String		

		JPanel panelHill = new JPanel();
		panelHill.setLayout(null);
		panelHill.setBackground(Color.WHITE);
		panelContent.add(panelHill, "Hill String");

		JLabel lblSha_1 = new JLabel("Hill");
		lblSha_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblSha_1.setBounds(28, 0, 272, 60);
		panelHill.add(lblSha_1);

		Label lblInputSHAString = new Label("Input:");
		lblInputSHAString.setFont(new Font("Dialog", Font.BOLD, 15));
		lblInputSHAString.setBounds(28, 77, 541, 47);
		panelHill.add(lblInputSHAString);

		Label label_1_1 = new Label("Output:");
		label_1_1.setFont(new Font("Dialog", Font.BOLD, 15));
		label_1_1.setBounds(28, 393, 272, 54);
		panelHill.add(label_1_1);

		JTextArea textAreaInputHill = new JTextArea();
		textAreaInputHill.setBorder(border);
		textAreaInputHill.setLineWrap(true);
		textAreaInputHill.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputHill.setBounds(28, 130, 540, 150);
		panelHill.add(textAreaInputHill);

		JTextArea textAreaResultHill = new JTextArea();
		textAreaResultHill.setBorder(border);
		textAreaResultHill.setLineWrap(true);
		textAreaResultHill.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultHill.setBounds(28, 450, 541, 150);
		panelHill.add(textAreaResultHill);
		
		JScrollPane scrollPane3 = new JScrollPane(textAreaInputHill);
		scrollPane3.setBounds(28, 130, 540, 150);
		scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane4 = new JScrollPane(textAreaResultHill);
		scrollPane4.setBounds(28, 450, 541, 150);
		scrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelHill.add(scrollPane3, BorderLayout.CENTER);
		panelHill.add(scrollPane4, BorderLayout.CENTER);

		Button buttonEncryptHill = new Button("Encrypt");
		buttonEncryptHill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyHillPanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					if (setKeyHillPanel.getHill() != null) {
						Hill hill = setKeyHillPanel.getHill();
						RealMatrix key = hill.getKeyMatrix();
						try {
							String plaintext = textAreaInputHill.getText();
							labelWarningHill.setText(hill.checkInsufficientData(plaintext));
							String ciphertext = hill.encryptWithCommonsMath3(plaintext, key);
							textAreaResultHill.setText(ciphertext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}

				}
			}
		});
		buttonEncryptHill.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptHill.setBackground(new Color(203, 163, 255));
		buttonEncryptHill.setBounds(28, 326, 149, 41);
		panelHill.add(buttonEncryptHill);

		Button buttonDecryptHill = new Button("Decrypt");
		buttonDecryptHill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyHillPanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					if (setKeyHillPanel.getHill() != null) {
						Hill hill = setKeyHillPanel.getHill();
						RealMatrix key = hill.getKeyMatrix();
						try {
							String ciphertext = textAreaInputHill.getText();
							String plaintext = hill.decryptWithCommonsMath3(ciphertext, key);
							textAreaResultHill.setText(plaintext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}

				}
			}
		});
		buttonDecryptHill.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecryptHill.setBackground(new Color(203, 163, 255));
		buttonDecryptHill.setBounds(207, 326, 149, 41);
		panelHill.add(buttonDecryptHill);

		JButton btnSetKeyHill = new JButton("Set key");
		btnSetKeyHill.setBackground(new Color(196, 235, 214));
		btnSetKeyHill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyHillPanel == null) {
					setKeyHillPanel = new SetKeySymmetryPanel("Hill");
				} else {
					setKeyHillPanel.setVisible(true);
				}
			}
		});
		btnSetKeyHill.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKeyHill.setBounds(398, 16, 149, 41);
		panelHill.add(btnSetKeyHill);

		labelWarningHill = new Label("");
		labelWarningHill.setAlignment(Label.RIGHT);
		labelWarningHill.setFont(new Font("Dialog", Font.BOLD, 15));
		labelWarningHill.setBounds(306, 393, 263, 54);
		panelHill.add(labelWarningHill);

//Panel VInegere		

		JPanel panelVigenere = new JPanel();
		panelVigenere.setLayout(null);
		panelVigenere.setBackground(Color.WHITE);
		panelContent.add(panelVigenere, "Vinegere String");

		Label labelInputVigenere = new Label("Input:");
		labelInputVigenere.setFont(new Font("Dialog", Font.BOLD, 15));
		labelInputVigenere.setBounds(28, 77, 541, 47);
		panelVigenere.add(labelInputVigenere);

		Label labelResultVigenere = new Label("Output:");
		labelResultVigenere.setFont(new Font("Dialog", Font.BOLD, 15));
		labelResultVigenere.setBounds(28, 393, 541, 54);
		panelVigenere.add(labelResultVigenere);

		JTextArea textAreaInputVigenere = new JTextArea();
		textAreaInputVigenere.setLineWrap(true);
		textAreaInputVigenere.setBorder(border);
		textAreaInputVigenere.setBounds(28, 130, 540, 150);
		panelVigenere.add(textAreaInputVigenere);

		JTextArea textAreaResultVigenere = new JTextArea();
		textAreaResultVigenere.setLineWrap(true);
		textAreaResultVigenere.setBorder(border);
		textAreaResultVigenere.setBounds(28, 450, 540, 150);
		panelVigenere.add(textAreaResultVigenere);
		
		JScrollPane scrollPane5 = new JScrollPane(textAreaInputVigenere);
		scrollPane5.setBounds(28, 130, 540, 150);
		scrollPane5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane6 = new JScrollPane(textAreaResultVigenere);
		scrollPane6.setBounds(28, 450, 540, 150);
		scrollPane6.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelVigenere.add(scrollPane5, BorderLayout.CENTER);
		panelVigenere.add(scrollPane6, BorderLayout.CENTER);

		Button buttonEncryptVigenere = new Button("Encrypt");
		buttonEncryptVigenere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyVigenerePanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					if (setKeyVigenerePanel.getVigenere() != null) {
						if (setKeyVigenerePanel.getVigenere().getKey() != null) {
							String plaintext = textAreaInputVigenere.getText();
							Vigenere vigenere = setKeyVigenerePanel.getVigenere();
							String ciphertext = vigenere.encryptVigenere(plaintext, vigenere.getKey());
							textAreaResultVigenere.setText(ciphertext);
						} else {
							JOptionPane.showMessageDialog(null, "Key is null");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}
				}
			}
		});
		buttonEncryptVigenere.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptVigenere.setBackground(new Color(203, 163, 255));
		buttonEncryptVigenere.setBounds(28, 326, 149, 41);
		panelVigenere.add(buttonEncryptVigenere);

		Button buttonDecryptVigenere = new Button("Decrypt");
		buttonDecryptVigenere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyVigenerePanel == null) {
					JOptionPane.showMessageDialog(null, "Key is null");
				} else {
					if (setKeyVigenerePanel.getVigenere() != null) {
						String ciphertext = textAreaInputVigenere.getText();
						Vigenere vigenere = setKeyVigenerePanel.getVigenere();
						String plaintext = vigenere.decryptVigenere(ciphertext, vigenere.getKey());
						textAreaResultVigenere.setText(plaintext);
					}
				}
			}
		});
		buttonDecryptVigenere.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecryptVigenere.setBackground(new Color(203, 163, 255));
		buttonDecryptVigenere.setBounds(207, 326, 149, 41);
		panelVigenere.add(buttonDecryptVigenere);

		JLabel lblNewLabel_1 = new JLabel("Vigenere");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(28, 0, 274, 60);
		panelVigenere.add(lblNewLabel_1);

		JButton btnSetKeyVigenere = new JButton("Set key");
		btnSetKeyVigenere.setBackground(new Color(196, 235, 214));
		btnSetKeyVigenere.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKeyVigenere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyVigenerePanel == null) {
					setKeyVigenerePanel = new SetKeySymmetryPanel("Vigenere");
				} else {
					setKeyVigenerePanel.setVisible(true);
				}
			}
		});
		btnSetKeyVigenere.setBounds(398, 16, 149, 41);
		panelVigenere.add(btnSetKeyVigenere);

//Panel 3DES 

		JPanel panelTripleDES = new JPanel();
		panelTripleDES.setLayout(null);
		panelTripleDES.setBackground(Color.WHITE);
		panelContent.add(panelTripleDES, "3DES String");

		Label labelInput3DES = new Label("Input:");
		labelInput3DES.setFont(new Font("Dialog", Font.BOLD, 15));
		labelInput3DES.setBounds(28, 77, 541, 47);
		panelTripleDES.add(labelInput3DES);

		Label labelResult3DES = new Label("Output (Base64):");
		labelResult3DES.setFont(new Font("Dialog", Font.BOLD, 15));
		labelResult3DES.setBounds(28, 393, 541, 54);
		panelTripleDES.add(labelResult3DES);

		JTextArea textAreaInput3DES = new JTextArea();
		textAreaInput3DES.setBorder(border);
		textAreaInput3DES.setLineWrap(true);
		textAreaInput3DES.setBounds(28, 130, 540, 150);
		panelTripleDES.add(textAreaInput3DES);

		JTextArea textAreaResult3DES = new JTextArea();
		textAreaResult3DES.setBorder(border);
		textAreaResult3DES.setLineWrap(true);
		textAreaResult3DES.setBounds(28, 450, 543, 150);
		panelTripleDES.add(textAreaResult3DES);

		JScrollPane scrollPane7 = new JScrollPane(textAreaInput3DES);
		scrollPane7.setBounds(28, 130, 540, 150);
		scrollPane7.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane8 = new JScrollPane(textAreaResult3DES);
		scrollPane8.setBounds(28, 450, 540, 150);
		scrollPane8.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelTripleDES.add(scrollPane7, BorderLayout.CENTER);
		panelTripleDES.add(scrollPane8, BorderLayout.CENTER);
		
		Button buttonEncrypt3DES = new Button("Encrypt");
		buttonEncrypt3DES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKey3DESPanel != null) {
					if (setKey3DESPanel.getTripleDes() != null) {
						try {
							String plaintext = textAreaInput3DES.getText();
							TripleDES tripleDES = setKey3DESPanel.getTripleDes();
							String ciphertext;
							ciphertext = tripleDES.encrypt(plaintext, tripleDES.getKey());
							textAreaResult3DES.setText(ciphertext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Key is null");
				}
			}
		});
		buttonEncrypt3DES.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncrypt3DES.setBackground(new Color(203, 163, 255));
		buttonEncrypt3DES.setBounds(28, 326, 149, 41);
		panelTripleDES.add(buttonEncrypt3DES);

		Button buttonDecrypt3DES = new Button("Decrypt");
		buttonDecrypt3DES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKey3DESPanel != null) {
					if (setKey3DESPanel.getTripleDes() != null) {
						try {
							String ciphertext = textAreaInput3DES.getText();
							TripleDES tripleDES = setKey3DESPanel.getTripleDes();
							String plaintext = tripleDES.decrypt(ciphertext, tripleDES.getKey());
							textAreaResult3DES.setText(plaintext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Key is null");
				}

			}
		});
		buttonDecrypt3DES.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecrypt3DES.setBackground(new Color(203, 163, 255));
		buttonDecrypt3DES.setBounds(207, 326, 149, 41);
		panelTripleDES.add(buttonDecrypt3DES);

		JLabel lblNewLabel_1_1 = new JLabel("TRIPLE DES");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(28, 0, 274, 60);
		panelTripleDES.add(lblNewLabel_1_1);

		JButton btnSetKey3DES = new JButton("Set key");
		btnSetKey3DES.setBackground(new Color(196, 235, 214));
		btnSetKey3DES.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKey3DES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKey3DESPanel == null) {
					setKey3DESPanel = new SetKeySymmetryPanel("3DES");
				} else {
					setKey3DESPanel.setVisible(true);
				}
			}
		});
		btnSetKey3DES.setBounds(398, 16, 149, 41);
		panelTripleDES.add(btnSetKey3DES);

//Panel AES		

		JPanel panelAES = new JPanel();
		panelAES.setLayout(null);
		panelAES.setBackground(Color.WHITE);
		panelContent.add(panelAES, "AES String");

		Label labelInputAES = new Label("Input:");
		labelInputAES.setFont(new Font("Dialog", Font.BOLD, 15));
		labelInputAES.setBounds(28, 77, 541, 47);
		panelAES.add(labelInputAES);

		Label labelResultAES = new Label("Output (Base64):");
		labelResultAES.setFont(new Font("Dialog", Font.BOLD, 15));
		labelResultAES.setBounds(28, 393, 541, 54);
		panelAES.add(labelResultAES);

		JTextArea textAreaInputAES = new JTextArea();
		textAreaInputAES.setBorder(border);
		textAreaInputAES.setLineWrap(true);
		textAreaInputAES.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaInputAES.setBounds(28, 130, 540, 150);
		panelAES.add(textAreaInputAES);

		JTextArea textAreaResultAES = new JTextArea();
		textAreaResultAES.setBorder(border);
		textAreaResultAES.setLineWrap(true);
		textAreaResultAES.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaResultAES.setBounds(28, 450, 541, 150);
		panelAES.add(textAreaResultAES);
		
		JScrollPane scrollPane9 = new JScrollPane(textAreaInputAES);
		scrollPane9.setBounds(28, 130, 540, 150);
		scrollPane9.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane10 = new JScrollPane(textAreaResultAES);
		scrollPane10.setBounds(28, 450, 540, 150);
		scrollPane10.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panelAES.add(scrollPane9, BorderLayout.CENTER);
		panelAES.add(scrollPane10, BorderLayout.CENTER);
		

		Button buttonEncryptAES = new Button("Encrypt");
		buttonEncryptAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyAESPanel != null) {
					if (setKeyAESPanel.getAes() != null) {
						try {
							String ciphertext = textAreaInputAES.getText();
							AES ase = setKeyAESPanel.getAes();
							String plaintext = ase.encryptAES(ciphertext, ase.getKey());
							textAreaResultAES.setText(plaintext);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Key is null");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Key is null");
				}
			}
		});
		buttonEncryptAES.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonEncryptAES.setBackground(new Color(203, 163, 255));
		buttonEncryptAES.setBounds(28, 326, 149, 41);
		panelAES.add(buttonEncryptAES);

		Button buttonDecrypt3AES = new Button("Decrypt");
		buttonDecrypt3AES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String ciphertext = textAreaInputAES.getText();
					AES ase = setKeyAESPanel.getAes();
					String plaintext = ase.decryptAES(ciphertext, ase.getKey());
					textAreaResultAES.setText(plaintext);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		buttonDecrypt3AES.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonDecrypt3AES.setBackground(new Color(203, 163, 255));
		buttonDecrypt3AES.setBounds(207, 326, 149, 41);
		panelAES.add(buttonDecrypt3AES);

		JLabel lblNewLabel_1_1_1 = new JLabel("AES");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1_1_1.setBounds(28, 0, 274, 60);
		panelAES.add(lblNewLabel_1_1_1);

		JButton btnSetKeyAES = new JButton("Set key");
		btnSetKeyAES.setBackground(new Color(196, 235, 214));
		btnSetKeyAES.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSetKeyAES.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setKeyAESPanel == null) {
					setKeyAESPanel = new SetKeySymmetryPanel("AES");
				} else {
					setKeyAESPanel.setVisible(true);
				}
			}
		});
		btnSetKeyAES.setBounds(398, 16, 149, 41);
		panelAES.add(btnSetKeyAES);

		this.setVisible(true);
	}
}
