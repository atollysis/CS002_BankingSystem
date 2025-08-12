package banking_system.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import banking_system.accounts.AccountOperationResult;
import banking_system.accounts.Account;
import banking_system.accounts.ManagerOperationResult;
import banking_system.transactions.Transaction;
import banking_system.transactions.TransactionType;

public class CSVParser {
	/*
	 * ATTRIBUTES
	 */
	private static final String ACCOUNTS_FILE_PATH = "data/accounts.csv";
	private static final String TRANSACTIONS_FILE_PATH = "data/transactions.csv";
	
	private static final String ACCOUNTS_HEADER = "id,closed,accountNumber,pin,balance\n";
	private static final String TRANSACTIONS_HEADER = "id,timestamp,transactionType,accountId,amount,recipientId,status\n";
	
	/*
	 * ACCOUNT METHODS
	 */
	public static Set<Account> loadAccounts() {
		Set<Account> output = new HashSet<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE_PATH))
				) {
			String row = reader.readLine(); // consume header
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				Account currAccount = new Account(
						Integer.parseInt(values[0]),		// ID
						values[1].toLowerCase().equals("true") ? true : false, // isClosed
						values[2],							// Account Number
						values[3],							// Pin
						Double.parseDouble(values[4])		// Balance
						);
				output.add(currAccount);
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Error in processing id/balance.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static ManagerOperationResult updateAccounts(Set<Account> accounts) {
		List<Account> sortedAccounts = new ArrayList<>(accounts);
		sortedAccounts.sort(Comparator.comparingInt(Account::getID));
		
		try (FileWriter writer = new FileWriter(ACCOUNTS_FILE_PATH)) {
			writer.write(ACCOUNTS_HEADER);
			
			for (Account account : sortedAccounts)
				writer.write(account.getCSVString());
			
			return ManagerOperationResult.SUCCESS;
			
		} catch (IOException e) {
			e.printStackTrace();
			return ManagerOperationResult.IO_PROBLEM;
		}
	}
	
	/*
	 * TRANSACTION METHODS
	 */
	public static Set<Transaction> loadTransactions() {
		Set<Transaction> output = new HashSet<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE_PATH))
				) {
			String row = reader.readLine(); // consume header
			while ((row = reader.readLine()) != null) {
				String[] values = row.split(",");
				TransactionType type = TransactionType.convert(values[2]);
				Transaction currTransaction = new Transaction(
						Integer.parseInt(values[0]),		// ID
						LocalDateTime.parse(values[1]),		// Timestamp
						type,	// Transaction Type
						CSVParser.checkID(values[3]),					// Account ID
						Double.parseDouble(values[4]),		// Amount
						CSVParser.parseRecipientID(values[5], type),		// Recipient ID (nullable)
						AccountOperationResult.convert(values[6]) // Status
						);
				output.add(currTransaction);
			}
			
		} catch (NumberFormatException e) {
			System.out.println("Error in processing id/balance.");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	private static boolean isAllDigits(String string) {
		return string.chars().anyMatch(Character::isDigit);
	}
	
	private static String checkID(String id) throws IOException {
		if (!isAllDigits(id))
			throw new IOException("Account ID contains non-digits: " + id);
		return id;
	}
	
	private static String parseRecipientID(String value, TransactionType type) throws IOException {
		if (type == TransactionType.TRANSFER)
			CSVParser.checkID(value);
		return value.toLowerCase().equals("null") ? null : value.toLowerCase();
	}
	
	public static ManagerOperationResult updateTransactions(Set<Transaction> transactions) {
		List<Transaction> sortedTransactions = new ArrayList<>(transactions);
		sortedTransactions.sort(Comparator.comparingInt(Transaction::getID));
		
		try (FileWriter writer = new FileWriter(TRANSACTIONS_FILE_PATH)) {
			writer.write(TRANSACTIONS_HEADER);
			
			for (Transaction transaction : sortedTransactions)
				writer.write(transaction.getCSVString());
			
			return ManagerOperationResult.SUCCESS;
			
		} catch (IOException e) {
			e.printStackTrace();
			return ManagerOperationResult.IO_PROBLEM;
		}
	}
}
