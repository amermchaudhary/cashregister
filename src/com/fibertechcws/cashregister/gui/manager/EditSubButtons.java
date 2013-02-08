package com.fibertechcws.cashregister.gui.manager;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.fibertechcws.cashregister.db.*;

public class EditSubButtons extends JPanel implements ActionListener, ButtonUpdateListener{
	JLabel title = new JLabel("Edit SubButtons");
	JPanel center = new JPanel();
	Database db;
	SubButtonHandler sbh;
	ArrayList<SubButton> sbList;
	ArrayList<JButton> blist;
	ButtonHandler bh;
	JTextField sbht[];
	JTextField sbct[];
	JLabel sbl[];
	int groupid;
	JButton updat=new JButton("Update");
	JButton gb=new JButton("Back");
	public EditSubButtons(Database db) {
		super();
		this.db = db;
		title.setFont(new Font("Arial", Font.BOLD, 25));

		sbh=new SubButtonHandler(db);
		bh=db.getButtonHandler();
		bh.addButtonUpdateListener(this);
		blist=bh.getButtons();
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(500, 600));
		jp.setLayout(new BorderLayout());
		jp.add(title, BorderLayout.NORTH);
		jp.add(center, BorderLayout.CENTER);
		add(jp);
		center.setPreferredSize(new Dimension(500, 650));
		center.setLayout(new GridLayout(15,15));
		for(int i=0;i<blist.size();i++)
		{
			blist.get(i).addActionListener(this);
			center.add(blist.get(i));
		}
		updat.addActionListener(this);
		gb.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource()==updat)
		{
			for(int i=0;i<sbList.size();i++)
			{
				sbList.get(i).setText(sbht[i].getText());
				sbList.get(i).setShortcut(sbct[i].getText());
				sbh.updateButton(sbList.get(i), i+1);
				sbl[i].setText(sbht[i].getText() + ":");
			}
		} else if(ae.getSource()==gb)
		{
			center.removeAll();
			center.setPreferredSize(new Dimension(500, 650));
			center.setLayout(new GridLayout((int) blist.size()/2,2));
			for(int i=0;i<blist.size();i++)
			{
				center.add(blist.get(i));
			}
			center.update(center.getGraphics());
			center.validate();
		}
		else
		{
			for(int i=0;i<blist.size();i++)
			{
				if(ae.getSource()==blist.get(i))
				{
					sbList=sbh.getButtons(i+1);
				}
				center.remove(blist.get(i));
			}
			center.update(center.getGraphics());
			center.validate();
			center.setLayout(new GridLayout(29,3));
			sbht=new JTextField[sbList.size()];
			sbct=new JTextField[sbList.size()];
			sbl = new JLabel[sbList.size()];
			//center.add(new JLabel("Size="+sbList.size()));
			for(int i=0;i<sbht.length;i++)
			{
				sbl[i] = new JLabel(sbList.get(i).getText()+":");
				center.add(sbl[i]);
				sbht[i]=new JTextField(sbList.get(i).getText());
				center.add(sbht[i]);
				sbct[i]=new JTextField(sbList.get(i).getShortcut());
				center.add(sbct[i]);
			}
			center.add(new JLabel(""));
			center.add(updat);
			center.add(gb);
			center.update(center.getGraphics());
			center.validate();
		}

	}
	public void updateButton(int id, String s) {
		blist.get(id).setText(s);
	}
}
