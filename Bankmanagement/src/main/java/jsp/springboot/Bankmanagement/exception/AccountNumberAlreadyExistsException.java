package jsp.springboot.Bankmanagement.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException {

	public AccountNumberAlreadyExistsException(String message) {
		super(message);
		
	}
}