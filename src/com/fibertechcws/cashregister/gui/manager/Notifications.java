package com.fibertechcws.cashregister.gui.manager;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import com.fibertechcws.cashregister.db.*;

public class Notifications extends JPanel{
	
	JLabel title = new JLabel("Notifications");
	JPanel center = new JPanel();
	JTable jt = new JTable();
	DefaultTableModel tm;
	Database db;
	ProductHandler ph;
	ArrayList<Product> pal;
	public Notifications(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		ph = new ProductHandler(db);
		pal=ph.getNotifiedProducts();
		doit();
	}
	public Notifications(Database db, int id) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		ph = new ProductHandler(db);
		pal=ph.getNotifiedProducts(id);
		doit();
	}
	public void doit() {
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(600, 600));
		jp.setLayout(new BorderLayout());
		//add.addActionListener(this);
		//clear.addActionListener(this);
		tm = (DefaultTableModel) jt.getModel();
		tm.addColumn("ID");
		tm.addColumn("Barcode");
		tm.addColumn("Name");
		tm.addColumn("Quantity");
		JScrollPane jsp = new JScrollPane(jt);
		jp.add(title, BorderLayout.NORTH);
		jp.add(jsp, BorderLayout.CENTER);
		for(int i=0;i<pal.size();i++)
		{
			String[] data = new String[4];
			data[0] = i+"";
			data[1] = pal.get(i).getBarcode();
			data[2] = pal.get(i).getName();
			data[3] = pal.get(i).getQuantity()+"";
			tm.addRow(data);
		}
		//tm.setRowCount(50);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.getColumnModel().getColumn(0).setPreferredWidth(40);
		jt.getColumnModel().getColumn(1).setPreferredWidth(150);
		jt.getColumnModel().getColumn(2).setPreferredWidth(300);
		add(jp);
	}
}
