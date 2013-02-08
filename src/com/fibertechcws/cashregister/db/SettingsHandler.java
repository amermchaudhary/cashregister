package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.util.*;
import com.fibertechcws.cashregister.*;
public class SettingsHandler {
	Database db;
	public SettingsHandler(Database db) {
		this.db = db;
	}
	public ArrayList<String> getSettings() {
		ArrayList<String> settings = new ArrayList<String>();
		try {
			db.execute("SELECT * FROM Settings");
			ResultSet rs = db.getResultSet();
			if (rs != null && rs.next()) {
				settings.add(rs.getString("StoreName"));
				settings.add(rs.getString("StoreAddress"));
				settings.add(rs.getString("StoreCSZ"));
				settings.add(rs.getString("StorePhone"));
				settings.add(rs.getString("Tax"));
				settings.add(rs.getString("ProductKey"));
			} else {
				return null;
			}
			return settings;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}	
	public void update(String s) {
		try {
			db.execute(s);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/*public void addDepartment(Department dept) {
		try {
			db.execute("INSERT INTO Department (id, Department, ShiftTotals) VALUES ("+dept.getID()+","+dept.getName()+","+dept.getShiftTotals()+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void updateDepartments (ArrayList<Department> dal) {
		try {
			for (Department d : dal) {
				db.execute("UPDATE Department SET  ShiftTotals="+d.getShiftTotals()+" WHERE ID="+d.getID());
			}
			//db.execute(txt);
		} catch (Exception e) {
			System.out.println(e);
		}
	}*/
}