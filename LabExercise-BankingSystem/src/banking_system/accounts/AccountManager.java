package banking_system.accounts;

import java.util.List;
import java.util.Set;

import banking_system.parsers.CSVParser;

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
	
	private static Account findAccount(String accountNumber) {
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
	
	private static boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber.length() < 4)
			return false;
		
		for (char ch : accountNumber.toCharArray()) {
			if (!Character.isDigit(ch))
				return false;
		}
		return true;
	}
	
	private static boolean isValidPin(String pin) {
		if (pin.length() < 4)
			return false;
		
		for (char ch : pin.toCharArray()) {
			if (!Character.isDigit(ch))
				return false;
		}
		return true;
	}
	
	/*
	 * SERVICE METHODS
	 */
	public static boolean accountNumberExists(String accountNumber) {
		return AccountManager.findAccount(accountNumber) != null;
	}
	
	public ManagerOperationResult createAccount(String accountNumber, String pin) {
		if (!AccountManager.isValidAccountNumber(accountNumber))
			return ManagerOperationResult.INVALID_ACCOUNT_FORMAT;
		if (AccountManager.accountNumberExists(accountNumber))
			return ManagerOperationResult.INVALID_ACCOUNT_EXISTS;
		if (!AccountManager.isValidPin(pin))
			return ManagerOperationResult.INVALID_PIN_FORMAT;
		
		Account newAccount = new Account(
				AccountManager.accounts.size(),
				false,
				accountNumber,
				pin,
				0.0);
		AccountManager.accounts.add(newAccount);
		return ManagerOperationResult.SUCCESS;
	}
	
	public void deleteAccount() {
		//
	}
	
	public ManagerOperationResult modifyAccountPin(String oldPin, String newPin) {
		if (this.currentAccount == null)
			throw new IllegalStateException("Currently not logged in to any account.");
		
		if (!this.currentAccount.verify(oldPin))
			return ManagerOperationResult.INVALID_PIN_FORMAT;
		
		this.currentAccount.setPin(newPin);
		return ManagerOperationResult.SUCCESS;
	}
	
	public ManagerOperationResult login(String accountNumber, String pin) {
		Account account = AccountManager.findAccount(accountNumber);
		if (account == null)
			return ManagerOperationResult.INVALID_ACCOUNT_NONEXISTENT;
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
	public void setCurrAccout(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
}
