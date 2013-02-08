package com.fibertechcws.cashregister.db;

import java.sql.ResultSet;
import java.util.ArrayList;



public class ShiftTotals {

	static DepartmentHandler dh;
	static int d;
	static ArrayList<Product> p;
	public static void shifttotals(Transaction t,Database db)
	{
		try{
		p=t.getProductList();
		for(int i=0;i<p.size();i++)
		{
			d=p.get(i).getDepartment();
			db.execute("SELECT * FROM Department where id="+d);
			ResultSet rs = db.getResultSet();
			if (rs != null) {
				db.execute("Update Department set ShiftTotals="+(rs.getInt(2)+p.get(i).getPrice())+" Where id="+d);
			}
		}
		}
		catch(Exception e)
		{}
		
	}
}
