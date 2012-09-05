package com.devspark.sidenavigation;

/**
 * Item of side navigation.
 * 
 * @author johnkil
 * 
 */
class SideNavigationItem {

	private int id;
	private int icon;
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
