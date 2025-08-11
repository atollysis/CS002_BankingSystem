package runners;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import gui.PanelType;
import gui._1_LoginPage;
import gui._2_AccountPage;
import gui._3_DepositPage;

public class MainRunner extends JFrame {

	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	// Self
	private JPanel contentPane;
	private CardLayout card = new CardLayout(0, 0);
	// Back-end
	private AccountManager accountManager;
	private TransactionManager transactionManager;
	// Panels
	private _1_LoginPage panelLogin;
	private _2_AccountPage panelAccount;
	private _3_DepositPage panelDeposit;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainRunner frame = new MainRunner();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainRunner() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Dimension dims = new Dimension(600, 850);
		setMinimumSize(dims);
		setSize(dims);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(card);
		
		accountManager = new AccountManager();
		transactionManager = new TransactionManager();
		
		panelLogin = new _1_LoginPage(this);
		panelAccount = new _2_AccountPage(this);
		panelDeposit = new _3_DepositPage(this);
		
		card.addLayoutComponent(this.panelLogin, PanelType.LOGIN);
		card.addLayoutComponent(this.panelAccount, PanelType.ACCOUNT);
		card.addLayoutComponent(this.panelDeposit, PanelType.DEPOSIT);
	}
	
	/*
	 * SERVICE METHODS
	 */
	public void goToPanel(PanelType panel) {
		card.show(contentPane, panel.toString());
	}
	
	public void updateAccountDetails() {
		Account account = this.accountManager.getCurrAccount();
		this.panelAccount.setDetails(account);
		this.panelDeposit.setDetails(account);
	}

	/*
	 * GETTER
	 */
	public AccountManager getAccountManager() {
		return this.accountManager;
	}
	
	public TransactionManager getTransactionManager() {
		return this.transactionManager;
	}
}
