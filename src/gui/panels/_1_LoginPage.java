package gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import banking_system.accounts.Account;
import banking_system.accounts.ManagerOperationResult;
import runners.MainRunner;

public class _1_LoginPage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	
	// Components
	private JLabel lbl_welcome;
	
	private JLabel lbl_accNum;
	private JTextField fld_accNum;
	
	private JLabel lbl_accPin;
	private JPasswordField fld_accPin;
	
	private JLabel lbl_error;
	private JButton btn_login;
	private JButton btn_create;
	
	/**
	 * Create the panel.
	 */
	public _1_LoginPage(MainRunner runner) {
		super(runner);
	}

	@Override
	protected void setupComponents() {
		this.lbl_welcome = new JLabel("Welcome!");
		lbl_welcome.setFont(new Font("Arial", Font.BOLD, 14));
		
		this.lbl_accNum = new JLabel("Account Number");
		this.fld_accNum = new JTextField();
		
		this.lbl_accPin = new JLabel("Pin");
		this.fld_accPin = new JPasswordField();
		
		this.lbl_error = new JLabel(" ");
		this.btn_login = new JButton("Login");
		this.btn_create = new JButton("Create Account");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		JPanel fieldWrapper = new JPanel();
		fieldWrapper.setLayout(new GridBagLayout());
		fieldWrapper.add(lbl_accNum, BankingPanel.newFieldLabelConstraint(0, 0));
		fieldWrapper.add(fld_accNum,      BankingPanel.newFieldConstraint(1, 0));
		fieldWrapper.add(lbl_accPin, BankingPanel.newFieldLabelConstraint(0, 1));
		fieldWrapper.add(fld_accPin,      BankingPanel.newFieldConstraint(1, 1));
		
		this.add(lbl_welcome,     BankingPanel.newCenterLabelConstraint(0, 0, 0));
		this.add(this.lbl_error,  BankingPanel.newCenterLabelConstraint(0, 1, 0));
		this.add(fieldWrapper,        BankingPanel.newWrapperConstraint(0, 2));
		this.add(this.btn_login,       BankingPanel.newButtonConstraint(0, 3));
		this.add(this.btn_create,      BankingPanel.newButtonConstraint(1, 3));
	}

	@Override
	protected void setupInteractions() {
		this.btn_login.addActionListener(e -> {
			ManagerOperationResult result = this.accountManager.login(
					this.fld_accNum.getText(),
					String.valueOf(this.fld_accPin.getPassword()));
			
			switch (result) {
				case SUCCESS:
					this.runner.updateAccountDetails();
					this.runner.goToPanel(PanelType.ACCOUNT);
					break;
					
				case INVALID_ACCOUNT_FORMAT:
					this.lbl_error.setText("Invalid account number.");
					break;
					
				case INVALID_PIN_FORMAT:
					this.lbl_error.setText("Invalid pin.");
					break;
					
				case INVALID_ACCOUNT_NONEXISTENT:
					this.lbl_error.setText("Account does not exist.");
					break;
					
				case INVALID_ACCOUNT_CLOSED:
					this.lbl_error.setText("Account is closed.");
					break;
					
				case INVALID_PIN_WRONG:
					this.lbl_error.setText("Invalid pin.");
					break;
					
				default:
					// IO_PROBLEM
					// INVALID_ACCOUNT_EXISTS
					throw new IllegalStateException("Unintended manager operation result: " + result);
			}
		});
		
		this.btn_create.addActionListener(e -> {
			ManagerOperationResult result = this.accountManager.createAccount(
					this.fld_accNum.getText(),
					String.valueOf(this.fld_accPin.getPassword()));
			
			switch (result) {
				case SUCCESS:
					// Create account creation transaction and inform the user
					String id = this.accountManager.getCurrAccount().getAccountNumber();
					this.transactionManager.setAccountID(id);
					this.transactionManager.setAccountCreationTransaction();
					this.transactionManager.addNewTransaction();
					this.accountManager.setCurrAccount(null);
					this.lbl_error.setText("Account succesfully created!");
					break;
					
				case INVALID_ACCOUNT_EXISTS:
					this.lbl_error.setText("Account number already taken.");
					break;
					
				case INVALID_ACCOUNT_FORMAT:
					this.lbl_error.setText("Invalid account number.");
					break;
					
				case INVALID_PIN_FORMAT:
					this.lbl_error.setText("Invalid pin. Must be all numbers.");
					break;
					
				default:
					// INVALID_ACCOUNT_NONEXISTENT,
					// INVALID_ACCOUNT_CLOSED,
					// INVALID_PIN_WRONG,
					// IO_PROBLEM;
					throw new IllegalStateException("Unintended manager operation result: " + result);
			}
		});
	}

	@Override
	public void clearFieldsMessages() {
		this.fld_accNum.setText("");
		this.fld_accPin.setText("");
		this.lbl_error.setText(" ");
	}

	@Override
	public void setDetails(Account account) {
		// None
	}
}
