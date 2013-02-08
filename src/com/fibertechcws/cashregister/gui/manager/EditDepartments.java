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

import com.fibertechcws.cashregister.db.*;

public class EditDepartments extends JPanel implements ActionListener {
	
	JLabel title = new JLabel("Departments");
	JPanel center = new JPanel();
	Database db;
	DepartmentHandler dh;
	ArrayList<Department> dList;
	JButton updat=new JButton("Update");
	JTextField dl[];
	Repaintable mp;
	public EditDepartments(Database db, Repaintable mp) {
		super();
		this.db = db;
		this.mp = mp;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		dh=new DepartmentHandler(db);
		dList=dh.getDepartments();
		dl=new JTextField[dList.size()];
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(500, 500));
		jp.setLayout(new BorderLayout());
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 550));
		center.setLayout(new GridLayout(29,2));
		for(int i=0;i<dl.length;i++)
		{
			dl[i]=new JTextField(dList.get(i).getName());
			center.add(new JLabel(dList.get(i).getName()+":"));
			center.add(dl[i]);
		}
		center.add(new JLabel());
		center.add(updat);
		updat.addActionListener(this);
			
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==updat)
		{
			for(int i=0;i<dl.length;i++)
			{
				dList.get(i).setName(dl[i].getText());
			}
			dh.updateDepartments(dList);
			mp.repaintIt("EditDepartments");
		}
	}

}
