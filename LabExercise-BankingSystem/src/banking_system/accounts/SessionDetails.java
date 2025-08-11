package banking_system.accounts;

import banking_system.transactions.TransactionType;

public class SessionDetails {
	/*
	 * ATTRIBUTES
	 */
	private TransactionType transactionType;
	private String accountID;
	private double amount;
	private String recipientID;
	private AccountOperationResult status;
	
	/*
	 * DEFAULT CONSTRUCTOR USED
	 */
	
	/*
	 * GETTERS
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public String getAccountID() {
		return accountID;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getRecipientID() {
		return recipientID;
	}
	
	public AccountOperationResult getStatus() {
		return status;
	}
	
	/*
	 * SETTERS
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void setRecipientID(String recipientID) {
		this.recipientID = recipientID;
	}
	
	public void setStatus(AccountOperationResult status) {
		this.status = status;
	}
}
