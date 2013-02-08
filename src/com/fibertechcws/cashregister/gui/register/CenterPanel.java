package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
public class CenterPanel extends JPanel implements ActionListener{
	JTable jt = new JTable();
	RegisterPanel rp;
	DefaultTableModel tm;
	public CenterPanel(RegisterPanel rp) {
		super();
		setLayout(new BorderLayout());
		this.rp = rp;
		rp.setJT(jt);
		JScrollPane jsp = new JScrollPane(jt);
		setBackground(Color.BLACK);
		add(jsp, BorderLayout.CENTER);
		tm = (DefaultTableModel) jt.getModel();
		tm.addColumn("ID");
		tm.addColumn("Barcode");
		tm.addColumn("Name");
		tm.addColumn("Qty");
		tm.addColumn("Price");
		tm.addColumn("Tax");
		tm.addColumn("Total");
		//tm.setRowCount(50);
		//jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jt.getColumnModel().getColumn(0).setPreferredWidth(20);
		jt.getColumnModel().getColumn(1).setPreferredWidth(150);
		jt.getColumnModel().getColumn(2).setPreferredWidth(300);
		jt.getColumnModel().getColumn(3).setPreferredWidth(20);
		jt.setFont(new Font("Arial", Font.BOLD, 18));
		jt.setRowHeight(30);
		
	}
	public void actionPerformed(ActionEvent ae) {
		
	}
}