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
	private final SessionDetails details;
	
	/*
	 * CONSTRUCTOR
	 */
	public Transaction(
			int id,
			LocalDateTime timestamp,
			SessionDetails details) {
		this.id = id;
		this.timestamp = timestamp;
		this.details = details;
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
				this.details.getTransactionType().toString(),
				this.details.getAccountID(),
				this.details.getAmount(),
				this.details.getRecipientID(),
				this.details.getStatus().toString());
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
