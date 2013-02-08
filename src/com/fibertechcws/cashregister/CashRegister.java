package com.fibertechcws.cashregister;
import com.fibertechcws.cashregister.db.*;
import com.fibertechcws.cashregister.gui.*;
public class CashRegister {
	public static void main (String[] sa) {
		Database db=new Database(ch().getBytes());
		CashRegister cr = new CashRegister(db);
	}
	public CashRegister(Database db) {
		CR cr = new CR(db);
	}
	public static String ch() {
		String ch = "2f71756d6c5b6125221d525e174c5810444a48";
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for( int i=0; i<ch.length()-1; i+=2 ){
				String output = ch.substring(i, (i + 2));
				int d = Integer.parseInt(output, 16);
				d = d+i;
				sb.append((char)d);
				temp.append(d);
		}
		return sb.toString();
	}
}