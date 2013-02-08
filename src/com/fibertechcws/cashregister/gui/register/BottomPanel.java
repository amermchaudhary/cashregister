package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class BottomPanel extends JPanel implements ActionListener, ButtonUpdateListener{
	JButton[][] b = new JButton[4][10];
	ArrayList<JButton> left = new ArrayList(12);
	ArrayList<JButton> right = new ArrayList(4);
	ArrayList<JButton> center;
	RegisterPanel rp;
	JPanel np = new JPanel();
	JPanel clp = new JPanel(new CardLayout());
	JPanel bp = new JPanel();
	JPanel rightPanel = new JPanel();
	Database db;
	ButtonHandler bh;
	boolean closeShift = true;
	public BottomPanel(RegisterPanel rp, Database db) {
		super();
		this.db = db;
		this.rp = rp;
		bh = db.getButtonHandler();
		bh.addButtonUpdateListener(this);
		center = bh.getButtons();
		setLayout(new BorderLayout());
		np.setLayout(new GridLayout(4,3));
		bp.setLayout(new GridLayout(4,8));
		rightPanel.setLayout(new GridLayout(4,1));
		clp.add(bp, "Home");
		add(np, BorderLayout.EAST);
		add(rightPanel, BorderLayout.WEST);
		add(clp, BorderLayout.CENTER);
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 7; j++) {
				if (count < 28) {
					b[i][j] = center.get(count);
					bp.add(b[i][j]);
					b[i][j].addActionListener(this);
					b[i][j].setBackground(Color.PINK);
				} else {
					
				}
				count++;
			}
		}
		//for (int i=0; i<4; i++) {
			//b[i][10] = new JButton();
			//rightPanel.add(b[i][10]);
			//b[i][10].addActionListener(this);
		//}
		for (int i = 0; i < 4; i++) {
			for (int j = 7; j < 10; j++) {
				b[i][j] = new JButton(i + "," + j);
				b[i][j].addActionListener(this);
				b[i][j].setBackground(Color.CYAN);
				b[i][j].setFont(new Font("Arial", Font.BOLD, 25));
				left.add(b[i][j]);
				np.add(b[i][j]);
			}
		}
		//for (int i=0; i<4; i++) {
			//b[i][10].setBackground(new Color(200, 255, 200));
			//right.add(b[i][10]);
		//}
		//b[3][10].setBackground(Color.RED);
		//Right Buttons
		b[0][7].setText("1");
		b[0][8].setText("2");
		b[0][9].setText("3");
		b[1][7].setText("4");
		b[1][8].setText("5");
		b[1][9].setText("6");
		b[2][7].setText("7");
		b[2][8].setText("8");
		b[2][9].setText("9");
		b[3][7].setText(".");
		b[3][8].setText("0");
		b[3][9].setText("Clear");
		//Left Buttons
		//b[0][10].setText("Non-Tax");
		//b[1][10].setText("Tax");
		//b[2][10].setText("Coupon");
		//b[3][10].setText("Close Shift");
		setPreferredSize(new Dimension(300,300));
	}
	public void actionPerformed(ActionEvent ae) {
		if (left.contains(ae.getSource())) {
			if(ae.getSource() == b[3][9]) {
				((JTextField) rp.getF()).setText("");
			} else {
				((JTextField) rp.getF()).setText(((JTextField) rp.getF()).getText() + ((JButton)ae.getSource()).getText());
			}
		} else {
			int index = center.indexOf(ae.getSource());
			index++;
			clp.add(new SubButtons(index, db, rp), "SubButton");
			((CardLayout) clp.getLayout()).show(clp, "SubButton");
			//b[3][10].setText("Go Back");
			//closeShift = false;
		}
		/*} else if (right.contains(ae.getSource())) {
			//right side buttons
			if (ae.getSource() == b[3][10] && !closeShift) {
				b[3][10].setText("Close Shift");
				closeShift = true;
				((CardLayout) clp.getLayout()).show(clp, "Home");
			} else if (ae.getSource() == b[3][10] && closeShift) {
				//close shift
			} else if (ae.getSource() == b[0][10]) {
				NumberInputBox nib = new NumberInputBox(rp, "Non-Taxable Product", NumberInputBox.Type.NONTAX);
			} else if (ae.getSource() == b[1][10]) {
				NumberInputBox nib = new NumberInputBox(rp, "Taxable Product", NumberInputBox.Type.TAX);
			} else if (ae.getSource() == b[2][10]) {
				NumberInputBox nib = new NumberInputBox(rp, "Coupon", NumberInputBox.Type.COUPON);
			}
		} */
	}
	public void showHome() {
		((CardLayout) clp.getLayout()).show(clp, "Home");
	}
	public void updateButton(int id, String s) {
		center.get(id).setText(s);
	}
	public void hideButtons() {
		bp.setVisible(false);
		clp.setVisible(false);
		rightPanel.setVisible(false);
	}
	public void showButtons() {
		bp.setVisible(true);
		clp.setVisible(true);
		rightPanel.setVisible(true);
	}
}