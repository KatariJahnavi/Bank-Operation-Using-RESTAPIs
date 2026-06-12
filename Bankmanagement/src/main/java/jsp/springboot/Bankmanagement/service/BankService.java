package jsp.springboot.Bankmanagement.service;

import java.util.List;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Account;
import jsp.springboot.Bankmanagement.entity.Address;
import jsp.springboot.Bankmanagement.entity.Bank;
import jsp.springboot.Bankmanagement.exception.AddressMandatoryException;
import jsp.springboot.Bankmanagement.exception.BankNotFoundException;
import jsp.springboot.Bankmanagement.exception.ContactException;
import jsp.springboot.Bankmanagement.exception.ContactLengthException;
import jsp.springboot.Bankmanagement.exception.IfscCodeException;
import jsp.springboot.Bankmanagement.exception.PincodeLengthException;
import jsp.springboot.Bankmanagement.repository.BankRepository;

@Service
public class BankService {
	@Autowired
	private BankRepository bankRepository;

	public ResponseStructure<Bank> saveBank(Bank bank) {
		
	    if (bankRepository.existsByIfscCode(bank.getIfscCode())) {
	        throw new IfscCodeException("Bank with IFSC code already exists: " + bank.getIfscCode());
	    }

	    if (bankRepository.existsByContact(bank.getContact())) {
	        throw new ContactException("Bank with contact number already exists: " + bank.getContact());
	    }
		if(bank.getAddress()==null ||bank.getAddress().isEmpty()) {
			throw new AddressMandatoryException("Address must exists to save the record");
		}
		if(String.valueOf(bank.getContact()).length()!=10) {
			throw new ContactLengthException("Contact must contain 10 digits.");
		}
		if(String.valueOf(bank.getAddress().getPincode()).length()!=6) {
			throw new PincodeLengthException("Pincode must contain 6 digits.");
		}
		bank.getAddress().setBank(bank);
		if (bank.getAccount() != null) {
	        for (Account account : bank.getAccount()) {
	            account.setBank(bank);
	        }
	    }

		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Record saved succesfully");
		res.setData(bankRepository.save(bank));
		return res;	
	}
	
	  // Get All Banks
    public ResponseStructure<List<Bank>> getAllBanks() {
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("All banks fetched successfully.");
        res.setData(bankRepository.findAll());
        return res;
    }
 
 // Get Bank By ID
    public ResponseStructure<Bank> getBankById(int id) {
        Optional<Bank> optional = bankRepository.findById(id);
        if (optional.isPresent()) {
            ResponseStructure<Bank> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Bank fetched successfully.");
            res.setData(optional.get());
            return res;
        } else {
            throw new BankNotFoundException("Bank not found with ID: " + id);
        }
    }

    // Delete Bank
    public ResponseStructure<String> deleteBank(int id) {
        Optional<Bank> optional = bankRepository.findById(id);
        if (optional.isPresent()) {
            Bank bank = optional.get();
            if (bank.getAccount() != null && !bank.getAccount().isEmpty()) {
                throw new RuntimeException("Cannot delete bank that has accounts.");
            }
            bankRepository.delete(bank);
            ResponseStructure<String> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Bank deleted successfully.");
            res.setData("Deleted bank with ID: " + id);
            return res;
        } else {
            throw new BankNotFoundException("Bank not found with ID: " + id);
        }
    }

    // Get Bank By Pagination
    public ResponseStructure<Page<Bank>> getBankByPagination(int page, int size) {
        Page<Bank> banks = bankRepository.findAll(PageRequest.of(page, size));
        ResponseStructure<Page<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks fetched with pagination.");
        res.setData(banks);
        return res;
    }

    // Get Bank By IFSC
    public ResponseStructure<Bank> getBankByIfsc(String ifscCode) {
        Optional<Bank> optional = bankRepository.findByIfscCode(ifscCode);
        if (optional.isPresent()) {
            ResponseStructure<Bank> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Bank fetched by IFSC.");
            res.setData(optional.get());
            return res;
        } else {
            throw new BankNotFoundException("Bank not found with IFSC: " + ifscCode);
        }
    }
    
 // Get Bank By Address ID
    public ResponseStructure<List<Bank>> getBankByAddress(int addressId) {
        List<Bank> banks = bankRepository.findByAddress_AddressId(addressId);
        if (banks.isEmpty()) {
            throw new BankNotFoundException("No banks found with Address ID: " + addressId);
        }
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks fetched by address.");
        res.setData(banks);
        return res;
    }

    // Get Bank By City
    public ResponseStructure<List<Bank>> getBankByCity(String city) {
        List<Bank> banks = bankRepository.findByAddress_City(city);
        if (banks.isEmpty()) {
            throw new BankNotFoundException("No banks found in city: " + city);
        }
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks fetched by city.");
        res.setData(banks);
        return res;
    }

    // Get Bank By Contact Number
    public ResponseStructure<List<Bank>> getBankByContact(long contact) {
        List<Bank> banks = bankRepository.findByContact(contact);
        if (banks.isEmpty()) {
            throw new BankNotFoundException("No banks found with contact: " + contact);
        }
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks fetched by contact.");
        res.setData(banks);
        return res;
    }
}
 
 


