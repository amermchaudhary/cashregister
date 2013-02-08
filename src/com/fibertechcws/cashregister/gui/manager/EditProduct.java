package com.fibertechcws.cashregister.gui.manager;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class EditProduct extends JPanel implements ActionListener, KeyListener{
	JLabel title = new JLabel("Edit Product");
	JPanel center = new JPanel();
	JLabel lblBarcode = new JLabel("Barcode:");
	JTextField txtBarcode = new JTextField();
	JButton search=new JButton("Search");
	JLabel lbcode=new JLabel("Barcode:");
	JTextField tbcode=new JTextField();
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
	JButton updat = new JButton("Update");
	//JButton clear = new JButton("Clear");
	Database db;
	DepartmentHandler dh;
	Product p;
	ProductHandler pd;
	public EditProduct(Database db) {
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
		//add.addActionListener(this);
		//clear.addActionListener(this);
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 250));
		center.setLayout(new GridLayout(12,2));
		center.add(lblBarcode);
		center.add(txtBarcode);
		center.add(new JLabel());
		center.add(search);
		search.addActionListener(this);
		txtBarcode.addKeyListener(this);
		center.add(lbcode);
		center.add(tbcode);
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
		center.add(new JLabel());
		center.add(updat);
	}
	public void myFocus() {
		txtBarcode.requestFocus();
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == search)
		{
			searchProduct();
		}
		else if(ae.getSource()==updat)
		{
			//int id, String barcode, String name, int quantity, boolean notification, int low, boolean taxable, int department, double price, boolean perunit) {
			boolean notification=true,taxable=true,perunit=true;
			
			if(cmbNotification.getSelectedIndex()==1)
				notification=false;
			if(cmbTaxable.getSelectedIndex()==1)
				taxable=false;
			if(cmbPerUnit.getSelectedIndex()==1)
				perunit=false;
			p.setProduct(p.getID(), tbcode.getText(), txtName.getText(), Integer.parseInt(txtQuantity.getText()),notification , Integer.parseInt(txtLow.getText()),taxable , cmbDepartment.getSelectedIndex() + 1, Double.parseDouble(txtPrice.getText()), perunit);
			if(pd.updateProduct(p)) {
				JOptionPane.showMessageDialog(this, "Product Updated Successfully");
			} else {
				JOptionPane.showMessageDialog(this, "Failed to update product");
			}
		}
	}
	public void keyPressed(KeyEvent ke) {
		if (KeyEvent.getKeyText(ke.getKeyCode()).equals("Enter") && ke.getSource() == txtBarcode) {
			searchProduct();
		}
	}
	public void keyTyped(KeyEvent ke) {
		//System.out.println(KeyEvent.getKeyText(ke.getKeyCode()));
	}
	public void keyReleased(KeyEvent ke) {
		//System.out.println(KeyEvent.getKeyText(ke.getKeyCode()));
	}
	private void searchProduct() {
		if(!txtBarcode.getText().equals(""))
		{
			pd=new ProductHandler(db);
			p=pd.getProduct(txtBarcode.getText());
			if (p == null) {
				JOptionPane.showMessageDialog(this, "Product Not Found");
				return;
			}
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
}