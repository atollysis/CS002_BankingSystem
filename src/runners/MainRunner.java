package runners;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banking_system.accounts.Account;
import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import gui.interfaces.Clearable;
import gui.interfaces.Settable;
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
	private Map<PanelType, JPanel> panels;
	private _1_LoginPage panelLogin;
	private _2_AccountPage panelAccount;
	private _3_DepositPage panelDeposit;
	private _4_WithdrawPage panelWithdraw;
	private _5_TransferPage panelTransfer;
	private _6_ChangePinPage panelChangePin;
	private _7_ClosurePage panelClosure;
	
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
		
		accountManager = new AccountManager();
		transactionManager = new TransactionManager();
		
		panelLogin		= new _1_LoginPage(this);
		panelAccount	= new _2_AccountPage(this);
		panelDeposit	= new _3_DepositPage(this);
		panelWithdraw	= new _4_WithdrawPage(this);
		panelTransfer	= new _5_TransferPage(this);
		panelChangePin	= new _6_ChangePinPage(this);
		panelClosure	= new _7_ClosurePage(this);
		
		this.panels = Map.ofEntries(
				Map.entry(PanelType.LOGIN, 		panelLogin),
				Map.entry(PanelType.ACCOUNT,	panelAccount),
				Map.entry(PanelType.DEPOSIT,	panelDeposit),
				Map.entry(PanelType.WITHDRAW,	panelWithdraw),
				Map.entry(PanelType.TRANSFER,	panelTransfer),
				Map.entry(PanelType.CHANGE_PIN,	panelChangePin),
				Map.entry(PanelType.CLOSURE,	panelClosure)
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
		if (panel == PanelType.ACCOUNT)
			this.setAccountId();
		card.show(contentPane, panel.toString());
	}
	
	public void updateAccountDetails() {
		Account account = this.accountManager.getCurrAccount();
		for (Component panel : this.panels.values()) {
			if (panel instanceof Settable)
				((Settable) panel).setDetails(account);
		}
	}

	public void resetFields() {
		for (Component comp : this.panels.values()) {
			if (comp instanceof Clearable)
				((Clearable) comp).clearFieldsAndMsgs();
		}
	}
	
	public void setAccountId() {
		int id = this.accountManager.getCurrAccount().getID();
		this.transactionManager.setAccountID(Integer.toString(id));
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
