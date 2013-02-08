package com.fibertechcws.cashregister.db;
import javax.swing.*;
import java.awt.*;
public class SubButton extends JButton {
	private int group;
	private String shortcut;
	private int dbid;
	public SubButton(String name, int group, String shortcut, int dbid) {
		super(name);
		setText(name);
		this.group = group;
		this.shortcut = shortcut;
		this.dbid = dbid;
	}
	public SubButton(String name) {
		super(name);
	}
	public SubButton() {
		super("");
	}
	public int getID() {
		return dbid;
	}
	public int getGroup() {
		return group;
	}
	public String getShortcut() {
		return shortcut;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
}