package com.fibertechcws.cashregister.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TopPanel extends JPanel implements ActionListener{
	CR cr;
	JButton pm = new JButton("Management");
	JButton register = new JButton("Cash Register");
	JButton reports = new JButton("Inventory Reports");
	JButton payout = new JButton("Payout");
	JButton exit = new JButton("Exit");
	public TopPanel(CR cr) {
		super();
		this.cr = cr;
		setLayout(new GridLayout(1,10));
		add(register);
		add(pm);
		//add(reports);
		//add(payout);
		add(exit);
		
		register.addActionListener(this);
		pm.addActionListener(this);
		reports.addActionListener(this);
		payout.addActionListener(this);
		exit.addActionListener(this);
		setPreferredSize(new Dimension(500,75));
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == pm) {
			cr.show("Manager");
		} else if(ae.getSource() == register) {
			cr.show("Register");
		}else if(ae.getSource() == payout) {
				cr.show("PayOut");
		}else if(ae.getSource() == reports) {
			cr.show("IReport");
		} else if(ae.getSource() == exit) {
			System.exit(0);
		}
	}
}