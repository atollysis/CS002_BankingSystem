package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import banking_system.accounts.AccountManager;
import banking_system.accounts.ManagerOperationResult;
import banking_system.transactions.TransactionManager;
import runners.MainRunner;

public class _1_LoginPage extends JPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;

	// Controllers
	private final MainRunner runner;
	private final AccountManager accountManager;
	private final TransactionManager transactionManager;
	
	// Swing
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
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	private void setupComponents() {
		this.lbl_accNum = new JLabel("Account Number");
		this.fld_accNum = new JTextField();
		
		this.lbl_accPin = new JLabel("Pin");
		this.fld_accPin = new JPasswordField();
		
		this.lbl_error = new JLabel(" ");
		this.btn_login = new JButton("Login");
		this.btn_create = new JButton("Create Account");
	}

	private void setupLayout() {
		this.setLayout(new GridBagLayout());
		
		this.add(this.lbl_accNum, newLabelConstraint(0, 0));
		this.add(this.fld_accNum, newFieldConstraint(1, 0));
		this.add(this.lbl_accPin, newLabelConstraint(0, 1));
		this.add(this.fld_accPin, newFieldConstraint(1, 1));
		this.add(this.lbl_error,  newErrLabelConstraint(0, 2));
		this.add(this.btn_login,  newButtonConstraint(0, 3));
		this.add(this.btn_create, newButtonConstraint(1, 3));
	}
	
	private static GridBagConstraints newLabelConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(5, 10, 5, 5);
		return c;
	}
	
	private static GridBagConstraints newErrLabelConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 10, 5, 5);
		return c;
	}
	
	private static GridBagConstraints newFieldConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c;
	}
	
	private static GridBagConstraints newButtonConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.insets = new Insets(5, 5, 5, 5);
		return c; 
	}
	
	private void setupInteractions() {
		this.btn_login.addActionListener(e -> {
			ManagerOperationResult result = this.accountManager.login(
					this.fld_accNum.getText(),
					String.valueOf(this.fld_accPin.getPassword()));
			
			switch (result) {
				case SUCCESS:
					String id = this.accountManager.getCurrAccount().getAccountNumber();
					this.transactionManager.setAccountID(id);
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
			}
		});
	}
}
