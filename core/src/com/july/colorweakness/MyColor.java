package com.july.colorweakness;

public enum MyColor {
	WHITE("��ɫ", "#FFFFFF", 0), BLACK("��ɫ", "#000000", 1), BLUE("��ɫ", "#0000FF",
			2), BROWN("��ɫ", "#A0522D", 3), CYAN("��ɫ", "#00FFFF", 4), GOLD("��ɫ",
			"#FFD700", 5), GRAY("��ɫ", "#808080", 6), GREEN("��ɫ", "#008000", 7), LAWNGREEN(
			"����ɫ", "#7CFC00", 8), MAROON("��ɫ", "#800000", 9), ORANGE("��ɫ",
			"#FFA500", 10), PINK("��ɫ", "#FFC0CB", 11), PURPLE("��ɫ", "#800080",
			12), RED("��ɫ", "#FF0000", 13), SILVER("��ɫ", "#C0C0C0", 14), WHEAT(
			"��ɫ", "#f5deb3", 15), YELLOW("��ɫ", "#FFFF00", 16);

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
