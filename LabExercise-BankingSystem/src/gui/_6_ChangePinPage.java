package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.accounts.AccountOperationResult;
import banking_system.transactions.TransactionManager;
import gui.interfaces.Clearable;
import gui.interfaces.Settable;
import runners.MainRunner;

public class _6_ChangePinPage extends JPanel implements Settable, Clearable {
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
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	private void setupComponents() {
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
	
	private void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		JPanel fieldWrapper = new JPanel();
		fieldWrapper.setLayout(new GridBagLayout());
		fieldWrapper.add(lbl_promptOldPin,   newFieldLabelConstraint(0, 0));
		fieldWrapper.add(fld_oldPin,              newFieldConstraint(1, 0));
		fieldWrapper.add(lbl_promptNewPin,  newFieldLabelConstraint(0, 1));
		fieldWrapper.add(fld_newPin,             newFieldConstraint(1, 1));
		
		this.add(lbl_title,   newLabelConstraint(0, 0, 0));
		this.add(lbl_accNum,  newLabelConstraint(0, 1, 20));
		this.add(lbl_error,   newLabelConstraint(0, 2, 0));
		this.add(fieldWrapper, newWrapperConstraints(0, 3));
		this.add(btn_changePin, newButtonConstraint(0, 4));
		this.add(btn_back,      newButtonConstraint(1, 4));
	}

	private static GridBagConstraints newWrapperConstraints(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 0, 0);
		return c;
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
	
	private static GridBagConstraints newFieldLabelConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0, 0, 0);
		return c;
	}
	
	private static GridBagConstraints newFieldConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c;
	}
	
	
	private static GridBagConstraints newButtonConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c; 
	}
	
	private void setupInteractions() {
		this.btn_changePin.addActionListener(e -> {
			AccountOperationResult result = this.accountManager.getCurrAccount().modifyPin(
					String.valueOf(this.fld_oldPin.getPassword()),
					String.valueOf(this.fld_newPin.getPassword()));
			this.transactionManager.setPinChangeTransaction();
			this.transactionManager.setStatus(result);
			this.runner.setAccountId();
			
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
	}
	
	@Override
	public void clearFieldsAndMsgs() {
		this.fld_oldPin.setText("");
		this.fld_newPin.setText("");
		this.lbl_error.setText(" ");
	}
}
