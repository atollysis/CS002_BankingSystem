package gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.accounts.AccountOperationResult;
import runners.MainRunner;

public class _5_TransferPage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
		
	// Components
	private JLabel lbl_title;
	private JLabel lbl_accNum;
	private JLabel lbl_balance;
	
	private JLabel lbl_error;
	private JLabel lbl_promptBalance;
	private JLabel lbl_promptRecipient;
	private JTextField fld_balance;
	private JTextField fld_recipient;
	private JButton btn_transfer;
	private JButton btn_back;

	/**
	 * Create the panel.
	 */
	public _5_TransferPage(MainRunner runner) {
		super(runner);
	}
	
	/*
	 * SUPPORT METHODS
	 */
	@Override
	protected void setupComponents() {
		this.lbl_title = new JLabel("TRANSFER");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		this.lbl_balance = new JLabel("Php 0.00");
		
		
		this.lbl_error = new JLabel(" ");
		this.lbl_promptBalance = new JLabel("Amount");
		this.lbl_promptRecipient = new JLabel("Account Number");
		this.fld_balance = new JTextField();
		this.fld_recipient = new JTextField();
		this.btn_transfer = new JButton("Transfer");
		this.btn_back = new JButton("Back");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		JPanel fieldWrapper = new JPanel();
		fieldWrapper.setLayout(new GridBagLayout());
		fieldWrapper.add(lbl_promptBalance,   BankingPanel.newFieldLabelConstraint(0, 0));
		fieldWrapper.add(fld_balance,              BankingPanel.newFieldConstraint(1, 0));
		fieldWrapper.add(lbl_promptRecipient, BankingPanel.newFieldLabelConstraint(0, 1));
		fieldWrapper.add(fld_recipient,            BankingPanel.newFieldConstraint(1, 1));
		
		this.add(lbl_title,   BankingPanel.newCenterLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  BankingPanel.newCenterLabelConstraint(0, 1, 0));
		this.add(lbl_balance, BankingPanel.newCenterLabelConstraint(0, 2, 20));
		this.add(lbl_error,   BankingPanel.newCenterLabelConstraint(0, 3, 0));
		this.add(fieldWrapper,    BankingPanel.newWrapperConstraint(0, 4));
		this.add(btn_transfer,     BankingPanel.newButtonConstraint(0, 5));
		this.add(btn_back,         BankingPanel.newButtonConstraint(1, 5));
	}

	@Override
	protected void setupInteractions() {
		this.btn_transfer.addActionListener(e -> {
			double amount;
			try {
				amount = Double.parseDouble(this.fld_balance.getText());
			} catch (NumberFormatException ex) {
				amount = 0.0;
			}
			Account recipientAccount = AccountManager.findAccount(this.fld_recipient.getText());
			AccountOperationResult result = this.accountManager.getCurrAccount().transfer(recipientAccount, amount);
			this.transactionManager.setTransferTransaction(this.accountManager.getCurrAccount(), recipientAccount);
			this.transactionManager.setAmount(amount);
			this.transactionManager.setStatus(result);
			
			/*
			 * I could bring down the transaction manager call here...
			 * but I want things to be consistent between all the panels.
			 */
			switch (result) {
				case SUCCESS:
					this.transactionManager.addNewTransaction();
					this.runner.updateAccountDetails();
					this.runner.goToPanel(PanelType.ACCOUNT);
					break;
			
				case ACCOUNT_INVALID:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Account not found or is invalid.");
					break;
					
				case BALANCE_INSUFFICIENT:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Insufficient balance.");
					break;
					
				case BALANCE_INVALID:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Invalid balance.");
					break;
					
				default:
					// none
					throw new IllegalStateException("Super secret erroR!!");
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
		this.fld_recipient.setText("");
		this.lbl_error.setText(" ");
	}
	
	@Override
	public void setDetails(Account account) {
		this.lbl_accNum.setText(String.format("#%s", account.getAccountNumber()));
		this.lbl_balance.setText(String.format("Php %.2f", account.getBalance()));
		this.lbl_error.setText(" ");
	}
}
