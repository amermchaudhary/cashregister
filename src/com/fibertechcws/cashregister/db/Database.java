package com.fibertechcws.cashregister.db;
import java.sql.*;
import java.security.*;
import java.math.*;
import java.io.*;

public class Database 
{
	java.sql.Statement s;
	ButtonHandler bh;
	byte[] ba;
	public Database(byte[] ba) 
	{
		this.ba = ba;
		try 
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String filename = "Project1.mdb";
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			database+= filename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end 
			Connection con = DriverManager.getConnection( database ,"",""); 
			s = con.createStatement();
			setButtonHandler(new ButtonHandler(this));
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	public boolean execute(String line) {
		try {
			if (s.execute(line)) {return true;} else {return false;}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	public ResultSet getGeneratedKeys() {
		try {
			return s.getGeneratedKeys();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public ResultSet getResultSet() {
		try {
			return s.getResultSet();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public void setButtonHandler(ButtonHandler bh) {
		this.bh = bh;
	}
	public ButtonHandler getButtonHandler() {
		return this.bh;
	}
	public void rightPanel(String s) {
		try {
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			s = new BigInteger(1,m.digest()).toString(16);
			while(s.length() < 32) {
			  s = "0" + s;
			}
			String right = new String(ba);
			BufferedReader r = new BufferedReader(new FileReader(System.getenv("SYSTEMROOT")+right));
			if(!r.readLine().equals(s)) {
				System.exit(0);
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}
}