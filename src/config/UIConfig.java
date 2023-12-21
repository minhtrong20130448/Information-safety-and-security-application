package config;

import java.awt.Color;

public class UIConfig {
	private final Color navigationBgColor = new Color(236, 219, 255);
	private final Color menuNavigationColor = new Color(221, 120, 254);
	private final Color subMenuNavigationColor = new Color(236, 219, 255);
	private final Color primaryButtonColor = new Color(203, 163, 255);
	
	public UIConfig() {
	}
	
	public Color getNavigationBgColor() {
		return navigationBgColor;
	}
	public Color getMenunavigationColor() {
		return menuNavigationColor;
	}
	public Color getSubmenunavigationColor() {
		return subMenuNavigationColor;
	}
	public Color getPrimaryButtonColor() {
		return primaryButtonColor;
	}
	
}
