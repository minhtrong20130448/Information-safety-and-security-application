package commonpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.commons.math3.linear.RealMatrix;

import symmetry.AES;
import symmetry.DES;
import symmetry.Hill;
import symmetry.TripleDES;
import symmetry.Vigenere;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SetKeySymmetryPanel extends JFrame {
	private DES des;
	private Hill hill;
	private Vigenere vigenere;
	private TripleDES tripleDes;
	private AES aes;
	private JComboBox comboBoxKeySize;
	private String nameAlgorithm;
	JTextArea textKey;

	public DES getDes() {
		return des;
	}

	public void setDes(DES des) {
		this.des = des;
	}

	public String getNameAlgorithm() {
		return nameAlgorithm;
	}

	public void setNameAlgorithm(String nameAlgorithm) {
		this.nameAlgorithm = nameAlgorithm;
	}

	public Hill getHill() {
		return hill;
	}

	public void setHill(Hill hill) {
		this.hill = hill;
	}

	public Vigenere getVigenere() {
		return vigenere;
	}

	public void setVigenere(Vigenere vigenere) {
		this.vigenere = vigenere;
	}

	public TripleDES getTripleDes() {
		return tripleDes;
	}

	public void setTripleDes(TripleDES tripleDes) {
		this.tripleDes = tripleDes;
	}

	public AES getAes() {
		return aes;
	}

	public void setAes(AES aes) {
		this.aes = aes;
	}

	private void enterKey() {
		String messageEnterSuccess = "Key created successfully!";
		int sizeKey = 0;

		if (comboBoxKeySize != null)
			sizeKey = Integer.parseInt(comboBoxKeySize.getSelectedItem().toString());

		String key = textKey.getText();

		switch (this.nameAlgorithm) {
		case "DES":
			if (des == null) {
				des = new DES();
			}
			try {
				des.createKeyAvailable(key);
				JOptionPane.showMessageDialog(null, messageEnterSuccess);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			break;

		case "Hill":
			String massage = "";
			if (hill == null) {
				hill = new Hill();
			}

			try {
				hill.enterKeyHill(key, sizeKey);
				boolean validKey = hill.isValidKey(hill.getKeyMatrix());
				if (validKey) {
					massage = messageEnterSuccess;
				} else {
					massage = "Key entered successfully! The key does not have an inverse matrix, if you use this key it will not be possible to decrypt!";
				}
				System.out.println("key:" + hill.getKey());
				JOptionPane.showMessageDialog(null, massage);
			} catch (Exception e) {
				hill.setKey(null);
				hill.setKeyMatrix(null);
				;
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			showKey();

			break;
		case "3DES":
			if (tripleDes == null) {
				tripleDes = new TripleDES();
			}

			try {
				tripleDes.entedKeyAvailable(key);
				JOptionPane.showMessageDialog(null, messageEnterSuccess);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}

			break;
		case "Vigenere":
			if (vigenere == null) {
				vigenere = new Vigenere();
			}
			vigenere.setKey(key);
			JOptionPane.showMessageDialog(null, messageEnterSuccess);
			break;

		case "AES":
			if (aes == null) {
				aes = new AES();
			}
			try {
				aes.entedKeyAvailable(key);
				JOptionPane.showMessageDialog(null, messageEnterSuccess);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			break;

		default:
			JOptionPane.showMessageDialog(null, "Error enter key!");
			break;
		}
	}

	public void createKey() throws Exception {
		String messageCreateSuccess = "Key created successfully!";
		String mesInfo = " The key is displayed as Base64";
		int sizeKey = 0;
		if (comboBoxKeySize != null)
			sizeKey = Integer.parseInt(comboBoxKeySize.getSelectedItem().toString());

		switch (this.nameAlgorithm) {
		case "DES":
			des = new DES();
			des.createKeyDES(sizeKey);
			JOptionPane.showMessageDialog(null, messageCreateSuccess + mesInfo);
			break;

		case "Hill":
			hill = new Hill();
			hill.createKeyHill(sizeKey);
			JOptionPane.showMessageDialog(null, messageCreateSuccess);
			break;

		case "Vigenere":
			vigenere = new Vigenere();
			vigenere.generateRandomKey();
			JOptionPane.showMessageDialog(null, messageCreateSuccess);
			break;

		case "3DES":
			tripleDes = new TripleDES();
			tripleDes.generateRandomKey(sizeKey);
			JOptionPane.showMessageDialog(null, messageCreateSuccess+ mesInfo); 
			break;
		case "AES":
			aes = new AES();
			aes.generateAESKey(sizeKey);
			JOptionPane.showMessageDialog(null, messageCreateSuccess+ mesInfo);
			break;

		default:
			JOptionPane.showMessageDialog(null, "Error create key!");
			break;
		}
	}

	public void showKey() {
		switch (this.nameAlgorithm) {
		case "DES":
			if (des != null && des.getKey() != null) {
				textKey.setText(Base64.getEncoder().encodeToString(des.getKey().getEncoded()));
			} else {
				textKey.setText("");
			}
			break;

		case "Hill":
			if (hill != null && hill.getKey() != null) {
				textKey.setText(hill.getKey() + "\n\n" + "Ma trận được tạo:\n" + hill.printMatrix(hill.getKeyMatrix()));
			} else {
				textKey.setText("");
			}
			break;

		case "Vigenere":
			if (vigenere != null && vigenere.getKey() != null) {
				System.out.println("Key: " + vigenere.getKey());
				textKey.setText(vigenere.getKey());
			} else {
				textKey.setText("");
			}
			break;
		case "3DES":
			if (tripleDes != null && tripleDes.getKey() != null) {
				textKey.setText(Base64.getEncoder().encodeToString(tripleDes.getKey().getEncoded()));
			} else {
				textKey.setText("");
			}
			break;
		case "AES":
			if (aes != null && aes.getKey() != null) {
				textKey.setText(Base64.getEncoder().encodeToString(aes.getKey().getEncoded()));
			} else {
				textKey.setText("");
			}
			break;

		default:
			textKey.setText("Error show key!");
			break;
		}
	}

	public String exportKeyToBase64(SecretKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public void setTextAreakey(SecretKey key) {
		textKey.setText(exportKeyToBase64(key));
	}

	public SetKeySymmetryPanel(String nameAlgorithm) {
		this.nameAlgorithm = nameAlgorithm;
		this.setTitle("Cài đặt khóa cho thuật toán " + nameAlgorithm);
		getContentPane().setLayout(null);
		this.setSize(600, 480);
		this.setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("SET KEY "+ nameAlgorithm.toUpperCase());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(0, 0, 600, 72);
		getContentPane().add(lblNewLabel);

		JLabel lblSizeKey = new JLabel("Select key size:");
		lblSizeKey.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSizeKey.setBounds(20, 66, 248, 36);
		getContentPane().add(lblSizeKey);

		JButton btnCreaterKey = new JButton("Create key");
		btnCreaterKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createKey();
					showKey();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnCreaterKey.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreaterKey.setBounds(445, 367, 131, 36);
		getContentPane().add(btnCreaterKey);

		JLabel lblNewLabel_1 = new JLabel("Your public key:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(20, 163, 274, 36);
		getContentPane().add(lblNewLabel_1);

		JButton btnNewButton_2 = new JButton("Enter key");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputKey = textKey.getText().trim();
				try {
					enterKey();
				} catch (Exception e2) {

				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(20, 367, 131, 36);
		getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel_2 = new JLabel("You don't have a key yet?");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(269, 367, 165, 36);
		getContentPane().add(lblNewLabel_2);

		comboBoxKeySize = new JComboBox();
		comboBoxKeySize.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxKeySize.setBounds(20, 109, 248, 44);
		getContentPane().add(comboBoxKeySize);
		Border border = BorderFactory.createLineBorder(Color.black, 1);

		textKey = new JTextArea();
		textKey.setBorder(border);
		textKey.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textKey.setBounds(20, 205, 556, 146);
		getContentPane().add(textKey);
		
		JScrollPane scrollPane1 = new JScrollPane(textKey);
		scrollPane1.setBounds(20, 205, 556, 146);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane1, BorderLayout.CENTER);
		
		if (this.nameAlgorithm.equals("DES")) {
			comboBoxKeySize.setModel(new DefaultComboBoxModel(new String[] { "56" }));
		} else if (this.nameAlgorithm.equals("Hill")) {
			comboBoxKeySize.setModel(new DefaultComboBoxModel(new String[] { "2", "3", "4", "5" }));
		} else if (this.nameAlgorithm.equals("3DES")) {
			comboBoxKeySize.setModel(new DefaultComboBoxModel(new String[] { "112", "168" }));
		} else if (this.nameAlgorithm.equals("AES")) {
			comboBoxKeySize.setModel(new DefaultComboBoxModel(new String[] { "128", "192", "256" }));
		} else if (this.nameAlgorithm.equals("Vigenere")) {
			comboBoxKeySize.setVisible(false);
			comboBoxKeySize = null;
			lblSizeKey.setVisible(false);
			lblNewLabel_1.setBounds(20, 163 - 100, 274, 36);
			textKey.setBounds(20, 205 - 100, 556, 127);
			btnNewButton_2.setBounds(20, 367 - 100, 131, 36);
			lblNewLabel_2.setBounds(269, 367 - 100, 165, 36);
			btnCreaterKey.setBounds(445, 367 - 100, 131, 36);
			this.setSize(600, 480 - 100);
			scrollPane1.setBounds(20, 205 - 100, 556, 127);
		}


		
		showKey();
		setVisible(true);
	}
}
