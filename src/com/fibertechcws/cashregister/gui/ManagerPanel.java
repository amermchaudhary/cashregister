package com.fibertechcws.cashregister.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.fibertechcws.cashregister.gui.manager.*;
import com.fibertechcws.cashregister.db.*;
public class ManagerPanel extends JPanel implements ActionListener, Repaintable{
	CR cr;
	Database db;
	JPanel left = new JPanel();
	JPanel clp = new JPanel(new CardLayout());
	JButton pAdd = new JButton("Add Product");
	JButton pEdit = new JButton("Edit Product");
	JButton bEdit = new JButton("Edit Buttons");
	JButton sbEdit = new JButton("Edit SubButtons");
	JButton notify = new JButton("Notifications");
	JButton settings = new JButton("Settings");
	JButton dView = new JButton("Edit Departments");
	JButton dAdd = new JButton("Add Departments");
	AddProduct ap;
	EditProduct ep;
	EditButtons eb;
	EditSubButtons esb;
	EditDepartments vd;
	//Notifications n;
	SDFN sdfn;
	Settings st;
	public ManagerPanel(CR cr, Database db) {
		super();
		this.cr = cr;
		this.db = db;
		ap = new AddProduct(db);
		ep=new EditProduct(db);
		eb=new EditButtons(db);
		esb=new EditSubButtons(db);
		vd=new EditDepartments(db, this);
		st=new Settings(db);
		//n=new Notifications(db);
		sdfn = new SDFN(db);
		setLayout(new BorderLayout());
		left.setPreferredSize(new Dimension(150, 375));
		left.setLayout(new GridLayout(8,1));
		setPreferredSize(new Dimension(500,375));
		
		/*
		 * ActionListeners
		 */
		pAdd.addActionListener(this);
		pEdit.addActionListener(this);
		bEdit.addActionListener(this);
		sbEdit.addActionListener(this);
		settings.addActionListener(this);
		dAdd.addActionListener(this);
		dView.addActionListener(this);
		notify.addActionListener(this);
		add(left, BorderLayout.WEST);
		add(clp, BorderLayout.CENTER);
		clp.add(ap, "AddProduct");
		clp.add(ep,"EditProduct");
		clp.add(eb,"EditButtons");
		clp.add(esb,"EditSubButtons");
		clp.add(vd,"EditDepartments");
		clp.add(st,"Settings");
		//clp.add(n,"Notifications");
		clp.add(sdfn,"SDFN");
		left.add(pAdd);
		left.add(pEdit);
		left.add(notify);
		left.add(settings);
		left.add(bEdit);
		left.add(sbEdit);
		left.add(dAdd);
		left.add(dView);
	}
	public void repaintIt(String name) {
		ap = new AddProduct(db);
		ep=new EditProduct(db);
		eb=new EditButtons(db);
		esb=new EditSubButtons(db);
		vd=new EditDepartments(db, this);
		st=new Settings(db);
		//n=new Notifications(db);
		sdfn = new SDFN(db);
		clp.add(ap, "AddProduct");
		clp.add(ep,"EditProduct");
		clp.add(eb,"EditButtons");
		clp.add(esb,"EditSubButtons");
		clp.add(vd,"EditDepartments");
		clp.add(st,"Settings");
		//clp.add(n,"Notifications");
		clp.add(sdfn,"SDFN");
		((CardLayout)clp.getLayout()).show(clp, name);
	}
	public void repaintIt() {
	ap = new AddProduct(db);
		ep=new EditProduct(db);
		eb=new EditButtons(db);
		esb=new EditSubButtons(db);
		vd=new EditDepartments(db, this);
		st=new Settings(db);
		//n=new Notifications(db);
		sdfn=new SDFN(db);
		clp.add(ap, "AddProduct");
		clp.add(ep,"EditProduct");
		clp.add(eb,"EditButtons");
		clp.add(esb,"EditSubButtons");
		clp.add(vd,"EditDepartments");
		clp.add(st,"Settings");
		//clp.add(n,"Notifications");
		clp.add(sdfn,"SDFN");
	}
	public void myFocus() {
		ap.myFocus();
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		String str=arg0.getActionCommand();
		if(str.equals("Add Product"))
		{
			repaintIt("AddProduct");
			ap.myFocus();
		}
		else if(str.equals("Edit Product"))
		{
			repaintIt("EditProduct");
			ep.myFocus();
		}
		else if(str.equals("Edit Buttons"))
		{
			repaintIt("EditButtons");
		}
		else if(str.equals("Edit SubButtons"))
		{
			repaintIt("EditSubButtons");
		}
		else if(str.equals("Edit Departments"))
		{
			repaintIt("EditDepartments");
		}
		else if(str.equals("Settings"))
		{
			((CardLayout)clp.getLayout()).show(clp, "Settings");
		}
		else if(str.equals("Notifications"))
		{
			repaintIt("SDFN");
		}
		else if(str.equals("Add Departments"))
		{
			String name = JOptionPane.showInputDialog(this, "Enter the Department Name");
			if(!(name == null || name.equals(""))) {
				DepartmentHandler dh = new DepartmentHandler(db);
				dh.addDepartment(name);
				repaintIt("EditDepartments");
			}
		}
		
	}
}