package runners;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

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
import gui._4_WithdrawPage;
import gui._5_TransferPage;
import gui._6_ChangePinPage;
import gui.interfaces.Clearable;
import gui.interfaces.Settable;

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
	private List<JPanel> panels;
	private _1_LoginPage panelLogin;
	private _2_AccountPage panelAccount;
	private _3_DepositPage panelDeposit;
	private _4_WithdrawPage panelWithdraw;
	private _5_TransferPage panelTransfer;
	private _6_ChangePinPage panelChangePin;
	
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
		panelWithdraw = new _4_WithdrawPage(this);
		panelTransfer = new _5_TransferPage(this);
		panelChangePin = new _6_ChangePinPage(this);
		
		this.panels = List.of(
				panelLogin,
				panelAccount,
				panelDeposit,
				panelWithdraw,
				panelTransfer,
				panelChangePin);
		
		card.addLayoutComponent(this.panelLogin, PanelType.LOGIN);
		card.addLayoutComponent(this.panelAccount, PanelType.ACCOUNT);
		card.addLayoutComponent(this.panelDeposit, PanelType.DEPOSIT);
		card.addLayoutComponent(this.panelWithdraw, PanelType.WITHDRAW);
		card.addLayoutComponent(this.panelTransfer, PanelType.TRANSFER);
		card.addLayoutComponent(this.panelChangePin, PanelType.CHANGE_PIN);
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
		for (Component panel : this.panels) {
			if (panel instanceof Settable)
				((Settable) panel).setDetails(account);
		}
	}

	public void resetFields() {
		for (Component comp : this.panels) {
			if (comp instanceof Clearable)
				((Clearable) comp).clearFieldsAndMsgs();
		}
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
