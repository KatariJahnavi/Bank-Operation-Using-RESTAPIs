package jsp.springboot.Bankmanagement.controller;
 
import jsp.springboot.Bankmanagement.dto.AccountDto;
import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.dto.TransferDto;
import jsp.springboot.Bankmanagement.entity.Account;
import jsp.springboot.Bankmanagement.entity.AccountType;
import jsp.springboot.Bankmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/account")
public class AccountController {
 
    @Autowired
    private AccountService accountService;
 
    @PostMapping("/{bankId}")
    public ResponseEntity<ResponseStructure<Account>> createAccount(
            @PathVariable int bankId, @RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(bankId, account), HttpStatus.CREATED);
    }
 
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Account>>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable int id) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteAccount(@PathVariable int id) {
        return new ResponseEntity<>(accountService.deleteAccount(id), HttpStatus.OK);
    }
 
    @PutMapping("/deposit")
    public ResponseEntity<ResponseStructure<Account>> depositAmount(@RequestBody AccountDto dto) {
        return new ResponseEntity<>(accountService.depositAmount(dto), HttpStatus.OK);
    }
 
    @PutMapping("/withdraw")
    public ResponseEntity<ResponseStructure<Account>> withdrawAmount(@RequestBody AccountDto dto) {
        return new ResponseEntity<>(accountService.withdrawAmount(dto), HttpStatus.OK);
    }
 
    @PutMapping("/transfer")
    public ResponseEntity<ResponseStructure<String>> transferAmount(@RequestBody TransferDto dto) {
        return new ResponseEntity<>(accountService.transferAmount(dto), HttpStatus.OK);
    }
 
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByBank(@PathVariable int bankId) {
        return new ResponseEntity<>(accountService.getAccountsByBank(bankId), HttpStatus.OK);
    }
 
    @GetMapping("/type/{type}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByType(@PathVariable AccountType type) {
        return new ResponseEntity<>(accountService.getAccountsByType(type), HttpStatus.OK);
    }
 
    @GetMapping("/balance/{value}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsWithBalanceGreaterThan(
            @PathVariable double value) {
        return new ResponseEntity<>(accountService.getAccountsWithBalanceGreaterThan(value), HttpStatus.OK);
    }
 
    @GetMapping("/sorting")
    public ResponseEntity<ResponseStructure<Page<Account>>> getAccountsBySorting(
            @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(accountService.getAccountsBySorting(page, size), HttpStatus.OK);
    }
}
