package jsp.springboot.Bankmanagement.exception;

public class InsufficientBalanceException extends RuntimeException {

	public InsufficientBalanceException(String message) {
		super(message);
		
	}
}
