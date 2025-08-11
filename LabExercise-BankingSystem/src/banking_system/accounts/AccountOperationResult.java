package banking_system.accounts;

public enum AccountOperationResult {
	SUCCESS,
	INVALID_ACCOUNT,
	INVALID_AMOUNT,
	INSUFFICIENT_BALANCE;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
	public static AccountOperationResult convert(String result) {
		for (AccountOperationResult currResult : AccountOperationResult.values()) {
			if (currResult.toString().toLowerCase().equals(result.toLowerCase()))
				return currResult;
		}
		throw new IllegalArgumentException("Input " + result + " does not match with existing transaction types.");
	}
}
