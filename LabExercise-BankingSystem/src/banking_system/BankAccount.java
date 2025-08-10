package banking_system;

import java.util.Objects;

import banking_system.enums.AccountOperationResult;

public class BankAccount implements Comparable<BankAccount> {
	/*
	 * ATTRIBUTES
	 */
	private int id;
	private boolean isClosed;
	private String accountNumber;
	private String pin;
	private double balance;

	/*
	 * CONSTRUCTOR
	 */
	public BankAccount(int id, boolean isClosed, String accountNumber, String pin, double balance) {
		this.id = id;
		this.isClosed = isClosed;
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
	}
	
	/*
	 * SERVICE METHODS
	 */
	public boolean verify(String pin) {
		return this.pin.equals(pin);
	}

	public AccountOperationResult withdraw(double amount) {
		// Guard clauses
		if (amount > 0)
			return AccountOperationResult.INVALID_AMOUNT;
		if (amount > this.balance)
			return AccountOperationResult.INSUFFICIENT_BALANCE;

		this.balance -= amount;
		return AccountOperationResult.SUCCESS;
	}

	public AccountOperationResult deposit(double amount) {
		// Guard clause
		if (amount > 0)
			return AccountOperationResult.INVALID_AMOUNT;

		this.balance += amount;
		return AccountOperationResult.SUCCESS;
	}

	public AccountOperationResult transfer(BankAccount targetAccount, double amount) {
		// Guard clauses
		if (targetAccount == null)
			return AccountOperationResult.INVALID_ACCOUNT;
		if (amount > 0)
			return AccountOperationResult.INVALID_AMOUNT;
		if (amount > this.balance)
			return AccountOperationResult.INSUFFICIENT_BALANCE;
		
		this.balance -= amount;
		targetAccount.balance += amount;
		return AccountOperationResult.SUCCESS;
	}
	
	/*
	 * GETTERS
	 */
	public boolean getClosed() {
		return this.isClosed;
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public String getCSVString() {
		return String.format("%s,%s,%s,%s,%.2f\n",
				this.id,
				this.isClosed,
				this.accountNumber,
				this.pin,
				this.balance
				);
	}

	/*
	 * SETTERS
	 */
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
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

		BankAccount other = (BankAccount) obj;

		return this.accountNumber.equals(other.accountNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.accountNumber);
	}

	@Override
	public int compareTo(BankAccount o) {
		return Integer.compare(this.id, o.id);
	}
}
