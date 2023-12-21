package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import asymmetric.RSA;
import hash.MD5;
import hash.SHA;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.BorderLayout;
import java.awt.Button;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class SignaturePanelUI extends JPanel {
	private File file;
	JComboBox comboBoxTypeHash;
	public SignaturePanelUI() {
		setLayout(null);
		setBounds(0, 0, 800, 750);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Chữ ký điện tử");
		lblNewLabel_1_1_1.setBounds(41, 10, 564, 60);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		add(lblNewLabel_1_1_1);
		
		JLabel lblNameFileSource = new JLabel("Vui lòng chọn file");
		lblNameFileSource.setHorizontalAlignment(SwingConstants.LEFT);
		lblNameFileSource.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNameFileSource.setBounds(41, 165, 541, 40);
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		lblNameFileSource.setBorder(border);
		add(lblNameFileSource);
		
		JButton btnChooseFileSource = new JButton("Chọn tệp");
		btnChooseFileSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				int response = fileChooser.showOpenDialog(null);
				if(response == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					fileName = file.getName();
					lblNameFileSource.setText(fileName);
				}else if(response == JFileChooser.CANCEL_OPTION) {
					return;
				}else if(response == JFileChooser.ERROR_OPTION) {
					return;
				}
			}
		});
		btnChooseFileSource.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnChooseFileSource.setBounds(603, 165, 149, 40);
		add(btnChooseFileSource);
		
		JTextArea textAreaInputHash = new JTextArea();
		textAreaInputHash.setLineWrap(true);
		textAreaInputHash.setFont(new Font("Dialog", Font.PLAIN, 15));
		textAreaInputHash.setBounds(41, 275, 541, 150);
		add(textAreaInputHash);
		
		JLabel lblNewLabel = new JLabel("Mã Hash:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(41, 236, 349, 33);
		add(lblNewLabel);
		
		JLabel lblKtQu = new JLabel("Kết quả xác minh:");
		lblKtQu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKtQu.setBounds(41, 520, 349, 33);
		add(lblKtQu);
		
		JTextArea textAreaOutput = new JTextArea();
		textAreaOutput.setLineWrap(true);
		textAreaOutput.setFont(new Font("Dialog", Font.PLAIN, 15));
		textAreaOutput.setBounds(41, 559, 541, 150);
		add(textAreaOutput);
		
		JScrollPane scrollPane9 = new JScrollPane(textAreaInputHash);
		scrollPane9.setBounds(41, 275, 541, 150);
		scrollPane9.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPane10 = new JScrollPane(textAreaOutput);
		scrollPane10.setBounds(41, 559, 541, 150);
		scrollPane10.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane9, BorderLayout.CENTER);
		add(scrollPane10, BorderLayout.CENTER);
		
		Button buttonVerify = new Button("Xác minh");
		buttonVerify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typeHash = comboBoxTypeHash.getSelectedItem().toString();
				String type = typeHash;
				if(typeHash.contains("SHA")) {
					typeHash = "SHA";
				}
				String resultHashFile = "";

				switch (typeHash) {
				case "MD5":
					MD5 md5 = new MD5();
					if(file == null) {
						System.out.println("File not exists!");
						JOptionPane.showMessageDialog(null, "File not exists!");
					}else {
						if(file.isFile()) {
							try {
								resultHashFile = md5.hash(file.getAbsolutePath());
							} catch (Exception e2) {
								e2.getStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "This is not file!");
						}
					}
					break;
				case "SHA":
					SHA sha = new SHA();
					if(file == null) {
						System.out.println("File not exists!");
						JOptionPane.showMessageDialog(null, "File not exists!");
					}else {
						System.out.println("File exists!");
						if(file.isFile()) {
							try {
								resultHashFile = sha.hashFile(file.getAbsolutePath(), type);
							} catch (Exception e2) {
								e2.getStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(null, "This is not file!");
						}
					}
					break;
				default: JOptionPane.showMessageDialog(null, "Error type hash!");
					break;
				}
				
				String hashCodeInput = textAreaInputHash.getText();
				if(hashCodeInput.equals(resultHashFile)) {
					textAreaOutput.setText("Chữ ký vẹn toàn không bị thay đổi");
				}else {
					textAreaOutput.setText("Chữ ký bị thay đổi");
				}
				
			}
		});
		buttonVerify.setFont(new Font("Dialog", Font.BOLD, 13));
		buttonVerify.setBackground(new Color(203, 163, 255));
		buttonVerify.setBounds(41, 463, 149, 41);
		add(buttonVerify);
		
		comboBoxTypeHash = new JComboBox();
		comboBoxTypeHash.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBoxTypeHash.setModel(new DefaultComboBoxModel(new String[] {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512/224", "SHA-512/256"}));
		comboBoxTypeHash.setBounds(41, 99, 274, 40);
		add(comboBoxTypeHash);
	}
}
