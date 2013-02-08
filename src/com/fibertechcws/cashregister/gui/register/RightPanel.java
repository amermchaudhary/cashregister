package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.text.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
import com.fibertechcws.cashregister.db.*;
public class RightPanel extends JPanel implements ActionListener, KeyListener{
	JPanel top = new JPanel();
	JPanel middle = new JPanel();
	JPanel bottom = new JPanel(new CardLayout());
	RegisterPanel rp;
	NumberFormat nf;
	JTable jt;
	double total = 0.00;
	TransactionHandler th;
	//top
	JLabel lblbs = new JLabel("Enter Barcode / Shortcut");
	JTextField txtbs = new JTextField();
	JButton btnbs = new JButton("Go!");
	//middle
	JLabel lblitems = new JLabel("Items: 0");
	JLabel lblsubtotal = new JLabel("Subtotal: $0.00");
	JLabel lbltax = new JLabel("Tax: $0.00");
	JLabel lbltotal = new JLabel("Total: $0.00");
	//bottom
	JButton btnCash = new JButton("Pay");
	JPanel pnlCash = new JPanel();
	
	JLabel lblPaid = new JLabel("Amount Paid:");
	JTextField txtPaid = new JTextField();
	JButton btnPaid = new JButton("Cash");
	JButton btnBack = new JButton("Go Back");
	JPanel pnlPaid = new JPanel();
	
	JLabel lblA = new JLabel("Paid: ");
	JLabel lblB = new JLabel("Change: ");
	JButton btnPR = new JButton("Print Receipt");
	JButton btnNT = new JButton("New Transaction");
	JPanel pnlA = new JPanel();
	
	JTextField pf;
	String Receipt = "";
	double sub, taxable;
	ArrayList<PrintableLine> plal;
	java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
	public RightPanel(RegisterPanel rp) {
		super();
		this.rp = rp;
		th = new TransactionHandler(rp.getDB());
		nf = NumberFormat.getCurrencyInstance();
		setBackground(new Color(200,255,200));
		top.setBackground(new Color(200,255,200));
		setLayout(new GridLayout(3,1));
		add(top);
		add(middle);
		add(bottom);
		//TOP
		top.setLayout(new GridLayout(3,1));
		rp.setF(txtbs);
		btnbs.addActionListener(this);
		txtbs.addKeyListener(this);
		top.add(lblbs);
		top.add(txtbs);
		top.add(btnbs);
		//Middle
		middle.setBackground(new Color(200,255,200));
		middle.setLayout(new GridLayout(4,1));
		lblitems.setFont(new Font("Arial", Font.BOLD, 18));
		lblsubtotal.setFont(new Font("Arial", Font.BOLD, 18));
		lbltax.setFont(new Font("Arial", Font.BOLD, 18));
		lbltotal.setFont(new Font("Arial", Font.BOLD, 18));
		middle.add(lblitems);
		middle.add(lblsubtotal);
		middle.add(lbltax);
		middle.add(lbltotal);
		setPreferredSize(new Dimension(300,300));
		//Bottom
		bottom.setBackground(new Color(200,255,200));
		pnlCash.setLayout(new BorderLayout());
		pnlCash.add(btnCash);
		btnCash.addActionListener(this);
		bottom.add(pnlCash, "Pay");
		
		pnlPaid.setLayout(new GridLayout(4,1));
		pnlPaid.add(lblPaid);
		pnlPaid.add(txtPaid);
		pnlPaid.add(btnPaid);
		pnlPaid.add(btnBack);
		btnPaid.addActionListener(this);
		txtPaid.addKeyListener(this);
		btnBack.addActionListener(this);
		bottom.add(pnlPaid, "Paid");
		
		pnlA.setLayout(new GridLayout(4,1));
		pnlA.add(lblA);
		pnlA.add(lblB);
		pnlA.add(btnPR);
		pnlA.add(btnNT);
		btnPR.addActionListener(this);
		btnNT.addActionListener(this);
		lblA.setFont(new Font("Arial", Font.BOLD, 18));
		lblB.setFont(new Font("Arial", Font.BOLD, 18));
		bottom.add(pnlA, "A");
	}
	public void updateTotals(ArrayList<Product> al) {
		sub = 0.00;
		taxable = 0.00;
		int quantity = 0;
		for (int i =0; i < al.size(); i++) {
			Product p = al.get(i);
			int q = Integer.parseInt((String) (jt.getModel().getValueAt(i, 3).toString()));
			quantity += q;
			sub += p.getPrice() * q;
			if(p.isTaxable()) {
				taxable += p.getPrice() * q;
			}
		}
		double tax = Double.parseDouble(df.format(taxable*rp.getTax()));
		total = sub + tax;
		lblitems.setText("Items: " + quantity);
		lblsubtotal.setText("Subtotal: " + nf.format(sub));
		lbltax.setText("Tax: " + nf.format(tax));
		lbltotal.setText("Total: " + nf.format(total));
	}
	public void setJT(JTable jt) {
		this.jt = jt;
	}
	public JTable getJT() {
		return this.jt;
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnCash) {
			top.setVisible(false);
			pf = ((JTextField) rp.getF());
			rp.setF(txtPaid);
			rp.getLeftPanel().setVisible(false);
			rp.getBottomPanel().hideButtons();
			((CardLayout)bottom.getLayout()).show(bottom, "Paid");
		} else if (ae.getSource() == btnBack) {
			top.setVisible(true);
			rp.setF(pf);
			rp.getLeftPanel().setVisible(true);
			rp.getBottomPanel().showButtons();
			((CardLayout)bottom.getLayout()).show(bottom, "Pay");
		} else if (ae.getSource() == btnbs) {
			rp.addProduct(txtbs.getText());
			txtbs.setText("");
		} else if (ae.getSource() == btnPaid) {
			paidMoney();
		} else if (ae.getSource() == btnNT) {
			//new transaction
			rp.deleteAllProducts();
			top.setVisible(true);
			rp.setF(pf);
			rp.getLeftPanel().setVisible(true);
			rp.getBottomPanel().showButtons();
			((CardLayout)bottom.getLayout()).show(bottom, "Pay");
		} else if (ae.getSource() == btnPR) {
			//Print Receipt
			plal.add(new PrintableLine("_______________"));
			plal.add(new PrintableLine("Subtotal: ", nf.format(sub)));
			plal.add(new PrintableLine("Tax: ", nf.format(total-sub)));
			plal.add(new PrintableLine("Total: ", nf.format(total)));
			plal.add(new PrintableLine("Paid: ", lblA.getText().substring(5, lblA.getText().length())));
			plal.add(new PrintableLine("Change: ", lblB.getText().substring(7, lblB.getText().length())));
			rp.printReceipt(plal);
		}
	}
	public void paidMoney() {
		if(total != 0.0) {
			if (total <= Double.parseDouble(txtPaid.getText())) {
				//complete and save transaction
				double paid = Double.parseDouble(txtPaid.getText());
				Calendar cal = Calendar.getInstance();
				java.sql.Date d = new Date(cal.getTime().getTime());
				ArrayList<Product> al1 = rp.getTransactionProducts();
				Calendar end = Calendar.getInstance();
				ArrayList<String> al2 = rp.getSettings();
				plal = new ArrayList<PrintableLine>(15);
				plal.add(new PrintableLine(al2.get(0)));
				plal.add(new PrintableLine(al2.get(1)));
				plal.add(new PrintableLine(al2.get(2)));
				plal.add(new PrintableLine(al2.get(3)));
				plal.add(new PrintableLine(end.MONTH+"/"+end.DATE+"/"+end.YEAR+" "+end.HOUR+":"+end.MINUTE+" "+end.AM_PM));
				plal.add(new PrintableLine("_______________"));
				ProductHandler ph = new ProductHandler(rp.getDB());
				for (Product p : al1) {
					plal.add(new PrintableLine(p.getQuantity() + " " + p.getName(), p.getQuantity()*p.getPrice() + ""));
					if (!ph.updateProductQuantity(p)) {
						rp.needFocus(false);
						JOptionPane.showMessageDialog(rp, "There was an error updating the product quantity");
						rp.needFocus(true);
					}
				}
				th.addTransaction(new Transaction(0, d, new Time(cal.getTime().getTime()), al1, total, paid));
				rp.getShift().setTotal(rp.getShiftHandler().getCurrentTotal(rp.getShift()) + total);
				rp.getShift().setTax(rp.getShift().getTax() + Double.parseDouble(df.format(taxable*rp.getTax())));
				rp.getShiftHandler().updateTotal(rp.getShift());
				rp.getDepartmentHandler().updateDepartments(calculateDepartments(al1));
				lblA.setText("Paid: " + nf.format(paid));
				lblB.setText("Change: " + nf.format(paid - total));
				txtPaid.setText("");
				rp.printReceipt("", 0);
				((CardLayout)bottom.getLayout()).show(bottom, "A");
			} else {
				rp.needFocus(false);
				JOptionPane.showMessageDialog(rp, "Amount less than Total");
				rp.needFocus(true);
			}
		}
	}
	public void keyPressed(KeyEvent ke) {
		if (KeyEvent.getKeyText(ke.getKeyCode()).equals("Enter")) {
			if (ke.getSource() == txtbs) {
				rp.addProduct(txtbs.getText());
				txtbs.setText("");
			} else if (ke.getSource() == txtPaid) {
				paidMoney();
			}
		}
	}
	public void keyTyped(KeyEvent ke) {
		if(ke.KEY_TYPED  == KeyEvent.VK_ENTER) {
			System.out.println("Enter Pressed!!");
		}
	}
	public void keyReleased(KeyEvent ke) {
		//System.out.println(KeyEvent.getKeyText(ke.getKeyCode()));
	}
	public ArrayList<Department> calculateDepartments(ArrayList<Product> pal) {
		ArrayList<Department> list1 = rp.getDepartmentHandler().getDepartments();
		ArrayList<Department> list2 = new ArrayList<Department>();
		for (Product p : pal) {
			Department d = getDepartmentFromList(list1, p.getDepartment());
			if (list2.contains(d)) {
				int index = list2.indexOf(d);
				list2.get(index).setShiftTotals(list2.get(index).getShiftTotals() + (p.getPrice() * p.getQuantity()));
			} else {
				if(p.getDepartment() > 0) {
					list2.add(new Department(p.getDepartment(), d.getName(), p.getPrice() * p.getQuantity()));
				}
			}
		}
		return list2;
	}
	public Department getDepartmentFromList(ArrayList<Department> list1, int id) {
		for (Department d : list1) {
			if (d.getID() == id) {
				return d;
			}
		}
		return null;
	}
}