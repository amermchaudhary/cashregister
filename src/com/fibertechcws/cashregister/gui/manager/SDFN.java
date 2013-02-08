package com.fibertechcws.cashregister.gui.manager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import com.fibertechcws.cashregister.db.*;

public class SDFN extends JPanel implements ActionListener{
	
	JLabel title = new JLabel("Select a Department to view Notifications");
	JPanel center = new JPanel();
	JTable jt = new JTable();
	DefaultTableModel tm;
	Database db;
	DepartmentHandler dh;
	ArrayList<Department> dal;
	JButton submit = new JButton("View Notifications");
	JButton submitAll = new JButton("View All Notifications");
	public SDFN(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		dh = new DepartmentHandler(db);
		dal=dh.getDepartments();
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(600, 600));
		jp.setLayout(new BorderLayout());
		//add.addActionListener(this);
		//clear.addActionListener(this);
		tm = (DefaultTableModel) jt.getModel();
		tm.addColumn("ID");
		tm.addColumn("Department Name");
		JScrollPane jsp = new JScrollPane(jt);
		jp.add(title, BorderLayout.NORTH);
		jp.add(jsp, BorderLayout.CENTER);
		for(int i=0;i<dal.size();i++)
		{
			String[] data = new String[2];
			data[0] = dal.get(i).getID() + "";
			data[1] = dal.get(i).getName();
			tm.addRow(data);
		}
		//tm.setRowCount(50);
		//jt.setAutoResizeMode(JTable.AUTO_RESIZE_ON);
		jt.getColumnModel().getColumn(0).setPreferredWidth(40);
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2,1));
		jp1.add(submit);
		jp1.add(submitAll);
		submit.addActionListener(this);
		submitAll.addActionListener(this);
		jp.add(jp1, BorderLayout.SOUTH);
		add(jp);
	}
	public void actionPerformed(ActionEvent ae) {
		removeAll();
		
		if (ae.getSource() == submitAll) {
			add(new Notifications(db));
		} else if(jt.getSelectedRow() >= 0){
			add(new Notifications(db, dal.get(jt.getSelectedRow()).getID()));
		}
		update(getGraphics());
		validate();
	}
}
