package banking_system.transactions;

import java.time.LocalDateTime;
import java.util.Set;

import banking_system.accounts.Account;
import banking_system.accounts.AccountOperationResult;
import banking_system.parsers.CSVParser;

public class TransactionManager {
	/*
	 * ATTRIBUTES
	 */
	private static Set<Transaction> transactions;
	private SessionDetails details;
	
	/*
	 * CONSTRUCTOR
	 */
	public TransactionManager() {
		this.lazyLoadTransactions();
		this.resetSessionDetails();
	}
	
	/*
	 * SUPPORT METHODS
	 */
	private static void nullCheck(Account account) {
		if (account == null)
			throw new NullPointerException("Account is null.");
	}
	
	/*
	 * SERVICE METHODS
	 */
	public void lazyLoadTransactions() {
		if (transactions == null)
			transactions = CSVParser.loadTransactions();
	}
	
	public void addNewTransaction() {
		this.addNewTransaction(this.details);
	}
	
	public void addNewTransaction(SessionDetails details) {
		this.lazyLoadTransactions();
		if (details == null)
			throw new NullPointerException("Session details are null.");
		
		Transaction newTransaction = new Transaction(
				transactions.size() + 1,	// ID
				LocalDateTime.now(),		// Timestamp
				details						// Transaction Type, ID, Amount, ID, Status
				);
		transactions.add(newTransaction);
		CSVParser.updateTransactions(transactions);
		this.resetSessionDetails();
	}
	
	public void updateCSVFile() {
		CSVParser.updateTransactions(transactions);
	}
	
	/*
	 * SETTERS
	 */
	public void resetSessionDetails() {
		this.details = new SessionDetails();
	}
	
	public void setAccountID(String id) {
		this.details.setAccountID(id);
	}
	
	public void setAmount(double amount) {
		this.details.setAmount(amount);
	}
	
	public void setStatus(AccountOperationResult status) {
		this.details.setStatus(status);
	}
	
	public void setDepositTransaction(Account account) {
		TransactionManager.nullCheck(account);
		details.setTransactionType(TransactionType.DEPOSIT);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(null);
	}
	
	public void setWithdrawTransaction(Account account) {
		TransactionManager.nullCheck(account);
		details.setTransactionType(TransactionType.WITHDRAW);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(null);
	}
	
	public void setTransferTransaction(Account account, Account recipientAccount) {
		TransactionManager.nullCheck(account);
		TransactionManager.nullCheck(recipientAccount);
		details.setTransactionType(TransactionType.TRANSFER);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(Integer.toString(recipientAccount.getID()));
	}
	
	public void setPinChangeTransaction(Account account) {
		TransactionManager.nullCheck(account);
		details.setTransactionType(TransactionType.CHANGE_PIN);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(null);
	}
	
	public void setAccountCreationTransaction(Account account) {
		TransactionManager.nullCheck(account);
		details.setTransactionType(TransactionType.ACCOUNT_CREATION);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(null);
		// Specify success since it's created on the login page
		details.setStatus(AccountOperationResult.SUCCESS);
	}
	
	public void setAccountClosureTransaction(Account account) {
		TransactionManager.nullCheck(account);
		details.setTransactionType(TransactionType.ACCOUNT_CLOSURE);
		details.setAccountID(Integer.toString(account.getID()));
		details.setAmount(0.0);
		details.setRecipientID(null);
		details.setStatus(AccountOperationResult.SUCCESS);
	}
}
