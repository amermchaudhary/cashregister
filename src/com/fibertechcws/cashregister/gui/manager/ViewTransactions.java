package com.fibertechcws.cashregister.gui.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fibertechcws.cashregister.db.ButtonHandler;
import com.fibertechcws.cashregister.db.Database;
import com.fibertechcws.cashregister.db.Product;
import com.fibertechcws.cashregister.db.Transaction;
import com.fibertechcws.cashregister.db.TransactionHandler;

public class ViewTransactions extends JPanel implements ActionListener {
	
	JLabel title = new JLabel("Transactions");
	JPanel center = new JPanel();
	JTextField tid=new JTextField();
	JLabel lt=new JLabel();
	JLabel lid=new JLabel("Transaction ID:");
	JButton gettrans=new JButton("Get Transaction");
	Database db;
	TransactionHandler th;
	public ViewTransactions(Database db){
		super();
		this.db = db;
		
		title.setFont(new Font("Arial", Font.BOLD, 25));
		th=new TransactionHandler(db);
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(500, 500));
		jp.setLayout(new BorderLayout());
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 550));
		center.setLayout(new GridLayout(10,2));
		center.add(lid);
		center.add(tid);
		gettrans.addActionListener(this);
		center.add(gettrans);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==gettrans)
		{
			Transaction t=th.getTransaction(Integer.parseInt(tid.getText()));
			ArrayList<Product> plist=t.getProductList();
			//date,products,total,paid
			center.add(new JLabel("Date:"+t.getDate()));
			center.add(new JLabel());
			center.add(new JLabel("Total:"+t.getTotal()));
			center.add(new JLabel());
			center.add(new JLabel("Paid:"+t.getPaid()));
			center.add(new JLabel());
			for(int i=0;i<plist.size();i++)
			{
				center.add(new JLabel("Item:"+plist.get(i)));
				center.add(new JLabel());
			}
			
			
		}
	}

}
