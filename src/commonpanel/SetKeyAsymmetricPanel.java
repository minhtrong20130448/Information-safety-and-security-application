package commonpanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.TextArea;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SetKeyAsymmetricPanel extends JFrame {
	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	JComboBox comboBoxKeySize;
	JTextArea textAreaPublicKey;
	JTextArea textAreaPrivateKey;

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKeyBase64() {
		if (publicKey != null)
			return Base64.getEncoder().encodeToString(publicKey.getEncoded());
		return null;
	}

	public String getPrivateKeyString() {
		if (privateKey != null)
			return Base64.getEncoder().encodeToString(privateKey.getEncoded());
		return null;
	}

	public void createRSAKey() {
		int keySize = Integer.parseInt(comboBoxKeySize.getSelectedItem().toString());
		KeyPairGenerator keyGenerator = null;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(keySize);
			keyPair = keyGenerator.generateKeyPair();
			publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			JOptionPane.showMessageDialog(this, "Key generated successfully! The key will be displayed as Base64");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	public void enterRSAKey(String pubKey, String priKey) {
		try {
			byte[] publicKeyBytes = Base64.getDecoder().decode(pubKey);
			byte[] privateKeyBytes = Base64.getDecoder().decode(priKey);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

			keyPair = new KeyPair(publicKey, privateKey);
			this.publicKey = (RSAPublicKey) keyPair.getPublic();
			this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	public void showKey() {
		if (getPublicKeyBase64() != null) {
			textAreaPublicKey.setText(getPublicKeyBase64());
		}
		if (getPrivateKeyString() != null) {
			textAreaPrivateKey.setText(getPrivateKeyString());
		}
	}

	public SetKeyAsymmetricPanel() {
		getContentPane().setLayout(null);
		this.setSize(600, 650);
		this.setLocationRelativeTo(null);
		this.setTitle("Cài đặt khóa");
		JLabel lblNewLabel = new JLabel("SET KEY RSA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(0, 0, 600, 72);
		getContentPane().add(lblNewLabel);

		JButton btnCreaterKey = new JButton("Create key");
		btnCreaterKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRSAKey();
				showKey();
			}
		});
		btnCreaterKey.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreaterKey.setBounds(445, 550, 131, 36);
		getContentPane().add(btnCreaterKey);

		JLabel lblNewLabel_1 = new JLabel("Your public key:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(20, 163, 274, 36);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Your private key:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(20, 353, 274, 36);
		getContentPane().add(lblNewLabel_1_1);

		Border border = BorderFactory.createLineBorder(Color.black, 1);
		textAreaPublicKey = new JTextArea();
		textAreaPublicKey.setLineWrap(true);
		textAreaPublicKey.setBorder(border);
		textAreaPublicKey.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaPublicKey.setBounds(20, 210, 556, 130);
		getContentPane().add(textAreaPublicKey);

		textAreaPrivateKey = new JTextArea();
		textAreaPrivateKey.setLineWrap(true);
		textAreaPrivateKey.setBorder(border);
		textAreaPrivateKey.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaPrivateKey.setBounds(20, 399, 556, 130);
		getContentPane().add(textAreaPrivateKey);

		JScrollPane scrollPane1 = new JScrollPane(textAreaPublicKey);
		scrollPane1.setBounds(20, 210, 556, 130);
		scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane10 = new JScrollPane(textAreaPrivateKey);
		scrollPane10.setBounds(20, 399, 556, 130);
		scrollPane10.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane1, BorderLayout.CENTER);
		add(scrollPane10, BorderLayout.CENTER);
		
		
		JButton btnNewButton_2 = new JButton("Enter key");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputPublicKey = textAreaPublicKey.getText().trim();
				String inputPrivateKey = textAreaPrivateKey.getText().trim();
				try {
					enterRSAKey(inputPublicKey, inputPrivateKey);
					JOptionPane.showMessageDialog(null, "Key entered successfully!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				showKey();

			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(20, 550, 131, 36);
		getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel_2 = new JLabel("You don't have a key yet?");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(269, 550, 165, 36);
		getContentPane().add(lblNewLabel_2);

		comboBoxKeySize = new JComboBox();
		comboBoxKeySize.setModel(new DefaultComboBoxModel(new String[] { "1024", "2048", "3072", "4096" }));
		comboBoxKeySize.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxKeySize.setBounds(20, 109, 248, 44);
		getContentPane().add(comboBoxKeySize);

		JLabel lblNewLabel_1_2 = new JLabel("Select key size:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(20, 66, 274, 36);
		getContentPane().add(lblNewLabel_1_2);

		this.showKey();
		setVisible(true);
	}
}
