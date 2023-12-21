package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.JTextArea;
import java.awt.TextArea;

public class App extends JFrame {

	public App() {
		getContentPane().setBackground(new Color(240, 240, 240));
		this.setTitle("An toàn và bảo mật thông tin");
		this.setSize(800, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setUI(new CustomTabbedPaneUI());
		tabbedPane.setBounds(0, 0, 786, 763);

		JPanel panelEncodeSymmetry = new SymmetryPanelUI();
		tabbedPane.addTab("  Symmetry  ", panelEncodeSymmetry);
		
		JPanel panelEncodeAsymmetrical = new AsymmetricPanelUI();
		tabbedPane.addTab("Asymmetrical", panelEncodeAsymmetrical);
		
		JPanel panelHash = new HashPanelUI();
		panelHash.setBackground(new Color(128, 128, 192));
		tabbedPane.addTab("      Hash      ", panelHash);
		panelHash.setLayout(null);
		
		JPanel panelSignature = new SignaturePanelUI();
		tabbedPane.addTab("  Signature  ", panelSignature);
		panelHash.setLayout(null);
		
		getContentPane().add(tabbedPane);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		App app = new App();
	}
}

class CustomTabbedPaneUI extends BasicTabbedPaneUI {
	private Color selectedTabColor = new Color(101, 0, 231); //
    private Color unselectedTabColor = new Color(151, 71, 255); 
    
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		if (isSelected) {
			g.setColor(selectedTabColor);
		} else {
			g.setColor(unselectedTabColor); 
		}
		g.fillRect(x, y, w, h);
	}
	
	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
			Rectangle textRect, boolean isSelected) {
		if (isSelected) {
			g.setColor(Color.white); 
		} else {
			g.setColor(Color.white);
		}
		g.setFont(font);
		g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
	}
}
