package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import banking_system.transactions.TransactionType;
import gui.interfaces.Settable;
import runners.MainRunner;

public class _2_AccountPage extends JPanel implements Settable {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	
	// Controllers
	private final MainRunner runner;
	private final AccountManager accountManager;
	private final TransactionManager transactionManager;
		
	// Components
	private JLabel lbl_welcome;
	private JLabel lbl_accNum;
	private JLabel lbl_balance;
	
	private JButton btn_changePin;
	private JButton btn_deposit;
	private JButton btn_withdraw;
	private JButton btn_logout;
	private JButton btn_transfer;
	private JButton btn_delete;
	
	/**
	 * Create the panel.
	 */
	public _2_AccountPage(MainRunner runner) {
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	private void setupComponents() {
		this.lbl_welcome = new JLabel("Welcome!");
		lbl_welcome.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		this.lbl_balance = new JLabel("Php 0.00");
		
		this.btn_deposit = new JButton("Deposit");
		this.btn_withdraw = new JButton("Withdraw");
		this.btn_transfer = new JButton("Transfer Balance");
		this.btn_logout = new JButton("Log Out");
		this.btn_changePin = new JButton("Change PIN");
		this.btn_delete = new JButton("Close Account");
	}
	
	private void setupLayout() {
		this.setLayout(new GridBagLayout());
		
		this.add(lbl_welcome, newLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  newLabelConstraint(0, 1, 0));
		this.add(lbl_balance, newLabelConstraint(0, 2, 20));
		this.add(btn_deposit,  newButtonConstraint(0, 3));
		this.add(btn_withdraw, newButtonConstraint(0, 4));
		this.add(btn_transfer, newButtonConstraint(0, 5));
		this.add(btn_logout,    newButtonConstraint(1, 3));
		this.add(btn_changePin, newButtonConstraint(1, 4));
		this.add(btn_delete,    newButtonConstraint(1, 5));
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
			this.transactionManager.setTransactionType(TransactionType.DEPOSIT);
			this.runner.goToPanel(PanelType.DEPOSIT);
		});
		
		this.btn_withdraw.addActionListener(e -> {
			this.transactionManager.setTransactionType(TransactionType.WITHDRAW);
			this.runner.goToPanel(PanelType.WITHDRAW);
		});
		
		this.btn_transfer.addActionListener(e -> {
			this.transactionManager.setTransactionType(TransactionType.TRANSFER);
			this.runner.goToPanel(PanelType.TRANSFER);
		});
		
		this.btn_logout.addActionListener(e -> {
			this.accountManager.logout();
			this.transactionManager.clearSessionDetails();
			this.runner.goToPanel(PanelType.LOGIN);
		});
		
		this.btn_changePin.addActionListener(e -> {
			this.transactionManager.setPinChangeTransaction();
			this.runner.goToPanel(PanelType.CHANGE_PIN);
		});
		
		this.btn_delete.addActionListener(e -> {
			this.transactionManager.setAccountClosureTransaction();
			this.runner.goToPanel(PanelType.DELETE);
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
}
