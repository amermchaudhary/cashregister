package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.util.ArrayList;

import com.fibertechcws.cashregister.*;
public class ShiftHandler {
	Database db;
	public ShiftHandler(Database db) {
		this.db = db;
	}
	public Shift getCurrentShift() {
		try {
			db.execute("SELECT * FROM Shift WHERE Active=" + true +"");
			ResultSet rs = db.getResultSet();
			if (rs != null && rs.next()) {
				return new Shift(rs.getInt("ID"), rs.getString("Cashier"), rs.getLong("Start"), (long) 0, rs.getDouble("Total"), rs.getDouble("Tax"), null);
			} else {
				System.out.println("Test1");
				String cashier = javax.swing.JOptionPane.showInputDialog("Please enter the Cashier Name");
				if (cashier.equals("") || cashier == null) {
					return null;
				} else {
					db.execute("INSERT INTO Shift (Cashier, Start, End, Total, Tax, Active) VALUES (\'"+cashier+"\', "+(long) (System.currentTimeMillis() / 1000)+", "+ (long) 0+"," +(long) 0+", "+(long) 0+", "+Boolean.valueOf("true")+")");
					db.execute("SELECT * FROM Shift WHERE Active=" + true +"");
					ResultSet rs1 = db.getResultSet();
					if (rs1 != null && rs1.next()) {
						return new Shift(rs1.getInt("ID"), rs1.getString("Cashier"), rs1.getLong("Start"), (long) 0, rs1.getDouble("Total"), rs1.getDouble("Tax"), null);
					} else {
						return null;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public Shift closeShift(String depts, String cashier, Shift s) {
		try {
			db.execute("UPDATE Shift SET DepartmentTotals=\'"+depts+"\', End="+(long) (System.currentTimeMillis() / 1000)+", [Active]="+Boolean.valueOf("false")+" WHERE id="+s.getID());
			db.execute("INSERT INTO Shift (Cashier, Start, End, Total, Tax, Active) VALUES (\'"+cashier+"\', "+(long) (System.currentTimeMillis() / 1000)+", "+ (long) 0+"," +(long) 0+"," +(long) 0+", "+Boolean.valueOf("true")+")");
			db.execute("SELECT * FROM Shift WHERE Active=" + true +"");
			ResultSet rs1 = db.getResultSet();
			if (rs1 != null && rs1.next()) {
				return new Shift(rs1.getInt("ID"), rs1.getString("Cashier"), rs1.getLong("Start"), (long) 0, rs1.getDouble("Total"), rs1.getDouble("Tax"), null);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public void updateTotal(Shift s) {
		try {
			db.execute("UPDATE Shift SET Total="+s.getTotal()+", Tax="+s.getTax()+" WHERE id="+s.getID());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public double getCurrentTotal(Shift s) {
		try {
			db.execute("SELECT Total FROM Shift WHERE Active=" + true +"");
			ResultSet rs = db.getResultSet();
			if (rs != null && rs.next()) {
				return rs.getDouble("Total");
			} else {
				System.out.println("Failed to get shift totals from the database");
				return s.getTotal();
			}
		} catch (Exception e) {
			System.out.println(e);
			return s.getTotal();
		}
	}
}