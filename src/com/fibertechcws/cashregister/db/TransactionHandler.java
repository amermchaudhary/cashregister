package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.util.ArrayList;

import com.fibertechcws.cashregister.*;
public class TransactionHandler {
	Database db;
	public TransactionHandler(Database db) {
		this.db = db;
	}
	public Transaction getTransaction(int tid) {
		try {
			boolean notify, taxable;
			db.execute("SELECT * FROM Transaction WHERE ID=\'" + tid+"\'");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				rs.next();
				return new Transaction(rs.getInt("ID"), rs.getDate("TDate"), rs.getTime("TTime"), Transaction.getProducts(rs.getString("Products")), rs.getDouble("Total"), rs.getDouble("Paid"));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public void addTransaction(Transaction t) {
		try {
			ShiftTotals.shifttotals(t, db);
			db.execute("INSERT INTO Transaction (TDate, Products, Total, Paid) VALUES (=Now(),\'"+t.productString()+"\',"+t.getTotal()+","+t.getPaid()+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}	
	 
}