package com.fibertechcws.cashregister.db;
import java.sql.*;
import javax.swing.*;
import java.util.*;
public class SubButtonHandler {
	Database db;
	public SubButtonHandler(Database db) {
		this.db = db;
	}
	public void updateButton(SubButton jb, int id) {
		try {
			db.execute("UPDATE SubButton SET buttonname=\'"+jb.getText() +"\', Shortcut=\'"+jb.getShortcut()+"\', ButtonGroup="+jb.getGroup()+" WHERE id="+jb.getID());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public ArrayList<SubButton> getButtons(int group) {
		ArrayList<SubButton> jba = new ArrayList<SubButton>(27);
		for (int i=0; i<27; i++) {
			jba.add(new SubButton());
		}
		try {
			db.execute("SELECT * FROM SubButton WHERE ButtonGroup="+group);
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				int count=0;
				while(rs.next() & count < 27) {
					jba.set(count, new SubButton(rs.getString("ButtonName"), group, rs.getString("Shortcut"), Integer.parseInt(rs.getString("ID"))));
					count++;
					//System.out.println(rs.getString("ButtonName") + " " + group + " " + rs.getString("Shortcut"));
				}
				for(SubButton sb : jba) {
					db.execute("INSERT INTO SubButton(ButtonName, Shortcut, ButtonGroup) VALUES(\' \', \'1\', "+group+")");
					/*if (sb.getShortcut() == null) {
						if (db.execute("INSERT INTO SubButton(ButtonName, Shortcut, ButtonGroup) VALUES(\' \', \'1\', "+group+")")) {
							System.out.println("Failed");
						}
					}*/
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