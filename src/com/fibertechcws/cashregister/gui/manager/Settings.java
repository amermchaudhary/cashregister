package com.fibertechcws.cashregister.gui.manager;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class Settings extends JPanel implements ActionListener{
	JLabel title = new JLabel("Edit Settings");
	JPanel center = new JPanel();
	JLabel lblName=new JLabel("Store Name:");
	JTextField txtName=new JTextField();
	JLabel lblAddress = new JLabel("Street Address:");
	JTextField txtAddress = new JTextField();
	JLabel lblCSZ = new JLabel("City, State, Zip: ");
	JTextField txtCSZ = new JTextField();
	JLabel lblPhone = new JLabel("Phone Number: ");
	JTextField txtPhone = new JTextField();
	JLabel lblPassword = new JLabel("New Password:");
	JPasswordField txtPassword = new JPasswordField();
	JLabel lblTax = new JLabel("Tax Percentage:");
	JTextField txtTax = new JTextField();
	JButton updat = new JButton("Update");
	//JButton clear = new JButton("Clear");
	Database db;
	SettingsHandler sh;
	public Settings(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		//title.setPreferredSize(new Dimension(500, 75));
		//add(title);
		JPanel jp = new JPanel();
		sh = new SettingsHandler(db);
		ArrayList<String> sal = sh.getSettings();
		txtName.setText(sal.get(0));
		txtAddress.setText(sal.get(1));
		txtCSZ.setText(sal.get(2));
		txtPhone.setText(sal.get(3));
		txtTax.setText(sal.get(4));
		jp.setPreferredSize(new Dimension(500, 300));
		jp.setLayout(new BorderLayout());
		//add.addActionListener(this);
		//clear.addActionListener(this);
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 250));
		center.setLayout(new GridLayout(12,2));
		center.add(lblName);
		center.add(txtName);
		center.add(lblAddress);
		center.add(txtAddress);
		center.add(lblCSZ);
		center.add(txtCSZ);
		center.add(lblPhone);
		center.add(txtPhone);
		center.add(lblPassword);
		center.add(txtPassword);
		center.add(lblTax);
		center.add(txtTax);
		center.add(new JLabel());
		center.add(updat);
		updat.addActionListener(this);
	
	}
	public void actionPerformed(ActionEvent ae) {
		/*if(ae.getSource() == search)
		{
			if(!txtBarcode.getText().equals(""))
			{
				pd=new ProductHandler(db);
				p=pd.getProduct(txtBarcode.getText());
				tbcode.setText(p.getBarcode());
				txtName.setText(p.getName());
				txtQuantity.setText(""+p.getQuantity());
				txtLow.setText(""+p.getLow());
				txtPrice.setText(""+p.getPrice());
				if(p.getNotification())
					cmbNotification.setSelectedIndex(0);
				else
					cmbNotification.setSelectedIndex(1);
				if(p.isTaxable())
					cmbTaxable.setSelectedIndex(0);
				else
					cmbTaxable.setSelectedIndex(1);
				cmbDepartment.setSelectedIndex(p.getDepartment() - 1);
				if(p.getPerUnit())
					cmbPerUnit.setSelectedIndex(0);
				else
					cmbPerUnit.setSelectedIndex(1);
					center.update(center.getGraphics());
				center.validate();
				updat.addActionListener(this);
			
			}
			else
			{
				//center.add(new JLabel("Please enter the barcode and try again"));
			}
		}
		else */if(ae.getSource()==updat)
		{
			String qs = "UPDATE Settings SET StoreName=\'"+txtName.getText()+"\', StoreAddress=\'"+txtAddress.getText()+"\', StoreCSZ=\'"+txtCSZ.getText()+"\', StorePhone=\'"+txtPhone.getText()+"\', Tax=\'"+txtTax.getText()+"\'";
			if(!txtPassword.getText().equals("")) {
				qs += ", Password=\'"+txtPassword.getText()+"\'";
			}
			sh.update(qs);
			JOptionPane.showMessageDialog(this, "You must restart the program for the settings to take affect");
		}
	}
}