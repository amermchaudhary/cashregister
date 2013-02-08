package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.util.*;
import com.fibertechcws.cashregister.*;
public class DepartmentHandler {
	Database db;
	public DepartmentHandler(Database db) {
		this.db = db;
	}
	public ArrayList<Department> getDepartments() {
		ArrayList<Department> depts = new ArrayList<Department>();
		try {
			db.execute("SELECT * FROM Department");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				while(rs.next()) {
					depts.add(new Department(rs.getInt("ID"), rs.getString("DepartmentName"), rs.getDouble("ShiftTotals")));
				}
			} else {
				return null;
			}
			return depts;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public Department getDepartment(String name) {
		System.out.println(name);
		try {
			db.execute("SELECT * FROM Department WHERE DepartmentName=\'" + name +"\'");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				rs.next();
				return new Department(rs.getInt("ID"), rs.getString("DepartmentName"), rs.getDouble("ShiftTotals"));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public void updateDepartment(Department dept) {
		try {
			db.execute("UPDATE Department SET  DepartmentName="+dept.name+" WHERE ID="+dept.id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void addDepartment(Department dept) {
		try {
			db.execute("INSERT INTO Department (id, Department, ShiftTotals) VALUES ("+dept.getID()+","+dept.getName()+","+dept.getShiftTotals()+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void addDepartment(String s) {
		try {
			db.execute("INSERT INTO Department (DepartmentName, ShiftTotals) VALUES (\'"+s+"\',0.00)");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void updateDepartments (ArrayList<Department> dal) {
		try {
			for (Department d : dal) {
				db.execute("UPDATE Department SET  DepartmentName=\'"+d.getName()+"\', ShiftTotals="+d.getShiftTotals()+" WHERE ID="+d.getID());
			}
			//db.execute(txt);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void clearAllDepartmentTotals() {
		try {
			db.execute("UPDATE Department SET ShiftTotals=0.00");
		} catch (Exception e) {
			
		}
	}
}