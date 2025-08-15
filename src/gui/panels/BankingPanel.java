package gui.panels;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import runners.MainRunner;

public abstract class BankingPanel extends JPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	
	protected MainRunner runner;
	protected AccountManager accountManager;
	protected TransactionManager transactionManager;
	
	/*
	 * CONSTRUCTOR
	 */
	public BankingPanel(MainRunner runner) {
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	/*
	 * SUPPORT METHODS
	 * 
	 * To be implemented by all inheriting panels.
	 */
	protected abstract void setupComponents();
	protected abstract void setupLayout();
	protected abstract void setupInteractions();
	
	public abstract void clearFieldsMessages();
	public abstract void setDetails(Account account);
	
	/*
	 * SERVICE METHODS
	 */
	public static GridBagConstraints newButtonConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c; 
	}
	
	public static GridBagConstraints newCenterLabelConstraint(int x, int y, int bottomInset) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, bottomInset, 0);
		return c;
	}
	
	public static GridBagConstraints newFieldConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.weightx = 1; // stretch horizontally to fill
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c;
	}
	
	public static GridBagConstraints newFieldLabelConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0, 0, 0, 0);
		return c;
	}
	
	public static GridBagConstraints newWrapperConstraint(int x, int y) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 0, 0);
		return c;
	}
}
