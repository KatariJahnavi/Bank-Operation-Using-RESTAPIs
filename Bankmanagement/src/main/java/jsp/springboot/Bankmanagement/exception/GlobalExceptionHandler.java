package jsp.springboot.Bankmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Bank;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AddressMandatoryException.class)
	public ResponseEntity<ResponseStructure<String>> AddressMandatoryException(AddressMandatoryException e){
		ResponseStructure<String> res= new ResponseStructure<String>();
		res.setMessage(e.getMessage());
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setData("Failed to save the record");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_ACCEPTABLE);
	}
	
	 @ExceptionHandler(ContactLengthException.class)
	    public ResponseEntity<ResponseStructure<String>> handleContactLength(ContactLengthException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Invalid contact number");
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	    }
	 
	    @ExceptionHandler(PincodeLengthException.class)
	    public ResponseEntity<ResponseStructure<String>> handlePincodeLength(PincodeLengthException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Invalid pincode");
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	    }
	 
	    @ExceptionHandler(BankNotFoundException.class)
	    public ResponseEntity<ResponseStructure<String>> handleBankNotFound(BankNotFoundException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.NOT_FOUND.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Bank not found");
	        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	    }
	 
	    @ExceptionHandler(AccountNotFoundException.class)
	    public ResponseEntity<ResponseStructure<String>> handleAccountNotFound(AccountNotFoundException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.NOT_FOUND.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Account not found");
	        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	    }
	 
	    @ExceptionHandler(InsufficientBalanceException.class)
	    public ResponseEntity<ResponseStructure<String>> handleInsufficientBalance(InsufficientBalanceException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Insufficient balance");
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	    }
	 
	    @ExceptionHandler(InvalidAmountException.class)
	    public ResponseEntity<ResponseStructure<String>> handleInvalidAmount(InvalidAmountException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Invalid amount");
	        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	    }
	 
	    @ExceptionHandler(AddressNotFoundException.class)
	    public ResponseEntity<ResponseStructure<String>> handleAddressNotFound(AddressNotFoundException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.NOT_FOUND.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Address not found");
	        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
	    }
	 
	    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
	    public ResponseEntity<ResponseStructure<String>> handleAccountNumberExists(AccountNumberAlreadyExistsException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.CONFLICT.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Duplicate account number");
	        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	    }
	 
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ResponseStructure<String>> handleGeneral(Exception ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        res.setMessage("Something went wrong: " + ex.getMessage());
	        res.setData("Internal Server Error");
	        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    @ExceptionHandler(IfscCodeException.class)
	    public ResponseEntity<ResponseStructure<String>> handleIfscAlreadyExists(IfscCodeException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.CONFLICT.value());
	        res.setMessage(ex.getMessage());
	        res.setData("IFSC code must be unique for each bank.");
	        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	    }

	    @ExceptionHandler(ContactException.class)
	    public ResponseEntity<ResponseStructure<String>> handleContactAlreadyExists(ContactException ex) {
	        ResponseStructure<String> res = new ResponseStructure<>();
	        res.setStatusCode(HttpStatus.CONFLICT.value());
	        res.setMessage(ex.getMessage());
	        res.setData("Contact number must be unique for each bank.");
	        return new ResponseEntity<>(res, HttpStatus.CONFLICT);
	    }
	}
	 


