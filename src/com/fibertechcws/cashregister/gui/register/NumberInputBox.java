package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class NumberInputBox extends JDialog implements ActionListener{
	public enum Type {NONTAX, TAX, COUPON, TOTAL}
	JButton[][] b = new JButton[4][3];
	JTextField jtf = new JTextField();
	JPanel center = new JPanel();
	JPanel bottom = new JPanel();
	JButton enter = new JButton("Enter");
	JButton back = new JButton("Go Back");
	RegisterPanel rp;
	Type type;
	JTextField f = new JTextField();
	public NumberInputBox(RegisterPanel rp, String text, Type type) {
		super();
		//super((JFrame) ((JRootPane)((JLayeredPane) ((JPanel) ((JPanel) ((JPanel)rp.getParent()).getParent()).getParent()).getParent()).getParent()).getParent(), text, true);
		setLayout(new BorderLayout());
		bottom.setLayout(new GridLayout(1,2));
		center.setLayout(new GridLayout(4,3));
		this.rp = rp;
		this.type = type;
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				b[i][j] = new JButton();
				center.add(b[i][j]);
				b[i][j].setFont(new Font("Arial", Font.BOLD, 25));
				b[i][j].addActionListener(this);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jtf.setFont(new Font("Arial", Font.BOLD, 25));
		enter.setFont(new Font("Arial", Font.BOLD, 25));
		back.setFont(new Font("Arial", Font.BOLD, 25));
		enter.addActionListener(this);
		back.addActionListener(this);
		bottom.add(enter);
		bottom.add(back);
		add(jtf, BorderLayout.NORTH);
		bottom.setPreferredSize(new Dimension(500,75));
		jtf.setPreferredSize(new Dimension(500,50));
		add(bottom, BorderLayout.SOUTH);
		add(center, BorderLayout.CENTER);
		b[0][0].setText("1");
		b[0][1].setText("2");
		b[0][2].setText("3");
		b[1][0].setText("4");
		b[1][1].setText("5");
		b[1][2].setText("6");
		b[2][0].setText("7");
		b[2][1].setText("8");
		b[2][2].setText("9");
		b[3][0].setText(".");
		b[3][1].setText("0");
		b[3][2].setText("Clear");
		setPreferredSize(new Dimension(500,400));
		pack();
		setVisible(true);
	}
	public void dispose() {
		rp.needFocus(true);
		super.dispose();
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == enter ) {
			switch(type) {
				case NONTAX:
					rp.addProduct("NT", jtf.getText());
					break;
				case TAX:
					rp.addProduct("T", jtf.getText());
					break;
				case COUPON:
					rp.addProduct("C", jtf.getText());
					break;
				case TOTAL:
					
					break;
			}
			jtf.setText("");
			dispose();
		} else if (ae.getSource() == back) {
			dispose();
		} else if (ae.getSource() == b[3][2]) {
			jtf.setText("");
		} else {
			jtf.setText(jtf.getText() + ((JButton) ae.getSource()).getText());
		}
	}
}