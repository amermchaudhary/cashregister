package com.fibertechcws.cashregister.gui.register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.fibertechcws.cashregister.db.*;
public class Payouts extends JDialog implements ActionListener, KeyListener{
	JLabel lblCompany = new JLabel("Company Name");
	JLabel lblAmount = new JLabel("Amount");
	JTextField txtCompany = new JTextField();
	JTextField txtAmount = new JTextField();
	JPanel bottom = new JPanel();
	JButton enter = new JButton("Enter");
	JButton back = new JButton("Go Back");
	RegisterPanel rp;
	JFrame jf;
	PayoutHandler poh;
	public Payouts(RegisterPanel rp) {
		super();
		jf = (JFrame) ((JRootPane)((JLayeredPane) ((JPanel) ((JPanel) ((JPanel)rp.getParent()).getParent()).getParent()).getParent()).getParent()).getParent();
		jf.setEnabled(false);
		//super((JFrame) ((JRootPane)((JLayeredPane) ((JPanel) ((JPanel) ((JPanel)rp.getParent()).getParent()).getParent()).getParent()).getParent()).getParent(), text, true);
		setLayout(new GridLayout(5,1));
		bottom.setLayout(new GridLayout(1,2));
		this.rp = rp;
		poh = new PayoutHandler(rp.getDB());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		lblCompany.setFont(new Font("Arial", Font.BOLD, 25));
		lblAmount.setFont(new Font("Arial", Font.BOLD, 25));
		txtCompany.setFont(new Font("Arial", Font.BOLD, 25));
		txtAmount.setFont(new Font("Arial", Font.BOLD, 25));
		
		enter.setFont(new Font("Arial", Font.BOLD, 25));
		back.setFont(new Font("Arial", Font.BOLD, 25));
		enter.addActionListener(this);
		txtCompany.addKeyListener(this);
		back.addActionListener(this);
		txtAmount.addKeyListener(this);
		bottom.add(enter);
		bottom.add(back);
		add(lblCompany);
		add(txtCompany);
		add(lblAmount);
		add(txtAmount);
		add(bottom);
		bottom.setPreferredSize(new Dimension(500,75));
		setPreferredSize(new Dimension(500,400));
		setLocation((int) (rp.getWidth() / 2) - 250, (int) (rp.getHeight() / 2) - 200);
		pack();
		setVisible(true);
	}
	public void dispose() {
		rp.needFocus(true);
		jf.setEnabled(true);
		super.dispose();
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == enter && txtCompany.getText().length() > 0) {
			makePayout();
		} else if (ae.getSource() == back) {
			jf.setEnabled(true);
			dispose();
		}
	}
	public void keyReleased(KeyEvent ke) {
	
	}
	public void keyTyped(KeyEvent ke) {
	
	}
	public void keyPressed(KeyEvent ke) {
		if (KeyEvent.getKeyText(ke.getKeyCode()).equals("Enter")) {
			makePayout();
		}
	}
	public void makePayout() {
		long date = 0;
		if (txtCompany.getText().length() > 0 && txtAmount.getText().length() > 0) {
			try {
				poh.addPayout(new Payout(0, date, txtCompany.getText(), Double.parseDouble(txtAmount.getText()), rp.getShift().getID()));
				rp.getShift().setTotal(rp.getShiftHandler().getCurrentTotal(rp.getShift()) - Double.parseDouble(txtAmount.getText()));
				rp.getShiftHandler().updateTotal(rp.getShift());
				ArrayList<PrintableLine> plal = new ArrayList<PrintableLine>(11);
				plal.add(new PrintableLine(rp.getSettings().get(0)));
				plal.add(new PrintableLine(rp.getSettings().get(1)));
				plal.add(new PrintableLine(rp.getSettings().get(2)));
				plal.add(new PrintableLine(rp.getSettings().get(3)));
				plal.add(new PrintableLine("_______________"));
				plal.add(new PrintableLine(" "));
				plal.add(new PrintableLine("Cash Payout for:"));
				plal.add(new PrintableLine(txtCompany.getText()));
				plal.add(new PrintableLine("Amount:", txtAmount.getText()));
				plal.add(new PrintableLine("Signature:"));
				plal.add(new PrintableLine("_______________"));
				rp.printReceipt(plal);
				dispose();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(rp, "There was and error please check your entry");
			}
		}
	}
}