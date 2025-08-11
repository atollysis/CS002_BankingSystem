package banking_system.transactions;

import java.time.LocalDateTime;
import java.util.Set;

import banking_system.accounts.SessionDetails;
import banking_system.parsers.CSVParser;

public class TransactionManager {
	/*
	 * ATTRIBUTES
	 */
	private static Set<Transaction> transactions;
	
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
	}
	
	public void updateCSVFile() {
		CSVParser.updateTransactions(transactions);
	}
}
