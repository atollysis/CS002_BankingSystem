package banking_system.accounts;

import java.util.List;
import java.util.Set;

import banking_system.parsers.CSVParser;
import banking_system.transactions.TransactionType;

public class AccountManager {
	/*
	 * ATTRIBUTES
	 */
	private static Set<Account> accounts;
	private Account currentAccount;

	/*
	 * CONSTRUCTOR
	 */
	public AccountManager() {
		AccountManager.accounts = CSVParser.loadAccounts();
		this.currentAccount = null;
	}

	/*
	 * SUPPORT METHODS
	 */
	private static boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber.length() < 4)
			return false;
		
		for (char ch : accountNumber.toCharArray()) {
			if (!Character.isDigit(ch))
				return false;
		}
		return true;
	}
	
	/*
	 * SERVICE METHODS
	 */
	public static boolean isValidPin(String pin) {
		if (pin.length() < 4)
			return false;
		
		for (char ch : pin.toCharArray()) {
			if (!Character.isDigit(ch))
				return false;
		}
		return true;
	}
	
	public static Account findAccount(String accountNumber) {
		if (AccountManager.accounts == null)
			throw new IllegalStateException("Account list has not been initialized.");
		if (AccountManager.accounts.isEmpty())
			throw new IllegalStateException("Account list is empty.");
		
		List<Account> result = AccountManager.accounts
				.stream()
				.filter(acc -> acc.getAccountNumber().equals(accountNumber))
				.toList();
		if (result.size() != 1)
			return null;
		else
			return result.get(0);
	}
	
	public static boolean accountNumberExists(String accountNumber) {
		return AccountManager.findAccount(accountNumber) != null;
	}
	
	public ManagerOperationResult createAccount(String accountNumber, String pin) {
		if (AccountManager.accountNumberExists(accountNumber))
			return ManagerOperationResult.INVALID_ACCOUNT_EXISTS;
		if (!AccountManager.isValidAccountNumber(accountNumber))
			return ManagerOperationResult.INVALID_ACCOUNT_FORMAT;
		if (!AccountManager.isValidPin(pin))
			return ManagerOperationResult.INVALID_PIN_FORMAT;
		
		Account newAccount = new Account(
				AccountManager.accounts.size(),
				false,
				accountNumber,
				pin,
				0.0);
		AccountManager.accounts.add(newAccount);
		this.currentAccount = newAccount;
		CSVParser.updateAccounts(AccountManager.accounts);
		return ManagerOperationResult.SUCCESS;
	}
	
	public ManagerOperationResult closeAccount() {
		this.currentAccount.closeAccount();
		return CSVParser.updateAccounts(AccountManager.accounts);
	}
	
	public ManagerOperationResult login(String accountNumber, String pin) {
		if (!AccountManager.isValidAccountNumber(accountNumber))
			return ManagerOperationResult.INVALID_ACCOUNT_FORMAT;
		if (!AccountManager.isValidPin(pin))
			return ManagerOperationResult.INVALID_PIN_FORMAT;
		
		Account account = AccountManager.findAccount(accountNumber);
		if (account == null)
			return ManagerOperationResult.INVALID_ACCOUNT_NONEXISTENT;
		if (account.isClosed())
			return ManagerOperationResult.INVALID_ACCOUNT_CLOSED;
		if (!account.verify(pin))
			return ManagerOperationResult.INVALID_PIN_WRONG;
		
		this.currentAccount = account;
		return ManagerOperationResult.SUCCESS;
	}
	
	public void logout() {
		this.currentAccount = null;
	}
	
	/*
	 * GETTERS
	 */
	public Account getCurrAccount() {
		return this.currentAccount;
	}
	
	/*
	 * SETTERS
	 */
	public void setCurrAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
}
