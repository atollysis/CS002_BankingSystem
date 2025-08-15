package gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import banking_system.accounts.Account;
import runners.MainRunner;

public class _7_ClosurePage extends BankingPanel {
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
		
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
		super(runner);
	}
	
	/*
	 * SUPPORT METHODS
	 */
	@Override
	protected void setupComponents() {
		this.lbl_title = new JLabel("CLOSE ACCOUNT");
		lbl_title.setFont(new Font("Arial", Font.BOLD, 14));
		
		this.lbl_prompt1 = new JLabel("Are you sure you want to close account #1234?");
		this.lbl_prompt2 = new JLabel("This cannot be undone.");
		this.btn_yes = new JButton("Yes");
		this.btn_no = new JButton("No");
	}

	@Override
	protected void setupLayout() {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		this.add(lbl_title,   BankingPanel.newCenterLabelConstraint(0, 0, 20));
		this.add(lbl_prompt1, BankingPanel.newCenterLabelConstraint(0, 1, 0));
		this.add(lbl_prompt2, BankingPanel.newCenterLabelConstraint(0, 2, 0));
		this.add(btn_yes, 	       BankingPanel.newButtonConstraint(0, 3));
		this.add(btn_no,           BankingPanel.newButtonConstraint(1, 3));
	}

	@Override
	protected void setupInteractions() {
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
	public void clearFieldsMessages() {
		// None
	}

	@Override
	public void setDetails(Account account) {
		this.lbl_prompt1.setText(String.format(
				"Are you sure you want to close account #%s?",
				this.accountManager.getCurrAccount().getAccountNumber()));
	}
}
