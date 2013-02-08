package com.fibertechcws.cashregister.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.fibertechcws.cashregister.gui.register.*;
import com.fibertechcws.cashregister.db.*;
public class CR extends JFrame {
	JPanel clp = new JPanel(new CardLayout());
	Database db;
	ManagerPanel mp;
	public CR(Database db) {
		super();
		this.db = db;
		//setSize(500,500);
		clp.add(new RegisterPanel(db, this), "Register");
		mp = new ManagerPanel(this, db);
		clp.add(mp, "Manager");
		clp.add(new InventoryReport(db), "IReport");
		((CardLayout)clp.getLayout()).show(clp, "Register");
		setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		JPanel jp = new JPanel();
		jp.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		JButton jb = new JButton("Hello");
		setUndecorated(true);
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
		jp.setLayout(new BorderLayout());
		jp.add(new TopPanel(this), BorderLayout.NORTH);
		jp.add(clp, BorderLayout.CENTER);
		jp.setBackground(Color.BLACK);
		getContentPane().add(jp, BorderLayout.CENTER);
		setVisible(true);
	}
	public void show(String name) {
		((CardLayout)clp.getLayout()).show(clp, name);
		mp.myFocus();
	}
}