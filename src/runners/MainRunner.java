package runners;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import gui.panels.BankingPanel;
import gui.panels.PanelType;
import gui.panels._1_LoginPage;
import gui.panels._2_AccountPage;
import gui.panels._3_DepositPage;
import gui.panels._4_WithdrawPage;
import gui.panels._5_TransferPage;
import gui.panels._6_ChangePinPage;
import gui.panels._7_ClosurePage;

public class MainRunner extends JFrame {

	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	// Self
	private JPanel contentPane;
	private CardLayout card;
	// Back-end
	private AccountManager accountManager;
	private TransactionManager transactionManager;
	// Panels
	private Map<PanelType, BankingPanel> panels;
	
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
		Dimension dims = new Dimension(900, 600);
		setMinimumSize(dims);
		setSize(dims);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		card = new CardLayout();
		setContentPane(contentPane);
		contentPane.setLayout(card);
		
		// Back-end
		accountManager = new AccountManager();
		transactionManager = new TransactionManager();
		
		// Front-end
		this.panels = Map.ofEntries(
				Map.entry(PanelType.LOGIN, 		new _1_LoginPage(this)),
				Map.entry(PanelType.ACCOUNT,	new _2_AccountPage(this)),
				Map.entry(PanelType.DEPOSIT,	new _3_DepositPage(this)),
				Map.entry(PanelType.WITHDRAW,	new _4_WithdrawPage(this)),
				Map.entry(PanelType.TRANSFER,	new _5_TransferPage(this)),
				Map.entry(PanelType.CHANGE_PIN,	new _6_ChangePinPage(this)),
				Map.entry(PanelType.CLOSURE,	new _7_ClosurePage(this))
				);
		
		for (PanelType type: this.panels.keySet())
			contentPane.add(this.panels.get(type), type.toString());
		
		card.show(contentPane, PanelType.LOGIN.toString());
	}
	
	/*
	 * SERVICE METHODS
	 */
	public void goToPanel(PanelType panel) {
		this.resetFields();
		card.show(contentPane, panel.toString());
	}
	
	public void updateAccountDetails() {
		Account account = this.accountManager.getCurrAccount();
		for (BankingPanel panel : this.panels.values())
			panel.setDetails(account);
	}

	public void resetFields() {
		for (BankingPanel panel : this.panels.values())
			panel.clearFieldsMessages();
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
