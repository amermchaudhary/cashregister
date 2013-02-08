package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import com.fibertechcws.cashregister.gui.*;
import com.fibertechcws.cashregister.db.*;
import java.util.*;
import java.text.*;
import java.sql.*;
public class RegisterPanel extends JPanel implements Runnable{
	Component focused;
	JTable jt;
	LeftPanel lp;
	BottomPanel bp;
	Database db;
	ArrayList<Product> products = new ArrayList<Product>();
	ProductHandler ph;
	NumberFormat nf;
	RightPanel rp;
	Thread t = new Thread(this);
	boolean needFocus = true;
	Shift currentShift;
	ShiftHandler sh;
	DepartmentHandler dh;
	JOptionPane jop = new JOptionPane();
	PayoutHandler poh;
	SettingsHandler sh1;
	ArrayList<String> settings;
	double tax;
	DecimalFormat df = new DecimalFormat("#.##");
	JFrame jf;
	JPanel SP = new JPanel(new CardLayout());
	InputPanel ip;
	Product product;
	public RegisterPanel(Database db, JFrame jf) {
		super();
		this.db = db;
		this.jf = jf;
		sh = new ShiftHandler(db);
		dh = new DepartmentHandler(db);
		poh = new PayoutHandler(db);
		setShift(sh.getCurrentShift());
		sh1 = new SettingsHandler(db);
		settings = sh1.getSettings();
		db.rightPanel(settings.get(5));
		tax = Double.parseDouble(df.format(Double.parseDouble(settings.get(4))/100));
		nf = NumberFormat.getCurrencyInstance();
		ph = new ProductHandler(db);
		lp = new LeftPanel(this);
		rp = new RightPanel(this);
		bp = new BottomPanel(this, db);
		setLayout(new BorderLayout());
		add(lp, BorderLayout.WEST);
		//add(bp, BorderLayout.SOUTH);
		//add(rp, BorderLayout.EAST);
		//Right Panel
		ip = new InputPanel();
		SP.add(rp, "RightPanel");
		SP.add(ip, "InputPanel");
		
		
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		jp1.setLayout(new GridLayout(2,1));
		jp2.setLayout(new BorderLayout());
		jp2.add(SP, BorderLayout.EAST);
		jp2.add(new CenterPanel(this), BorderLayout.CENTER);
		jp1.add(jp2);
		jp1.add(bp);
		add(jp1, BorderLayout.CENTER);
		//printReceipt("Brothers Grocery\nHarrison Ave\nHarrison, NJ\n973-999-9999\n<hr>Item 1\nItem 2", 7);
		//add(new LeftPanel(), BorderLayout.CENTER);
		//setPreferredSize(new Dimension(500,75));
		
		t.start();
	}
	public ArrayList<String> getSettings() {
		return settings;
	}
	public double getTax() {
		return this.tax;
	}
	public void showInputBox(String type) {
		getLeftPanel().setVisible(false);
		getBottomPanel().hideButtons();
		SP.remove(ip);
		ip = new InputPanel(this, type);
		SP.add(ip, "InputPanel");
		((CardLayout)SP.getLayout()).show(SP, "InputPanel");
	}
	public void showRightPanel() {
		((CardLayout)SP.getLayout()).show(SP, "RightPanel");
	}
	public void printReceipt(String s, int lines) {
		PrintReceipt pr = new PrintReceipt(s, lines);
		
	}
	public void setCurrentProduct(Product p) {
		this.product = p;
	}
	public Product getCurrentProduct() {
		return this.product;
	}
	public void printReceipt(ArrayList<PrintableLine> plal) {
		PrintReceipt pr = new PrintReceipt(plal);
		
	}
	public void closeShift(String s) {
		String depts = "";
		ArrayList<Department> dal = getDepartmentHandler().getDepartments();
		ArrayList<PrintableLine> plal = new ArrayList<PrintableLine>(15);
		ArrayList<String> al2 = getSettings();
		plal.add(new PrintableLine(al2.get(0)));
		plal.add(new PrintableLine(al2.get(1)));
		plal.add(new PrintableLine(al2.get(2)));
		plal.add(new PrintableLine(al2.get(3)));
		plal.add(new PrintableLine("_______________"));
		plal.add(new PrintableLine(" "));
		for (Department d : dal) {
			depts += d.getName()+","+d.getShiftTotals()+"|";
			plal.add(new PrintableLine(d.getName(), d.getShiftTotals() + ""));
		}
		plal.add(new PrintableLine("_______________"));
		plal.add(new PrintableLine("Payout"));
		ArrayList<Payout> poal = getPayoutHandler().getPayouts(getShift().getID());
		double ptotal = 0.00;
		for(Payout p : poal) {
			plal.add(new PrintableLine(p.getCompany(), p.getAmount()+""));
			ptotal += p.getAmount();
		}
		plal.add(new PrintableLine("Total: ", ptotal+""));
		getDepartmentHandler().clearAllDepartmentTotals();
		depts = depts.substring(0,depts.length() - 1);
		Calendar start = Calendar.getInstance();
		start.setTime(new java.sql.Date(getShift().getStartTime()));
		Calendar end = Calendar.getInstance();
		plal.add(new PrintableLine("_______________"));
		plal.add(new PrintableLine(" "));
		plal.add(new PrintableLine("Cashier Name: ", getShift().getCashier()));
		plal.add(new PrintableLine("Shift Started: ", start.MONTH+"/"+start.DATE+"/"+start.YEAR+" "+start.HOUR+":"+start.MINUTE+" "+start.AM_PM));
		plal.add(new PrintableLine("Shift Ended: ", end.MONTH+"/"+end.DATE+"/"+end.YEAR+" "+end.HOUR+":"+end.MINUTE+" "+end.AM_PM));
		plal.add(new PrintableLine("Total Tax: ", getShift().getTax() + ""));
		plal.add(new PrintableLine("Total Cash: ", getShiftHandler().getCurrentTotal(getShift()) + ""));
		setShift(getShiftHandler().closeShift(depts, s, getShift()));
		printReceipt(plal);
	}
	public Shift getShift() {
		return currentShift;
	}
	public void setShift(Shift s) {
		if (s == null) {
			JOptionPane.showMessageDialog(this, "Error Loading Current Shift! System must exit now");
			System.exit(1);
		} else {
			currentShift = s;
		}
	}
	public ShiftHandler getShiftHandler() {
		return sh;
	}
	public DepartmentHandler getDepartmentHandler() {
		return dh;
	}
	public void needFocus(boolean focus) {
		this.needFocus = focus;
	}
	public String getInput(String message) {
		needFocus(false);
		jop.requestFocus();
		String rv =  jop.showInputDialog(null, message);
		needFocus(true);
		return rv;
	}
	public Database getDB() {
		return db;
	}
	public PayoutHandler getPayoutHandler() {
		return poh;
	}
	public LeftPanel getLeftPanel() {
		return this.lp;
	}
	public BottomPanel getBottomPanel() {
		return this.bp;
	}
	public void setF(Component jtf) {
		this.focused = jtf;
	}
	public void startT() {
		
	}
	public Component getF() {
		return focused;
	}
	public void setJT(JTable jt) {
		this.jt = jt;
		rp.setJT(jt);
	}
	public JTable getJT() {
		return jt;
	}
	public void run () {
		int count = 0;
		while(true) {
			if(needFocus && jf.getFocusOwner() != focused) {
				focused.requestFocus();
				//System.out.println(":"+count);
				//count++;
			}
		}
	}
	public void APBW(Product p, String price) {
		try {
			double index = Double.parseDouble(price);
			if(index > 0) {
				index = Double.parseDouble(df.format(index*p.getPrice()));
				p.setPrice(index);
				products.add(p);
				String [] data = new String[7];
				data[0] = (getJT().getModel().getRowCount() + 1) + "";
				data[1] = p.getBarcode();
				data[2] = p.getName();
				data[3] = "1";
				data[4] = nf.format(p.getPrice());
				double tax = p.getPrice()*this.tax;
				if (p.isTaxable()) {
					data[5] = nf.format(tax);
					data[6] = nf.format(p.getPrice() + tax);
				} else {
					data[5] = "0.00";
					data[6] = nf.format(p.getPrice());
				}
				((DefaultTableModel)getJT().getModel()).addRow(data);
			}
		} catch (Exception e) {
			needFocus(false);
			jop.showMessageDialog(this, "Product Add Failed");
			needFocus(true);
		}
	}
	public void addProduct(String code) {
		Product p = ph.getProduct(code);
		
		if (p!=null) {
			if (products.contains(p) && !p.getPerUnit()) {
				this.plus1(products.indexOf(p));
			} else {
				if(p.getPerUnit()) {
					setCurrentProduct(p);
					showInputBox("PRODUCT_WEIGHT");
				} else {
					products.add(p);
					String [] data = new String[7];
					data[0] = (getJT().getModel().getRowCount() + 1) + "";
					data[1] = p.getBarcode();
					data[2] = p.getName();
					data[3] = "1";
					data[4] = nf.format(p.getPrice());
					double tax = p.getPrice()*this.tax;
					if (p.isTaxable()) {
						data[5] = nf.format(tax);
						data[6] = nf.format(p.getPrice() + tax);
					} else {
						data[5] = "0.00";
						data[6] = nf.format(p.getPrice());
					}
					((DefaultTableModel)getJT().getModel()).addRow(data);
				}
			}
		} else {
			needFocus(false);
			JOptionPane.showMessageDialog(this, "Product Not Found");
			needFocus(true);
		}
		rp.updateTotals(products);
	}
	/*public void addProduct(String price, int val) {
		Product p;
		if (val == 1) {
			p = new Product(-1, "TX", "Taxable Product", 1, false, 0, true, -1, Double.parseDouble(price), false);
		} else if (val == 0) {
			p = new Product(-1, "NT", "NonTaxable Product", 1, false, 0, false, -1, Double.parseDouble(price), false);
		} else if (val == -1) {
			p = new Product(-1, "C", "Coupon", -1, false, 0, false, -1, Double.parseDouble("-" + price), false);
		} else {
			p = null;
		}
		if (p != null) {
			products.add(p);
			String [] data = new String[7];
			data[0] = (getJT().getModel().getRowCount() + 1) + "";
			data[1] = p.getBarcode();
			data[2] = p.getName();
			data[3] = "1";
			data[4] = nf.format(p.getPrice());
			double tax = p.getPrice()*this.tax;
			if (p.isTaxable()) {
				data[5] = nf.format(tax);
				data[6] = nf.format(p.getPrice() + tax);
			} else {
				data[5] = "0.00";
				data[6] = nf.format(p.getPrice());
			}
			((DefaultTableModel)getJT().getModel()).addRow(data);
			rp.updateTotals(products);
		}
	}*/
	public void addProduct(String code, double amount) {
		Product p = ph.getProduct(code);
		
		if (p!=null) {
			if (products.contains(p) && !p.getPerUnit()) {
				this.plus1(products.indexOf(p));
			} else {
				if(p.getPerUnit()) {
					needFocus(false);
					try {
						jop.requestFocus();
						double index = Double.parseDouble(jop.showInputDialog(this, "Enter the Amount"));
						if(index > 0) {
							index = Double.parseDouble(df.format(index*p.getPrice()));
							p.setPrice(index);
							products.add(p);
							String [] data = new String[7];
							data[0] = (getJT().getModel().getRowCount() + 1) + "";
							data[1] = p.getBarcode();
							data[2] = p.getName();
							data[3] = "1";
							data[4] = nf.format(p.getPrice());
							double tax = p.getPrice()*this.tax;
							if (p.isTaxable()) {
								data[5] = nf.format(tax);
								data[6] = nf.format(p.getPrice() + tax);
							} else {
								data[5] = "0.00";
								data[6] = nf.format(p.getPrice());
							}
							((DefaultTableModel)getJT().getModel()).addRow(data);
						}
					} catch (Exception e) {
						needFocus(false);
						jop.showMessageDialog(this, "Product Add Failed");
						needFocus(true);
					}
					needFocus(true);
				} else {
					products.add(p);
					String [] data = new String[7];
					data[0] = (getJT().getModel().getRowCount() + 1) + "";
					data[1] = p.getBarcode();
					data[2] = p.getName();
					data[3] = "1";
					data[4] = nf.format(p.getPrice());
					double tax = p.getPrice()*this.tax;
					if (p.isTaxable()) {
						data[5] = nf.format(tax);
						data[6] = nf.format(p.getPrice() + tax);
					} else {
						data[5] = "0.00";
						data[6] = nf.format(p.getPrice());
					}
					((DefaultTableModel)getJT().getModel()).addRow(data);
				}
			}
		} else {
			needFocus(false);
			JOptionPane.showMessageDialog(this, "Product Not Found");
			needFocus(true);
		}
		rp.updateTotals(products);
	}
	public void addProduct(String code, String price) {
		Product p = ph.getProduct(code);
		
		if (p!=null) {
			if (products.contains(p) && !p.getPerUnit()) {
				this.plus1(products.indexOf(p));
			} else {
				if(p.getPerUnit()) {
					try {
						double index = Double.parseDouble(price);
						if(index > 0) {
							index = Double.parseDouble(df.format(index*p.getPrice()));
							p.setPrice(index);
							products.add(p);
							String [] data = new String[7];
							data[0] = (getJT().getModel().getRowCount() + 1) + "";
							data[1] = p.getBarcode();
							data[2] = p.getName();
							data[3] = "1";
							data[4] = nf.format(p.getPrice());
							double tax = p.getPrice()*this.tax;
							if (p.isTaxable()) {
								data[5] = nf.format(tax);
								data[6] = nf.format(p.getPrice() + tax);
							} else {
								data[5] = "0.00";
								data[6] = nf.format(p.getPrice());
							}
							((DefaultTableModel)getJT().getModel()).addRow(data);
						}
					} catch (Exception e) {
						needFocus(false);
						jop.showMessageDialog(this, "Product Add Failed");
						needFocus(true);
					}
					needFocus(true);
				} else {
					products.add(p);
					String [] data = new String[7];
					data[0] = (getJT().getModel().getRowCount() + 1) + "";
					data[1] = p.getBarcode();
					data[2] = p.getName();
					data[3] = "1";
					data[4] = nf.format(p.getPrice());
					double tax = p.getPrice()*this.tax;
					if (p.isTaxable()) {
						data[5] = nf.format(tax);
						data[6] = nf.format(p.getPrice() + tax);
					} else {
						data[5] = "0.00";
						data[6] = nf.format(p.getPrice());
					}
					((DefaultTableModel)getJT().getModel()).addRow(data);
				}
			}
		} else {
			needFocus(false);
			JOptionPane.showMessageDialog(this, "Product Not Found");
			needFocus(true);
		}
		rp.updateTotals(products);
	}
	public void plus1() {
		int selected = jt.getSelectedRow();
		if (selected >= 0) {
			Product p = products.get(selected);
			String value = (String) (jt.getModel().getValueAt(selected, 3).toString());
			int q = Integer.parseInt(value);
			q++;
			double tax = 0.00;
			if (p.isTaxable()) {
				tax = q * p.getPrice() * this.tax;
			}
			double total = q * p.getPrice() + tax;
			jt.getModel().setValueAt(nf.format(tax), selected, 5);
			jt.getModel().setValueAt(nf.format(total), selected, 6);
			jt.getModel().setValueAt(q, selected, 3);
			rp.updateTotals(products);
		}
	}
	public void plus1(int selected) {
		if (selected >= 0) {
			Product p = products.get(selected);
			String value = (String) (jt.getModel().getValueAt(selected, 3).toString());
			int q = Integer.parseInt(value);
			q++;
			double tax = 0.00;
			if (p.isTaxable()) {
				tax = q * p.getPrice() * this.tax;
			}
			double total = q * p.getPrice() + tax;
			jt.getModel().setValueAt(nf.format(tax), selected, 5);
			jt.getModel().setValueAt(nf.format(total), selected, 6);
			jt.getModel().setValueAt(q, selected, 3);
			rp.updateTotals(products);
		}
	}
	public void minus1() {
		int selected = jt.getSelectedRow();
		if (selected >= 0) {
			Product p = products.get(selected);
			int q = Integer.parseInt((String) jt.getModel().getValueAt(selected, 3).toString());
			q--;
			double tax = 0.00;
			if (p.isTaxable()) {
				tax = q * p.getPrice() * this.tax;
			}
			double total = q * p.getPrice() + tax;
			jt.getModel().setValueAt(nf.format(tax), selected, 5);
			jt.getModel().setValueAt(nf.format(total), selected, 6);
			jt.getModel().setValueAt(q, selected, 3);
			rp.updateTotals(products);
		}
	}
	public void deleteProduct() {
		int selected = jt.getSelectedRow();
		if (selected >= 0) {
			((DefaultTableModel)jt.getModel()).removeRow(selected);
			products.remove(selected);
		}
		rp.updateTotals(products);
	}
	public void deleteAllProducts() {
		DefaultTableModel dtm = (DefaultTableModel)jt.getModel();
		while(dtm.getRowCount()>0) {
			dtm.removeRow(0);
			products.remove(0);
		}
		rp.updateTotals(products);
	}
	public ArrayList<Product> getTransactionProducts() {
		int count = 0;
		for (Product p : products) {
			p.setQuantity(Integer.parseInt(jt.getModel().getValueAt(count, 3).toString()));
			count++;
		}
		return products;
	}
}