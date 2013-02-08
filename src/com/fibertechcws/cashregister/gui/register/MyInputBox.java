package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class MyInputBox extends JDialog implements ActionListener, Runnable{
	JLabel lblText = new JLabel("Please enter a cashier name for the next shift!");
	JTextField txtInput = new JTextField();
	JPanel bottom = new JPanel();
	JButton enter = new JButton("Enter");
	JButton back = new JButton("Go Back");
	RegisterPanel rp;
	JFrame jf;
	public MyInputBox(RegisterPanel rp) {
		super();
		jf = (JFrame) ((JRootPane)((JLayeredPane) ((JPanel) ((JPanel) ((JPanel)rp.getParent()).getParent()).getParent()).getParent()).getParent()).getParent();
		jf.setEnabled(false);
		//super((JFrame) ((JRootPane)((JLayeredPane) ((JPanel) ((JPanel) ((JPanel)rp.getParent()).getParent()).getParent()).getParent()).getParent()).getParent(), text, true);
		setLayout(new GridLayout(3,1));
		bottom.setLayout(new GridLayout(1,2));
		this.rp = rp;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		lblText.setFont(new Font("Arial", Font.BOLD, 25));
		txtInput.setFont(new Font("Arial", Font.BOLD, 25));
		
		enter.setFont(new Font("Arial", Font.BOLD, 25));
		back.setFont(new Font("Arial", Font.BOLD, 25));
		enter.addActionListener(this);
		back.addActionListener(this);
		bottom.add(enter);
		bottom.add(back);
		add(lblText);
		add(txtInput);
		add(bottom);
		bottom.setPreferredSize(new Dimension(500,75));
		setPreferredSize(new Dimension(500,400));
		pack();
		setVisible(true);
	}
	public void run() {
		while (true) {
			//this.requestFocus();
		}
	}
	public void dispose() {
		rp.needFocus(true);
		jf.setEnabled(true);
		super.dispose();
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == enter ) {
			rp.closeShift(txtInput.getText());
			dispose();
		} else if (ae.getSource() == back) {
			jf.setEnabled(true);
			dispose();
		}
	}
}