package com.fibertechcws.cashregister.db;
import java.sql.*;
import javax.swing.*;
import java.util.*;
public class ButtonHandler {
	Database db;
	ArrayList<ButtonUpdateListener> bula = new ArrayList<ButtonUpdateListener>();
	public ButtonHandler(Database db) {
		this.db = db;
	}
	public void addButtonUpdateListener(ButtonUpdateListener bul) {
		bula.add(bul);
	}
	public void fireButtonUpdateListenerEvent(int id, String s) {
		for (ButtonUpdateListener bul : bula) {
			bul.updateButton(id, s);
		}
	}
	public void updateButton(JButton jb, int id) {
		try {
			db.execute("UPDATE Button SET ButtonName='"+jb.getText() +"'  WHERE id="+id);
			fireButtonUpdateListenerEvent(id-1, jb.getText());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public ArrayList<JButton> getButtons() {
		ArrayList<JButton> jba = new ArrayList<JButton>(28);
		try {
			db.execute("SELECT * FROM Button");
			ResultSet rs = db.getResultSet();
			for (int i=0; i<28; i++) {
				jba.add(new JButton());
			}
			if (rs != null) {
				int count=0;
				while(rs.next()) {
					jba.set(Integer.parseInt(rs.getString("id")) - 1, new JButton(rs.getString("ButtonName")));
					count++;
				}
				return jba;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("getButtons(): " + e);
		}
		return null;
	}
}