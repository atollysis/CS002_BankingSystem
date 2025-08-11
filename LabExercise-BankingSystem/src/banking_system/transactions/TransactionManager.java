package banking_system.transactions;

import java.time.LocalDateTime;
import java.util.Set;

import banking_system.accounts.Account;
import banking_system.accounts.AccountOperationResult;
import banking_system.accounts.SessionDetails;
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
	}
	
	/*
	 * SUPPORT METHODS
	 */
	public void lazyLoadTransactions() {
		if (transactions == null)
			transactions = CSVParser.loadTransactions();
	}
	
	/*
	 * SERVICE METHODS
	 */
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
		this.clearSessionDetails();
	}
	
	public void updateCSVFile() {
		CSVParser.updateTransactions(transactions);
	}
	
	/*
	 * SETTERS
	 */
	public void clearSessionDetails() {
		this.details = new SessionDetails();
	}
	
	public void setTransactionType(TransactionType type) {
		this.details.setTransactionType(type);
	}
	
	public void setAccountID(String id) {
		this.details.setAccountID(id);
	}
	
	public void setAmount(int amount) {
		this.details.setAmount(amount);
	}
	
	public void setRecipientID(String id) {
		this.details.setRecipientID(id);
	}
	
	public void setStatus(AccountOperationResult status) {
		this.details.setStatus(status);
	}
	
	public void setPinChangeTransaction() {
		details.setTransactionType(TransactionType.CHANGE_PIN);
//		details.setAccountID(); // set beforehand
		details.setAmount(0.0);
		details.setRecipientID(null);
	}
	
	public void setAccountCreationTransaction() {
		details.setTransactionType(TransactionType.ACCOUNT_CREATION);
//		details.setAccountID(); // set beforehand
		details.setAmount(0.0);
		details.setRecipientID(null);
		// Specify success since it's created on the login page
		details.setStatus(AccountOperationResult.SUCCESS);
	}
	
	public void setAccountClosureTransaction() {
		details.setTransactionType(TransactionType.ACCOUNT_CLOSURE);
//		details.setAccountID(); // set beforehand
		details.setAmount(0.0);
		details.setRecipientID(null);
	}
}
