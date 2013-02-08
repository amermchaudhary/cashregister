package com.fibertechcws.cashregister.db;
import java.sql.*;
import com.fibertechcws.cashregister.*;
import java.util.ArrayList;
public class ProductHandler {
	Database db;
	public ProductHandler(Database db) {
		this.db = db;
	}
	public Product getProduct(String code) {
		try {
			boolean notify, taxable;
			db.execute("SELECT * FROM Product WHERE Barcode=\'" + code+"\'");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				rs.next();
				return new Product(rs.getInt("ID"), rs.getString("Barcode"), rs.getString("ProductName"), rs.getInt("Quantity"), rs.getBoolean("Notification"), rs.getInt("LowAmount"), rs.getBoolean("Taxable"), rs.getInt("Department"), rs.getDouble("Price"), rs.getBoolean("PerUnit"));
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public ArrayList<Product> getNotifiedProducts() {
		try {
			ArrayList<Product> pal = new ArrayList<Product>();
			db.execute("SELECT * FROM Product WHERE Notification=" + Boolean.parseBoolean("true")+" AND Quantity <= LowAmount");
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				while(rs.next()) {
					pal.add(new Product(rs.getInt("ID"), rs.getString("Barcode"), rs.getString("ProductName"), rs.getInt("Quantity"), rs.getBoolean("Notification"), rs.getInt("LowAmount"), rs.getBoolean("Taxable"), rs.getInt("Department"), rs.getDouble("Price"), rs.getBoolean("PerUnit")));
				}
				return pal;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public ArrayList<Product> getNotifiedProducts(int dept) {
		try {
			ArrayList<Product> pal = new ArrayList<Product>();
			db.execute("SELECT * FROM Product WHERE Notification=" + Boolean.parseBoolean("true")+" AND Quantity <= LowAmount AND Department="+dept);
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				while(rs.next()) {
					pal.add(new Product(rs.getInt("ID"), rs.getString("Barcode"), rs.getString("ProductName"), rs.getInt("Quantity"), rs.getBoolean("Notification"), rs.getInt("LowAmount"), rs.getBoolean("Taxable"), rs.getInt("Department"), rs.getDouble("Price"), rs.getBoolean("PerUnit")));
				}
				return pal;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public boolean updateProduct(Product product) {
		try {
			db.execute("UPDATE Product SET Barcode=\'"+product.getBarcode()+"\', ProductName=\'"+product.getName()+"\', Quantity="+product.getQuantity()+", Notification="+product.getNotification()+", LowAmount="+product.getLow()+", Taxable="+product.isTaxable()+", Department="+product.getDepartment()+", Price="+product.getPrice()+", PerUnit="+product.getPerUnit()+"  WHERE id="+product.getID());
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	public boolean updateProductQuantity(Product product) {
		try {
			db.execute("UPDATE Product SET Quantity=Quantity - "+product.getQuantity()+" WHERE id="+product.getID());
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	public boolean addProduct(Product product) {
		try {
			db.execute("INSERT INTO Product (Barcode, ProductName, Quantity, notification, lowamount, taxable, department, price, perunit) VALUES (\'"+product.getBarcode()+"\' , \'"+product.getName()+"\' ,"+product.getQuantity()+","+product.getNotification()+","+product.getLow()+","+product.isTaxable()+","+product.getDepartment()+","+product.getPrice()+", " + product.getPerUnit() + ")");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
			return false;
		}
	}	
}