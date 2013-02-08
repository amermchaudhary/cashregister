package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.fibertechcws.cashregister.db.*;
public class InputPanel extends JPanel implements ActionListener{
	enum InputType {PRODUCT_WEIGHT, CLOSE_SHIFT, PAYOUT};
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel();
	JTextField jtf1 = new JTextField();
	JTextField jtf2 = new JTextField();
	JButton enter = new JButton("OK");
	JButton back = new JButton("Cancel");
	RegisterPanel rp;
	InputType it;
	Component c;
	Product p;
	public InputPanel() {
		super();
	}
	public InputPanel(RegisterPanel rp, String type) {
		super();
		this.rp = rp;
		this.it = InputType.valueOf(type);
		c = rp.getF();
		rp.setF(jtf1);
		switch (it) {
			case PRODUCT_WEIGHT:
				setLayout(new GridLayout(4,1));
				lbl1.setText("Please Enter the Weight/Units:");
				add(lbl1);
				add(jtf1);
				p = rp.getCurrentProduct();
				break;
			case CLOSE_SHIFT:
				setLayout(new GridLayout(4,1));
				lbl1.setText("Please Enter the cashier name for next shift:");
				add(lbl1);
				add(jtf1);
				break;
			case PAYOUT:
				setLayout(new GridLayout(6,1));
				rp.needFocus(false);
				lbl1.setText("Company Name:");
				lbl2.setText("Amount:");
				add(lbl1);
				add(jtf1);
				add(lbl2);
				add(jtf2);
				rp.setF(jtf2);
				break;
		}
		add(enter);
		add(back);
		enter.addActionListener(this);
		back.addActionListener(this);
		setPreferredSize(new Dimension(300,300));
		jtf1.requestFocus();
		jtf1.requestFocus();
		jtf1.requestFocus();
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == enter) {
			switch (it) {
				case PRODUCT_WEIGHT:
					rp.APBW(p, jtf1.getText());
					break;
				case CLOSE_SHIFT:
					rp.closeShift(jtf1.getText());
					break;
				case PAYOUT:
					long date = 0;
					PayoutHandler poh = new PayoutHandler(rp.getDB());
					if (jtf1.getText().length() > 0 && jtf2.getText().length() > 0) {
						try {
							poh.addPayout(new Payout(0, date, jtf1.getText(), Double.parseDouble(jtf2.getText()), rp.getShift().getID()));
							rp.getShift().setTotal(rp.getShiftHandler().getCurrentTotal(rp.getShift()) - Double.parseDouble(jtf2.getText()));
							rp.getShiftHandler().updateTotal(rp.getShift());
							ArrayList<PrintableLine> plal = new ArrayList<PrintableLine>(11);
							plal.add(new PrintableLine(rp.getSettings().get(0)));
							plal.add(new PrintableLine(rp.getSettings().get(1)));
							plal.add(new PrintableLine(rp.getSettings().get(2)));
							plal.add(new PrintableLine(rp.getSettings().get(3)));
							plal.add(new PrintableLine("_______________"));
							plal.add(new PrintableLine(" "));
							plal.add(new PrintableLine("Cash Payout for:"));
							plal.add(new PrintableLine(jtf1.getText()));
							plal.add(new PrintableLine("Amount:", jtf2.getText()));
							plal.add(new PrintableLine("Signature:"));
							plal.add(new PrintableLine("_______________"));
							rp.printReceipt(plal);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(rp, "There was and error please check your entry");
						}
					}
					break;
			}
		}
		rp.needFocus(true);
		rp.setF(c);
		rp.showRightPanel();
		rp.getLeftPanel().setVisible(true);
		rp.getBottomPanel().showButtons();
	}
}