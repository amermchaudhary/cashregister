package com.fibertechcws.cashregister.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PayoutHandler {
	Database db;
	//(int id, Date date, String company, double amount)
	public PayoutHandler(Database db) {
		this.db=db;
	}
	
	public void addPayout(Payout p)
	{
		long time = (long) (System.currentTimeMillis() / 1000);
		try {
			db.execute("INSERT INTO Payout (PayoutDate, Company ,Amount, Shift) VALUES ("+time+", \'"+p.getCompany()+"\', "+p.getAmount()+", "+p.getShiftId()+")");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public ArrayList<Payout> getPayouts(String company)
	{
		ArrayList<Payout> plist=new ArrayList<Payout>();
		try {
			db.execute("Select * from Payout where company=\'"+company+"\'");
			ResultSet rs = db.getResultSet();
			while(rs.next()){
				plist.add(new Payout(rs.getInt("ID"),Long.parseLong(rs.getString("PayoutDate")),rs.getString("Company"),Double.parseDouble(rs.getString("Amount")), rs.getInt("Shift")));
			} 
		return plist;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public ArrayList<Payout> getPayouts(int shift)
	{
		ArrayList<Payout> plist=new ArrayList<Payout>();
		try {
			db.execute("Select * from Payout where Shift="+shift);
			ResultSet rs = db.getResultSet();
			while(rs.next()){
				plist.add(new Payout(rs.getInt("ID"),Long.parseLong(rs.getString("PayoutDate")),rs.getString("Company"),Double.parseDouble(rs.getString("Amount")), rs.getInt("Shift")));
			} 
		return plist;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public Payout getPayout(int id)
	{
		try {
			db.execute("Select * from Payout where id="+id);
			ResultSet rs = db.getResultSet();
			rs.next();
			return new Payout(rs.getInt("ID"),Long.parseLong(rs.getString("PayoutDate")),rs.getString("Company"),Double.parseDouble(rs.getString("Amount")), rs.getInt("Shift"));
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	

}
