package com.fibertechcws.cashregister.gui.manager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class AddProduct extends JPanel implements ActionListener{
	JLabel title = new JLabel("Add Product");
	JPanel center = new JPanel();
	JLabel lblBarcode = new JLabel("Barcode:");
	JTextField txtBarcode = new JTextField();
	JLabel lblName = new JLabel("Name:");
	JTextField txtName = new JTextField();
	JLabel lblQuantity = new JLabel("Quantity:");
	JTextField txtQuantity = new JTextField();
	String[] data = {"Yes", "No"};
	JLabel lblNotification = new JLabel("Notification:");
	JComboBox cmbNotification = new JComboBox(data);
	JLabel lblLow = new JLabel("Low Amount:");
	JTextField txtLow = new JTextField();
	JLabel lblTaxable = new JLabel("Taxable:");
	JComboBox cmbTaxable = new JComboBox(data);
	JLabel lblDepartment = new JLabel("Department:");
	JComboBox cmbDepartment;
	JLabel lblPrice = new JLabel("Price:");
	JTextField txtPrice = new JTextField();
	JLabel lblPerUnit = new JLabel("Per Unit:");
	JComboBox cmbPerUnit = new JComboBox(data);
	JButton add = new JButton("Add Product");
	JButton clear = new JButton("Clear");
	Database db;
	DepartmentHandler dh;
	public AddProduct(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		//title.setPreferredSize(new Dimension(500, 75));
		//add(title);
		JPanel jp = new JPanel();
		dh = new DepartmentHandler(db);
		ArrayList<Department> depts = dh.getDepartments();
		String[] departments = new String[depts.size()];
		for (int i=0; i<depts.size(); i++) {
			departments[i] = depts.get(i).getName();
		}
		cmbDepartment = new JComboBox(departments);
		
		jp.setPreferredSize(new Dimension(500, 300));
		jp.setLayout(new BorderLayout());
		add.addActionListener(this);
		clear.addActionListener(this);
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 250));
		center.setLayout(new GridLayout(10,2));
		center.add(lblBarcode);
		center.add(txtBarcode);
		center.add(lblName);
		center.add(txtName);
		center.add(lblQuantity);
		center.add(txtQuantity);
		center.add(lblNotification);
		center.add(cmbNotification);
		center.add(lblLow);
		center.add(txtLow);
		center.add(lblTaxable);
		center.add(cmbTaxable);
		center.add(lblDepartment);
		center.add(cmbDepartment);
		center.add(lblPrice);
		center.add(txtPrice);
		center.add(lblPerUnit);
		center.add(cmbPerUnit);
		center.add(add);
		center.add(clear);
	}
	public void myFocus() {
		txtBarcode.requestFocus();
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == clear) {
			txtBarcode.setText("");
			txtName.setText("");
			txtQuantity.setText("");
			txtLow.setText("");
			txtPrice.setText("");
		} else if(ae.getSource() == add) {
			boolean correct = true;
			int qty = 0, low = 0;
			double price = 0.0;
			try {
				qty = Integer.parseInt(txtQuantity.getText());
				low = Integer.parseInt(txtLow.getText());
				price = Double.parseDouble(txtPrice.getText());
			} catch (Exception e) {
				System.out.println("Please check to make sure you have entered everything correctly");
				correct = false;
			}
			if(correct) {
				boolean notif = false;
				boolean taxable = false;
				boolean perunit = false;
				if(((String) cmbNotification.getSelectedItem()).equals("Yes")) {
					notif = true;
				}
				if(((String) cmbTaxable.getSelectedItem()).equals("Yes")) {
					taxable = true;
				}
				if(((String) cmbPerUnit.getSelectedItem()).equals("Yes")) {
					perunit = true;
				}
				int dept = dh.getDepartment((String) cmbDepartment.getSelectedItem()).getID();
				ProductHandler ph = new ProductHandler(db);
				if (ph.addProduct(new Product(0, txtBarcode.getText(), txtName.getText(), qty, notif, low, taxable, dept, price, perunit))) {
					//clear things
					txtBarcode.setText("");
					txtName.setText("");
					txtQuantity.setText("");
					txtLow.setText("");
					txtPrice.setText("");
					JOptionPane.showMessageDialog(this, "Product Added Successfully");
				} else {
					JOptionPane.showMessageDialog(this, "There was an error trying to add product to the database");
				}
			}
		}
	}
}