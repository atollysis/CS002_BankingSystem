package banking_system.transactions;

public enum TransactionType {
	DEPOSIT,
	WITHDRAW,
	TRANSFER,
	ACCOUNT_CREATION,
	ACCOUNT_CLOSURE;
	
	/*
	 * SERVICE METHODS
	 */
	public static TransactionType convert(String type) {
		for (TransactionType currType : TransactionType.values()) {
			if (currType.toString().toLowerCase().equals(type.toLowerCase()))
				return currType;
		}
		throw new IllegalArgumentException("Input " + type + " does not match with existing transaction types.");
	}
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
