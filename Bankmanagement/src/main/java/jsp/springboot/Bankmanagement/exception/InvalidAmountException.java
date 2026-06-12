package jsp.springboot.Bankmanagement.exception;

public class InvalidAmountException extends RuntimeException {

	public InvalidAmountException(String message) {
		super(message);
		
	}
}
