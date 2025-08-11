package miscellaneous;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class BankAccountGenerator {

	private static final int ACCOUNT_NUM_LENGTH = 4;
	private static final float MIN_BALANCE = 100.0f;
	private static final float MAX_BALANCE = 10000.0f;
	private static final int ROUND_CHANCE = 2; // invert for percentage
	
	public static void main(String[] args) {
		String path = "data/generator-outputs/accounts.csv";
		
		try (FileWriter writer = new FileWriter(path)) {
			writer.write("id,closed,accountNumber,pin,balance\n");
			
			for (int i = 1; i <= 1000; i++) {
				String row = String.format(
						"%d,%s,%s,%s,%.2f\n",
						i,
						"false",
						padNumber(i),
						padNumber(i),
						randomFloat()
						);
				writer.write(row);
			}
			
			System.out.println("File created successfully!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String padNumber(int num) {
		String output = Integer.toString(num);
		if (output.length() < ACCOUNT_NUM_LENGTH) {
			StringBuilder padded = new StringBuilder();
			while (padded.length() + output.length() < ACCOUNT_NUM_LENGTH)
				padded.append('0');
			output = padded.toString() + output;
		}
		return output;
	}
	
	private static float randomFloat() {
		float output = ThreadLocalRandom.current().nextFloat(MIN_BALANCE, MAX_BALANCE);
		if (ThreadLocalRandom.current().nextInt(ROUND_CHANCE) == 0)
			output = Math.round(output);
		return output;
	}
}
