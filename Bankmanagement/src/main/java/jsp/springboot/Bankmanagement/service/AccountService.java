package jsp.springboot.Bankmanagement.service;

import jsp.springboot.Bankmanagement.dto.AccountDto;
import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.dto.TransferDto;
import jsp.springboot.Bankmanagement.entity.Account;
import jsp.springboot.Bankmanagement.entity.Bank;
import jsp.springboot.Bankmanagement.exception.*;
import jsp.springboot.Bankmanagement.repository.AccountRepository;
import jsp.springboot.Bankmanagement.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    // Create Account
    public ResponseStructure<Account> createAccount(int bankId, Account account) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        if (optionalBank.isPresent()) {
            Optional<Account> existingAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
            if (existingAccount.isPresent()) {
                throw new AccountNumberAlreadyExistsException("Account number already exists: " + account.getAccountNumber());
            }
            account.setBank(optionalBank.get());
            ResponseStructure<Account> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Account created successfully.");
            res.setData(accountRepository.save(account));
            return res;
        } else {
            throw new BankNotFoundException("Bank not found with ID: " + bankId);
        }
    }

    // Get All Accounts
    public ResponseStructure<List<Account>> getAllAccounts() {
        ResponseStructure<List<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("All accounts fetched.");
        res.setData(accountRepository.findAll());
        return res;
    }

    // Get Account By ID
    public ResponseStructure<Account> getAccountById(int id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            ResponseStructure<Account> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Account fetched successfully.");
            res.setData(optional.get());
            return res;
        } else {
            throw new AccountNotFoundException("Account not found with ID: " + id);
        }
    }

    // Delete Account
    public ResponseStructure<String> deleteAccount(int id) {
        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isPresent()) {
            accountRepository.delete(optional.get());
            ResponseStructure<String> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Account deleted successfully.");
            res.setData("Deleted account with ID: " + id);
            return res;
        } else {
            throw new AccountNotFoundException("Account not found with ID: " + id);
        }
    }

    // Deposit Amount
    public ResponseStructure<Account> depositAmount(AccountDto dto) {
        if (dto.getAmount() <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        Optional<Account> optional = accountRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Account account = optional.get();
            account.setBalance(account.getBalance() + dto.getAmount());
            ResponseStructure<Account> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Amount deposited successfully.");
            res.setData(accountRepository.save(account));
            return res;
        } else {
            throw new AccountNotFoundException("Account not found with ID: " + dto.getId());
        }
    }

    // Withdraw Amount
    public ResponseStructure<Account> withdrawAmount(AccountDto dto) {
        if (dto.getAmount() <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        Optional<Account> optional = accountRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Account account = optional.get();
            if (dto.getAmount() > account.getBalance()) {
                throw new InsufficientBalanceException("Insufficient balance. Available: " + account.getBalance());
            }
            account.setBalance(account.getBalance() - dto.getAmount());
            ResponseStructure<Account> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Amount withdrawn successfully.");
            res.setData(accountRepository.save(account));
            return res;
        } else {
            throw new AccountNotFoundException("Account not found with ID: " + dto.getId());
        }
    }

    // Transfer Amount
    public ResponseStructure<String> transferAmount(TransferDto dto) {
        if (dto.getAmount() <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive.");
        }
        if (dto.getSenderId() == dto.getReceiverId()) {
            throw new InvalidAmountException("Sender and receiver cannot be the same.");
        }
        Optional<Account> optionalSender = accountRepository.findById(dto.getSenderId());
        Optional<Account> optionalReceiver = accountRepository.findById(dto.getReceiverId());
        if (!optionalSender.isPresent()) {
            throw new AccountNotFoundException("Sender account not found.");
        }
        if (!optionalReceiver.isPresent()) {
            throw new AccountNotFoundException("Receiver account not found.");
        }
        Account sender = optionalSender.get();
        Account receiver = optionalReceiver.get();
        if (dto.getAmount() > sender.getBalance()) {
            throw new InsufficientBalanceException("Sender has insufficient balance.");
        }
        sender.setBalance(sender.getBalance() - dto.getAmount());
        receiver.setBalance(receiver.getBalance() + dto.getAmount());
        accountRepository.save(sender);
        accountRepository.save(receiver);
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Transfer successful.");
        res.setData("Transferred " + dto.getAmount() + " from account " + dto.getSenderId() + " to " + dto.getReceiverId());
        return res;
    }

    // Get Accounts By Bank
    public ResponseStructure<List<Account>> getAccountsByBank(int bankId) {
        List<Account> accounts = accountRepository.findByBank_BankId(bankId);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Accounts fetched by bank.");
        res.setData(accounts);
        return res;
    }

    // Get Accounts By Account Type
    public ResponseStructure<List<Account>> getAccountsByType(jsp.springboot.Bankmanagement.entity.AccountType type) {
        List<Account> accounts = accountRepository.findByAccountType(type);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Accounts fetched by type.");
        res.setData(accounts);
        return res;
    }

    // Get Accounts With Balance Greater Than Value
    public ResponseStructure<List<Account>> getAccountsWithBalanceGreaterThan(double value) {
        List<Account> accounts = accountRepository.findByBalanceGreaterThan(value);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Accounts with balance > " + value);
        res.setData(accounts);
        return res;
    }

    // Get Accounts By Sorting (Pagination)
    public ResponseStructure<Page<Account>> getAccountsBySorting(int page, int size) {
        Page<Account> accounts = accountRepository.findAll(PageRequest.of(page, size));
        ResponseStructure<Page<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Accounts fetched with sorting/pagination.");
        res.setData(accounts);
        return res;
    }
}
