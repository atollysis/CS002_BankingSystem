package runners;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import banking_system.accounts.AccountManager;
import banking_system.transactions.TransactionManager;
import gui.PanelType;
import gui._1_LoginPage;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(card);
		
		accountManager = new AccountManager();
		transactionManager = new TransactionManager();
		
		panelLogin = new _1_LoginPage(this, accountManager);
		
		card.addLayoutComponent(this.panelLogin, PanelType.LOGIN);
	}
	
	/*
	 * SERVIC METHODS
	 */
	public void goToPanel(PanelType panel) {
		card.show(contentPane, panel.toString());
	}

}
