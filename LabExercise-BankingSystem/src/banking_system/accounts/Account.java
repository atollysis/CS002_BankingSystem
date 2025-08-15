package banking_system.accounts;

import java.util.Objects;

public class Account {
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
	public Account(int id, boolean isClosed, String accountNumber, String pin, double balance) {
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
	
	public boolean hasEmptyBalance() {
		return this.balance < 0.01;
	}

	public AccountOperationResult withdraw(double amount) {
		// Guard clauses
		if (amount <= 0)
			return AccountOperationResult.BALANCE_INVALID;
		if (amount > this.balance)
			return AccountOperationResult.BALANCE_INSUFFICIENT;

		this.balance -= amount;
		return AccountOperationResult.SUCCESS;
	}

	public AccountOperationResult deposit(double amount) {
		// Guard clause
		if (amount <= 0)
			return AccountOperationResult.BALANCE_INVALID;

		this.balance += amount;
		return AccountOperationResult.SUCCESS;
	}

	public AccountOperationResult transfer(Account targetAccount, double amount) {
		// Guard clauses
		if (targetAccount == null)
			return AccountOperationResult.ACCOUNT_INVALID;
		if (targetAccount.isClosed)
			return AccountOperationResult.ACCOUNT_INVALID;
		if (amount <= 0)
			return AccountOperationResult.BALANCE_INVALID;
		if (amount > this.balance)
			return AccountOperationResult.BALANCE_INSUFFICIENT;
		
		this.balance -= amount;
		targetAccount.balance += amount;
		return AccountOperationResult.SUCCESS;
	}
	
	public AccountOperationResult modifyPin(String oldPin, String newPin) {
		if (oldPin == null || newPin == null)
			return AccountOperationResult.PIN_INVALID;
		if (!AccountManager.isValidPin(newPin))
			return AccountOperationResult.PIN_INVALID;
		if (!this.verify(oldPin))
			return AccountOperationResult.PIN_WRONG;
		
		this.pin = newPin;
		return AccountOperationResult.SUCCESS;
	}
	
	public AccountOperationResult closeAccount() {
		if (!this.hasEmptyBalance())
			return AccountOperationResult.BALANCE_EXISTS;
		
		this.isClosed = true;
		return AccountOperationResult.SUCCESS;
	}
	
	/*
	 * GETTERS
	 */
	public int getID() {
		return this.id;
	}
	
	public boolean isClosed() {
		return this.isClosed;
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public String getCSVString() {
		return String.format("%s,%b,%s,%s,%.2f\n",
				this.id,
				this.isClosed,
				this.accountNumber,
				this.pin,
				this.balance
				);
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

		Account other = (Account) obj;

		return this.id == other.id
			&& this.accountNumber.equals(other.accountNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.accountNumber);
	}
}
