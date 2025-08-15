package banking_system.accounts;

public enum ManagerOperationResult {
	SUCCESS,
	
	INVALID_ACCOUNT_NONEXISTENT,
	INVALID_ACCOUNT_EXISTS,			// For creation
	INVALID_ACCOUNT_FORMAT,
	INVALID_ACCOUNT_CLOSED,			// For logging in
	INVALID_ACCOUNT_NOT_CLOSED,		// For closure
	INVALID_ACCOUNT_CLOSURE,		// Balance exists
	
	INVALID_PIN_WRONG,
	INVALID_PIN_FORMAT,
	
	IO_PROBLEM;
}
