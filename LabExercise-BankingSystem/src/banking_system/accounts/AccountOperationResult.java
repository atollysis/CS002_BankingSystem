package banking_system.accounts;

public enum AccountOperationResult {
	SUCCESS,
	ACCOUNT_INVALID,		// recipient account doesn't exist
	
	BALANCE_INVALID,		// <= 0
	BALANCE_INSUFFICIENT,	// amount > balance
	
	PIN_INVALID,
	PIN_WRONG;
	
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
