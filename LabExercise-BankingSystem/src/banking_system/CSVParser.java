package banking_system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import banking_system.enums.ManagerOperationResult;

public class CSVParser {
	/*
	 * ATTRIBUTES
	 */
	private static final String FILE_PATH = "data/accounts.csv";
	
	/*
	 * ALL STATIC METHODS
	 */
	public static Set<BankAccount> loadFile() {
		Set<BankAccount> output = new HashSet<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))
				) {
			reader.readLine(); // consume header
			String[] row = reader.readLine().split(",");
			BankAccount currAccount = new BankAccount(
					Integer.parseInt(row[0]),
					row[1].toLowerCase().equals("true") ? true : false,
					row[2],
					row[3],
					Double.parseDouble(row[4]));
			output.add(currAccount);
			
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
	
	public static ManagerOperationResult updateFile(Set<BankAccount> accounts) {
		List<BankAccount> sortedAccounts = new ArrayList<>(accounts);
		Collections.sort(sortedAccounts);
		
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			writer.write("id,closed,accountNumber,pin,balance\n");
			
			for (BankAccount account : accounts)
				writer.write(account.getCSVString());
			
			return ManagerOperationResult.SUCCESS;
			
		} catch (IOException e) {
			e.printStackTrace();
			return ManagerOperationResult.IO_PROBLEM;
		}
	}
}
