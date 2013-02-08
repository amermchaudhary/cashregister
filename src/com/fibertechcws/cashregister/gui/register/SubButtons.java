package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class SubButtons extends JPanel implements ActionListener{
	ArrayList<SubButton> al;
	SubButtonHandler bh;
	Database db;
	RegisterPanel rp;
	JButton gb = new JButton("Go Back");
	public SubButtons(int group, Database db, RegisterPanel rp) {
		super();
		this.db = db;
		this.rp = rp;
		bh = new SubButtonHandler(db);
		setLayout(new GridLayout(4,7));
		al = bh.getButtons(group);
		add(gb);
		gb.setBackground(Color.CYAN);
		gb.addActionListener(this);
		for (SubButton b : al) {
			add(b);
			b.setBackground(Color.PINK);
			b.addActionListener(this);
		}
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == gb) {
			rp.getBottomPanel().showHome();
		} else {
			int index = al.indexOf(ae.getSource());
			if (al.get(index).getShortcut() != null && al.get(index).getShortcut() != "") {
				rp.addProduct(al.get(index).getShortcut());
			}
		}
	}
}