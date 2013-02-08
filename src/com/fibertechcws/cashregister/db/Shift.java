package com.fibertechcws.cashregister.db;

import java.sql.*;
import java.util.*;

public class Shift {
	double total, tax;
	int id;
	String cashier;
	long start, end;
	ArrayList<Department> departments;
	public Shift (int id, String cashier, long start, long end, double total, double tax, ArrayList<Department> departments) {
		this.id = id;
		this.cashier = cashier;
		this.start = start;
		this.end = end;
		this.total = total;
		this.tax = tax;
		this.departments = departments;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public int getID() {
		return this.id;
	}
	public String getCashier() {
		return this.cashier;
	}
	public long getStartTime() {
		return this.start;
	}
	public long getEndTime() {
		return this.end;
	}
	public double getTotal() {
		return this.total;
	}
	public double getTax() {
		return this.tax;
	}
	public ArrayList<Department> getDepartments() {
		return this.departments;
	}
}
