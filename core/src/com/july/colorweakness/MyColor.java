package com.july.colorweakness;

public enum MyColor {
	WHITE("白色", "#FFFFFF", 0), BLACK("黑色", "#000000", 1), BLUE("蓝色", "#0000FF",
			2), BROWN("赭色", "#A0522D", 3), CYAN("青色", "#00FFFF", 4), GOLD("金色",
			"#FFD700", 5), GRAY("灰色", "#808080", 6), GREEN("绿色", "#008000", 7), LAWNGREEN(
			"草绿色", "#7CFC00", 8), MAROON("栗色", "#800000", 9), ORANGE("橙色",
			"#FFA500", 10), PINK("粉色", "#FFC0CB", 11), PURPLE("紫色", "#800080",
			12), RED("红色", "#FF0000", 13), SILVER("银色", "#C0C0C0", 14), WHEAT(
			"米色", "#f5deb3", 15), YELLOW("黄色", "#FFFF00", 16);

	private String name;
	private String rgb;
	private int index;

	private MyColor(String name, String rgb, int index) {
		this.name = name;
		this.rgb = rgb;
		this.index = index;
	}

	public static String getName(int index) {
		for (MyColor c : MyColor.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	public static String getRGB(int index) {
		for (MyColor c : MyColor.values()) {
			if (c.getIndex() == index) {
				return c.rgb;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getRGB() {
		return rgb;
	}

	public int getIndex() {
		return index;
	}
}
