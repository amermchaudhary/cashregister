package com.fibertechcws.cashregister.db;
import java.sql.*;
public class Payout{
	int id;
	String company;
	double amount;
	long date;
	int shift;
	public Payout(int id, long date, String company, double amount, int shift) {
		this.id = id;
		this.date = date;
		this.company = company;
		this.amount = amount;
		this.shift = shift;
	}
	public int getID() {
		return id;
	}
	public int getShiftId() {
		return shift;
	}
	public Date getDate() {
		return new Date(date);
	}
	public String getCompany() {
		return company;
	}
	public double getAmount() {
		return amount;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setShiftId(int id) {
		this.shift = shift;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public boolean equals(Object O) {
		Payout d = (Payout) O;
		if (d.getID() == this.getID()) {
			return true;
		} else {
			return false;
		}
	}
}