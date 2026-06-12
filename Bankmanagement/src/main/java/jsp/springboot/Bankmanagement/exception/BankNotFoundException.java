package jsp.springboot.Bankmanagement.exception;

public class BankNotFoundException extends RuntimeException {

	public BankNotFoundException(String message) {
		super(message);
		
	}

}
