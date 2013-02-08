package com.fibertechcws.cashregister.db;
public class Product{
	String barcode, name;
	int id, low, department, quantity;
	double price;
	boolean notification, taxable, perunit;
	public Product(int id, String barcode, String name, int quantity, boolean notification, int low, boolean taxable, int department, double price, boolean perunit) {
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
		this.notification = notification;
		this.low = low;
		this.taxable = taxable;
		this.department = department;
		this.price = price;
		this.perunit = perunit;
	}
	public Product(int id, String barcode, String name, int quantity, int taxable, double price) {
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
		this.notification = false;
		this.low = 0;
		if (taxable == 1) {
			this.taxable = true;
		} else {
			this.taxable = false;
		}
		this.department = 0;
		this.price = price;
	}
	public void setProduct(int id, String barcode, String name, int quantity, boolean notification, int low, boolean taxable, int department, double price, boolean perunit) {
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.quantity = quantity;
		this.notification = notification;
		this.low = low;
		this.taxable = taxable;
		this.department = department;
		this.price = price;
		this.perunit = perunit;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getID() {
		return id;
	}
	public String getBarcode() {
		return barcode;
	}
	public String getName() {
		return name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean getNotification() {
		return notification;
	}
	public int getLow() {
		return low;
	}
	public boolean isTaxable() {
		return taxable;
	}
	public int getDepartment() {
		return department;
	}
	public double getPrice() {
		return price;
	}
	public boolean getPerUnit() {
		return perunit;
	}
	public boolean equals(Object O) {
		Product p = (Product) O;
		if (p.getID() == this.getID()) {
			return true;
		} else {
			return false;
		}
	}
}