package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import gui.interfaces.Settable;
import runners.MainRunner;

public class _7_ClosurePage extends JPanel implements Settable {
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

	private JLabel lbl_prompt1;
	private JLabel lbl_prompt2;
	private JButton btn_yes;
	private JButton btn_no;

	/**
	 * Create the panel.
	 */
	public _7_ClosurePage(MainRunner runner) {
		this.runner = runner;
		this.accountManager = runner.getAccountManager();
		this.transactionManager = runner.getTransactionManager();
		
		this.setupComponents();
		this.setupLayout();
		this.setupInteractions();
	}
	
	private void setupComponents() {
		this.lbl_title = new JLabel("CLOSE ACCOUNT");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		
		this.lbl_prompt1 = new JLabel("Are you sure you want to close account #1234?");
		this.lbl_prompt2 = new JLabel("This cannot be undone.");
		this.btn_yes = new JButton("Yes");
		this.btn_no = new JButton("No");
	}
	
	private void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		this.add(lbl_title,   newLabelConstraint(0, 0, 20));
		this.add(lbl_prompt1,  newLabelConstraint(0, 1, 0));
		this.add(lbl_prompt2,  newLabelConstraint(0, 2, 0));
		this.add(btn_yes, 	newButtonConstraint(0, 3));
		this.add(btn_no,    newButtonConstraint(1, 3));
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
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		return c; 
	}
	
	private void setupInteractions() {
		this.btn_yes.addActionListener(e -> {
			this.runner.setAccountId();
			this.transactionManager.setAccountClosureTransaction();
			this.transactionManager.addNewTransaction();
			
			this.accountManager.closeAccount();
			this.accountManager.logout();
			
			this.runner.goToPanel(PanelType.LOGIN);
		});
		
		this.btn_no.addActionListener(e -> {
			this.runner.goToPanel(PanelType.ACCOUNT);
		});
	}
	
	/*
	 * SERVICE METHODS
	 */
	@Override
	public void setDetails(Account account) {
		this.lbl_prompt1.setText(String.format(
				"Are you sure you want to close account #%s?",
				this.accountManager.getCurrAccount().getAccountNumber()));
	}

}
