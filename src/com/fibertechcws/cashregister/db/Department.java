package com.fibertechcws.cashregister.db;
public class Department{
	String name;
	int id;
	double shiftTotals;
	public Department(int id, String name, double shiftTotals) {
		this.id = id;
		this.name = name;
		this.shiftTotals = shiftTotals;
	}
	public int getID() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getShiftTotals() {
		return shiftTotals;
	}
	public void setID(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setShiftTotals(double shiftTotals) {
		this.shiftTotals = shiftTotals;
	}
	public boolean equals(Object O) {
		Department d = (Department) O;
		if (d.getID() == this.getID()) {
			return true;
		} else {
			return false;
		}
	}
}