package miscellaneous;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionGenerator {
	/*
	 * ATTRIBUTES
	 */
	private static final Map<String, Double> TRANSACTION_WEIGHTS = Map.ofEntries(
			Map.entry("withdraw",				0.30),
			Map.entry("deposit",				0.30),
			Map.entry("transfer",				0.30),
			Map.entry("account_creation",		0.05),
			Map.entry("account_closure",		0.05)
			);
	private static final Map<String, Double> STATUS_WEIGHTS = Map.ofEntries(
			Map.entry("success",				0.50),
			Map.entry("balance_invalid",		0.30),
			Map.entry("account_invalid",		0.10),
			Map.entry("balance_insufficient",	0.10)
			);
	
	private static final int BALANCE_ROUND_CHANCE = 5;
	
	/*
	 * METHODS
	 */
	public static void main(String[] args) {
		String path = "data/generator-outputs/transactions.csv";
		LocalDateTime currTime = LocalDateTime.parse("2024-01-01T00:00:00");
		
		try (FileWriter writer = new FileWriter(path)) {
			
			writer.write("id,timestamp,transactionType,accountId,amount,recipientId,status\n");
			
			for (int i = 1; i <= 1000; i++) {
				currTime = currTime
						.plusSeconds(randomInt(30))
						.plusHours(randomInt(5))
						.plusDays(randomInt(2));
				
				String transactionType = randomTransactionType();
				Integer accountID = randomAccountID();
				// recipient id should only be for transfer types
				Integer recipientID = null;
				if (transactionType.equals("transfer")) {
					do {
						recipientID = randomAccountID();
					} while (recipientID == accountID);
				}
				// "invalid_account" should only be for transfer transaction types
				String status = randomStatus();
				if (status.equals("invalid_account") && !transactionType.equals("transfer"))
					transactionType = "transfer";
				// account creation and closures need to have 0.0 balance
				double balance = randomBalance();
				if (transactionType.equals("account_creation")
					|| transactionType.equals("account_closure")) {
					balance = 0.0;
				}
				
				writer.write(String.format(
						"%d,%s,%s,%d,%.2f,%d,%s\n",
						i,
						currTime.toString(),
						transactionType,
						accountID,
						balance,
						recipientID,
						status
						));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("File created successfully!");
	}
	
	private static int randomInt(int bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}
	
	private static double randomBalance() {
		double balance = ThreadLocalRandom.current().nextDouble(100, 10000);
		
		if (randomInt(BALANCE_ROUND_CHANCE) != 0)
			balance = Math.round(balance);
		
		return balance;
	}
	
	private static String randomTransactionType() {
		double roll = ThreadLocalRandom.current().nextDouble();
		double cumulativeChance = 0.0;
		
		for (String status : TRANSACTION_WEIGHTS.keySet()) {
			cumulativeChance += TRANSACTION_WEIGHTS.get(status);
			if (roll < cumulativeChance)
				return status;
		}
		
		return "success";
	}
	
	private static String randomStatus() {
		double roll = ThreadLocalRandom.current().nextDouble();
		double cumulativeChance = 0.0;
		
		for (String status : STATUS_WEIGHTS.keySet()) {
			cumulativeChance += STATUS_WEIGHTS.get(status);
			if (roll < cumulativeChance)
				return status;
		}
		
		return "success";
	}
	
	private static int randomAccountID() {
		return randomInt(1001);
	}
}
