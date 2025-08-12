package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.accounts.AccountOperationResult;
import banking_system.transactions.TransactionManager;
import banking_system.transactions.TransactionType;
import gui.interfaces.Clearable;
import gui.interfaces.Settable;
import runners.MainRunner;

public class _3_DepositPage extends JPanel implements Settable, Clearable {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	
	// Controllers
	private final MainRunner runner;
	private final AccountManager accountManager;
	private final TransactionManager transactionManager;
		
	// Components
	private JLabel lbl_title;
	private JLabel lbl_accNum;
	private JLabel lbl_balance;
	
	private JLabel lbl_error;
	private JTextField fld_balance;
	private JButton btn_deposit;
	private JButton btn_back;

	/**
	 * Create the panel.
	 */
	public _3_DepositPage(MainRunner runner) {
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	private void setupComponents() {
		this.lbl_title = new JLabel("DEPOSIT");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		this.lbl_balance = new JLabel("Php 0.00");
		
		this.lbl_error = new JLabel(" ");
		this.fld_balance = new JTextField();
		this.btn_deposit = new JButton("Deposit");
		this.btn_back = new JButton("Back");
	}
	
	private void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		this.add(lbl_title,   newLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  newLabelConstraint(0, 1, 0));
		this.add(lbl_balance, newLabelConstraint(0, 2, 20));
		this.add(lbl_error,   newLabelConstraint(0, 3, 0));
		this.add(fld_balance, newFieldConstraint(0, 4));
		this.add(btn_deposit, newButtonConstraint(0, 5));
		this.add(btn_back,    newButtonConstraint(1, 5));
	}

	private static GridBagConstraints newLabelConstraint(int x, int y, int bottomInset) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, bottomInset, 0);
		return c;
	}
	
	private static GridBagConstraints newFieldConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c;
	}
	
	private static GridBagConstraints newButtonConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c; 
	}
	
	private void setupInteractions() {
		this.btn_deposit.addActionListener(e -> {
			double amount;
			try {
				amount = Double.parseDouble(this.fld_balance.getText());
			} catch (NumberFormatException ex) {
				amount = 0.0;
			}
			AccountOperationResult result = this.accountManager.getCurrAccount().deposit(amount);
			this.transactionManager.setTransactionType(TransactionType.DEPOSIT);
			this.runner.setAccountId();
			this.transactionManager.setAmount(amount);
			this.transactionManager.setRecipientID(null);
			this.transactionManager.setStatus(result);
			
			switch (result) {
				case SUCCESS:
					this.transactionManager.addNewTransaction();
					this.runner.updateAccountDetails();
					this.runner.goToPanel(PanelType.ACCOUNT);
					break;
					
				case BALANCE_INVALID:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Invalid balance.");
					break;
					
				default:
					// BALANCE_INSUFFICIENT
					// ACCOUNT_INVALID
					throw new IllegalStateException("Unintended account operation result: " + result);
			}
		});
		
		this.btn_back.addActionListener(e -> {
			this.transactionManager.setTransactionType(null);
			this.runner.goToPanel(PanelType.ACCOUNT);
		});
	}
	
	/*
	 * SERVICE METHODS
	 */
	@Override
	public void setDetails(Account account) {
		this.lbl_accNum.setText("#" + account.getAccountNumber());
		this.lbl_balance.setText(String.format("Php %.2f", account.getBalance()));
	}
	
	@Override
	public void clearFieldsAndMsgs() {
		this.fld_balance.setText("");
		this.lbl_error.setText(" ");
	}
}
