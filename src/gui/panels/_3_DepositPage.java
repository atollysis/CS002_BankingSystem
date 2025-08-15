package gui.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import banking_system.accounts.Account;
import banking_system.accounts.AccountOperationResult;
import runners.MainRunner;

public class _3_DepositPage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	
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
		super(runner);
	}
	
	/*
	 * SUPPORT METHODS
	 */
	@Override
	protected void setupComponents() {
		this.lbl_title = new JLabel("DEPOSIT");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		this.lbl_balance = new JLabel("Php 0.00");
		
		this.lbl_error = new JLabel(" ");
		this.fld_balance = new JTextField();
		this.btn_deposit = new JButton("Deposit");
		this.btn_back = new JButton("Back");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		this.add(lbl_title,   BankingPanel.newCenterLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  BankingPanel.newCenterLabelConstraint(0, 1, 0));
		this.add(lbl_balance, BankingPanel.newCenterLabelConstraint(0, 2, 20));
		this.add(lbl_error,   BankingPanel.newCenterLabelConstraint(0, 3, 0));
		GridBagConstraints fieldConstraint = BankingPanel.newFieldConstraint(0, 4);
		fieldConstraint.gridwidth = 2;
		this.add(fld_balance,      fieldConstraint);
		this.add(btn_deposit,      BankingPanel.newButtonConstraint(0, 5));
		this.add(btn_back,         BankingPanel.newButtonConstraint(1, 5));
	}

	@Override
	protected void setupInteractions() {
		this.btn_deposit.addActionListener(e -> {
			double amount;
			try {
				amount = Double.parseDouble(this.fld_balance.getText());
			} catch (NumberFormatException ex) {
				amount = 0.0;
			}
			AccountOperationResult result = this.accountManager.getCurrAccount().deposit(amount);
			this.transactionManager.setDepositTransaction(this.accountManager.getCurrAccount());
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
			this.runner.goToPanel(PanelType.ACCOUNT);
		});
	}
	
	/*
	 * SERVICE METHODS
	 */
	@Override
	public void clearFieldsMessages() {
		this.fld_balance.setText("");
		this.lbl_error.setText(" ");
	}
	
	@Override
	public void setDetails(Account account) {
		this.lbl_accNum.setText(String.format("#%s", account.getAccountNumber()));
		this.lbl_balance.setText(String.format("Php %.2f", account.getBalance()));
		this.lbl_error.setText(" ");
	}
}
