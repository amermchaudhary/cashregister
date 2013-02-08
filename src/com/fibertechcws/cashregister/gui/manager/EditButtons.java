package com.fibertechcws.cashregister.gui.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fibertechcws.cashregister.db.ButtonHandler;
import com.fibertechcws.cashregister.db.Database;
import com.fibertechcws.cashregister.db.Department;
import com.fibertechcws.cashregister.db.DepartmentHandler;

public class EditButtons extends JPanel implements ActionListener {
	
	JLabel title = new JLabel("Edit Buttons");
	JPanel center = new JPanel();
	JTextField buttons[];
	JLabel labels[];
	Database db;
	ButtonHandler bh;
	ArrayList<JButton> blist;
	JButton updat=new JButton("Update");
	public EditButtons(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));
		bh=db.getButtonHandler();
		blist=bh.getButtons();
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(300, 600));
		jp.setLayout(new BorderLayout());
		//add.addActionListener(this);
		//clear.addActionListener(this);
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 550));
		center.setLayout(new GridLayout(29,2));
		buttons = new JTextField[blist.size()];
		labels = new JLabel[blist.size()];
		for(int i=0;i<blist.size();i++)
		{
			buttons[i]=new JTextField(blist.get(i).getText());
			labels[i]=new JLabel(blist.get(i).getText()+":");
			center.add(labels[i]);
			center.add(buttons[i]);
		}
		center.add(new JLabel());
		center.add(updat);
		updat.addActionListener(this);
			
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==updat)
		{
			for(int i=0;i<buttons.length;i++)
			{
				labels[i].setText(buttons[i].getText() + ":");
				blist.get(i).setText(buttons[i].getText());
				bh.updateButton(blist.get(i), i+1);
			}
			
		}
	}

}
