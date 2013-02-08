package com.fibertechcws.cashregister.gui;

import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fibertechcws.cashregister.db.Database;
import com.fibertechcws.cashregister.db.Department;

public class InventoryReport extends JPanel {
	Database db;
	
	
	public InventoryReport(Database db) {
		this.db=db;
		try {
			db.execute("SELECT * FROM Department");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				while(rs.next()) {
					add(new JLabel("Department="+rs.getString(1)+"     Amount="+rs.getString(2)));
				}
			} 
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}

}
