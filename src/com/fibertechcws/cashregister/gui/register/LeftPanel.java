package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class LeftPanel extends JPanel implements ActionListener{
	JButton plus1 = new JButton("+1");
	JButton minus1 = new JButton("-1");
	JButton delete = new JButton("Delete");
	JButton deleteAll = new JButton("Delete All");
	JButton taxable = new JButton("Taxable");
	JButton nontax = new JButton("Non-Tax");
	JButton coupon = new JButton("Coupon");
	JButton payout = new JButton("Payout");
	JButton closeShift = new JButton("Close Shift");
	RegisterPanel rp;
	public LeftPanel(RegisterPanel rp) {
		super();
		this.rp = rp;
		setLayout(new GridLayout(9,1));
		add(plus1);
		add(minus1);
		add(delete);
		add(deleteAll);
		add(taxable);
		add(nontax);
		add(coupon);
		add(payout);
		add(closeShift);
		plus1.addActionListener(this);
		minus1.addActionListener(this);
		delete.addActionListener(this);
		deleteAll.addActionListener(this);
		taxable.addActionListener(this);
		nontax.addActionListener(this);
		coupon.addActionListener(this);
		payout.addActionListener(this);
		closeShift.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == plus1) {
			rp.plus1();
		} else if (ae.getSource() == minus1) {
			rp.minus1();
		} else if (ae.getSource() == delete) {
			rp.deleteProduct();
		} else if (ae.getSource() == deleteAll) {
			rp.deleteAllProducts();
		} else if (ae.getSource() == taxable) {
			rp.addProduct("T");
		} else if (ae.getSource() == nontax) {
			//rp.needFocus(false);
			/*String price = rp.getInput("Please enter the amount");
			if (price != null && !price.equals("")) {
				rp.addProduct(price, 0);
			}*/
			//NumberInputBox nib = new NumberInputBox(rp, "Non-Taxable Product", NumberInputBox.Type.NONTAX);
			//rp.addProduct("NT", 0);
			//String[] a = {"Hello", "World"};
			rp.addProduct("NT");
		} else if (ae.getSource() == coupon) {
			rp.addProduct("C");
		} else if (ae.getSource() == payout) {
			rp.needFocus(false);
			//Payouts pay = new Payouts(rp);
			rp.showInputBox("PAYOUT");
		} else if (ae.getSource() == closeShift) {
			rp.showInputBox("CLOSE_SHIFT");
		}
	}
}