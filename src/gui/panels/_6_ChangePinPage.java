package gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import banking_system.accounts.Account;
import banking_system.accounts.AccountOperationResult;
import runners.MainRunner;

public class _6_ChangePinPage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
		
	// Components
	private JLabel lbl_title;
	private JLabel lbl_accNum;
	
	private JLabel lbl_error;
	private JLabel lbl_promptOldPin;
	private JLabel lbl_promptNewPin;
	private JPasswordField fld_oldPin;
	private JPasswordField fld_newPin;
	private JButton btn_changePin;
	private JButton btn_back;

	/**
	 * Create the panel.
	 */
	public _6_ChangePinPage(MainRunner runner) {
		super(runner);
	}
	
	/*
	 * SERVICE METHODS
	 */
	@Override
	protected void setupComponents() {
		this.lbl_title = new JLabel("CHANGE PIN");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbl_accNum = new JLabel("#1234");
		
		this.lbl_error = new JLabel(" ");
		this.lbl_promptOldPin = new JLabel("Old PIN");
		this.lbl_promptNewPin = new JLabel("New PIN");
		this.fld_oldPin = new JPasswordField();
		this.fld_newPin = new JPasswordField();
		this.btn_changePin = new JButton("Change PIN");
		this.btn_back = new JButton("Back");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		JPanel fieldWrapper = new JPanel();
		fieldWrapper.setLayout(new GridBagLayout());
		fieldWrapper.add(lbl_promptOldPin,  BankingPanel.newFieldLabelConstraint(0, 0));
		fieldWrapper.add(fld_oldPin,             BankingPanel.newFieldConstraint(1, 0));
		fieldWrapper.add(lbl_promptNewPin,  BankingPanel.newFieldLabelConstraint(0, 1));
		fieldWrapper.add(fld_newPin,             BankingPanel.newFieldConstraint(1, 1));
		
		this.add(lbl_title,  BankingPanel.newCenterLabelConstraint(0, 0, 0));
		this.add(lbl_accNum, BankingPanel.newCenterLabelConstraint(0, 1, 20));
		this.add(lbl_error,  BankingPanel.newCenterLabelConstraint(0, 2, 0));
		this.add(fieldWrapper,   BankingPanel.newWrapperConstraint(0, 3));
		this.add(btn_changePin,   BankingPanel.newButtonConstraint(0, 4));
		this.add(btn_back,        BankingPanel.newButtonConstraint(1, 4));
	}

	@Override
	protected void setupInteractions() {
		this.btn_changePin.addActionListener(e -> {
			AccountOperationResult result = this.accountManager.getCurrAccount().modifyPin(
					String.valueOf(this.fld_oldPin.getPassword()),
					String.valueOf(this.fld_newPin.getPassword()));
			this.transactionManager.setPinChangeTransaction(this.accountManager.getCurrAccount());
			this.transactionManager.setStatus(result);
			
			switch (result) {
				case SUCCESS:
					this.transactionManager.addNewTransaction();
					this.runner.updateAccountDetails();
					this.runner.goToPanel(PanelType.ACCOUNT);
					break;
					
				case PIN_INVALID:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Invalid PIN.");
					break;
					
				case PIN_WRONG:
					this.transactionManager.addNewTransaction();
					this.lbl_error.setText("Old PIN does not match.");
					break;
					
				default:
					// ACCOUNT_INVALID
					// BALANCE_INSUFFICIENT
					// BALANCE_INVALID
					throw new IllegalStateException("Unintended account operation result: " + result);
			}
		});
		
		this.btn_back.addActionListener(e -> {
			this.runner.goToPanel(PanelType.ACCOUNT);
		});
	}

	@Override
	public void clearFieldsMessages() {
		this.fld_newPin.setText("");
		this.fld_oldPin.setText("");
		this.lbl_error.setText(" ");
	}
	
	@Override
	public void setDetails(Account account) {
		this.lbl_accNum.setText(String.format("#%s", account.getAccountNumber()));
		this.lbl_error.setText(" ");
	}
}
