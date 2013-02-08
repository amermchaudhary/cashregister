package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.fibertechcws.cashregister.db.Product;
public class Transaction {
	ArrayList<Product> items = new ArrayList<Product>();
	int id;
	double total, paid;
	Date date;
	Time time;
	public Transaction(int id, Date date, Time time, ArrayList<Product> items, double total, double paid) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.items = items;
		this.total = total;
		this.paid = paid;
	}
	public ArrayList<Product> getProductList() {
		return items;
	}
	public int getID() {
		return id;
	}
	public double getTotal() {
		return total;
	}
	public double getPaid() {
		return paid;
	}	
	public Date getDate() {
		return date;
	}
	public Time getTime() {
		return time;
	}
	public static ArrayList<Product> getProducts(String s) {
		ArrayList<Product> al = new ArrayList<Product>();
		StringTokenizer st = new StringTokenizer(s, "|");
		while(st.hasMoreTokens()) {
			String line = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(line, ",");
			al.add(new Product(Integer.parseInt(st.nextToken()),st.nextToken(), st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken())));
		}
		return al;
	}
	public String productString() {
		String s = "";
		for (Product p : items) {
			if(p.isTaxable()) {
				s += p.getID()+","+p.getBarcode()+","+p.getName()+","+p.getQuantity()+",1,"+p.getPrice()+"|";
			} else {
				s += p.getID()+","+p.getBarcode()+","+p.getName()+","+p.getQuantity()+",0,"+p.getPrice()+"|";
			}
		}
		return s;
	}
}