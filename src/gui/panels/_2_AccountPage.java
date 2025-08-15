package gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import banking_system.accounts.Account;
import banking_system.accounts.ManagerOperationResult;
import runners.MainRunner;

public class _2_AccountPage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
		
	// Components
	private JLabel lbl_welcome;
	private JLabel lbl_accNum;
	private JLabel lbl_balance;
	
	private JLabel lbl_error;
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
		super(runner);
	}
	
	/*
	 * SUPPORT METHODS
	 */
	@Override
	protected void setupComponents() {
		this.lbl_welcome = new JLabel("ACCOUNT");
		lbl_welcome.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		this.lbl_balance = new JLabel("Php 0.00");
		
		this.lbl_error = new JLabel(" ");
		this.btn_deposit = new JButton("Deposit");
		this.btn_withdraw = new JButton("Withdraw");
		this.btn_transfer = new JButton("Transfer Balance");
		this.btn_logout = new JButton("Log Out");
		this.btn_changePin = new JButton("Change PIN");
		this.btn_delete = new JButton("Close Account");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		this.add(lbl_welcome, BankingPanel.newCenterLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  BankingPanel.newCenterLabelConstraint(0, 1, 0));
		this.add(lbl_balance, BankingPanel.newCenterLabelConstraint(0, 2, 20));
		this.add(lbl_error,   BankingPanel.newCenterLabelConstraint(0, 3, 0));
		this.add(btn_deposit,   BankingPanel.newButtonConstraint(0, 4));
		this.add(btn_withdraw,  BankingPanel.newButtonConstraint(0, 5));
		this.add(btn_transfer,  BankingPanel.newButtonConstraint(0, 6));
		this.add(btn_logout,    BankingPanel.newButtonConstraint(1, 4));
		this.add(btn_changePin, BankingPanel.newButtonConstraint(1, 5));
		this.add(btn_delete,    BankingPanel.newButtonConstraint(1, 6));
	}

	@Override
	protected void setupInteractions() {
		this.btn_deposit.addActionListener(e -> {
			this.runner.goToPanel(PanelType.DEPOSIT);
		});
		
		this.btn_withdraw.addActionListener(e -> {
			this.runner.goToPanel(PanelType.WITHDRAW);
		});
		
		this.btn_transfer.addActionListener(e -> {
			this.runner.goToPanel(PanelType.TRANSFER);
		});
		
		this.btn_logout.addActionListener(e -> {
			this.accountManager.logout();
			this.transactionManager.resetSessionDetails();
			this.runner.goToPanel(PanelType.LOGIN);
		});
		
		this.btn_changePin.addActionListener(e -> {
			this.runner.goToPanel(PanelType.CHANGE_PIN);
		});
		
		this.btn_delete.addActionListener(e -> {
			ManagerOperationResult result = this.accountManager.closeAccount();
			switch (result) {
				case SUCCESS:
					this.runner.goToPanel(PanelType.CLOSURE);
					break;
					
				case INVALID_ACCOUNT_CLOSURE:
					this.lbl_error.setText("You still have balance!");
					break;
					
				case IO_PROBLEM:
					this.lbl_error.setText("ERROR: Problem in reading/writing.");
					throw new IllegalStateException("Error in reading/writing to database.");
	
				default:
					// INVALID_ACCOUNT_CLOSED
					// INVALID_ACCOUNT_EXISTS
					// INVALID_ACCOUNT_FORMAT
					// INVALID_ACCOUNT_NONEXISTENT
					// INVALID_ACCOUNT_NOT_CLOSED
					// INVALID_PIN_FORMAT
					// INVALID_PIN_WRONG
					throw new IllegalStateException("Unexpected operation result returned: " + result);
			}
		});
	}

	/*
	 * SERVICE METHODS
	 */
	@Override
	public void clearFieldsMessages() {
		this.lbl_error.setText(" ");
	}
	
	@Override
	public void setDetails(Account account) {
		this.lbl_accNum.setText(String.format("#%s", account.getAccountNumber()));
		this.lbl_balance.setText(String.format("Php %.2f", account.getBalance()));
		this.lbl_error.setText(" ");
	}
}
