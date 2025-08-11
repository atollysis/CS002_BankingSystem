/**
 * {@code Transaction} is an immutable POJO storing all transactions in memory.
 */

package banking_system.transactions;

import java.time.LocalDateTime;
import java.util.Objects;

import banking_system.accounts.AccountOperationResult;
import banking_system.accounts.SessionDetails;

public class Transaction {
	/*
	 * ATTRIBUTES
	 */
	private final int id;
	private final LocalDateTime timestamp;
	private final TransactionType transactionType;
	private final String accountID;
	private final double amount;
	private final String recipientID;
	private final AccountOperationResult status;
	
	/*
	 * CONSTRUCTOR
	 */
	public Transaction(
			int id,
			LocalDateTime timestamp,
			TransactionType transactionType,
			String accountID,
			double amount,
			String recipientID,
			AccountOperationResult status) {
		this.id = id;
		this.timestamp = timestamp;
		this.transactionType = transactionType;
		this.accountID = accountID;
		this.amount = amount;
		this.recipientID = recipientID;
		this.status = status;
	}
	
	public Transaction(
			int id,
			LocalDateTime timestamp,
			SessionDetails details) {
		this.id = id;
		this.timestamp = timestamp;
		this.transactionType = details.getTransactionType();
		this.accountID = details.getAccountID();
		this.amount = details.getAmount();
		this.recipientID = details.getRecipientID();
		this.status = details.getStatus();
	}
	
	/*
	 * GETTERS
	 */
	public int getID() {
		return this.id;
	}
	
	public String getCSVString() {
		return String.format(
				"%d,%s,%s,%s,%.2f,%s,%s\n",
				this.id,
				this.timestamp.toString(),
				this.accountID,
				this.transactionType.toString(),
				this.accountID,
				this.amount,
				this.recipientID,
				this.status.toString());
	}
	
	/*
	 * OVERRIDDEN METHODS
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this.getClass() != obj.getClass())
			return false;
		
		Transaction other = (Transaction) obj;
		return this.id == other.id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}
}
