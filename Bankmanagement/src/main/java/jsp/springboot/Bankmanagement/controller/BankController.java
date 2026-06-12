package jsp.springboot.Bankmanagement.controller;
 
import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Bank;
import jsp.springboot.Bankmanagement.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/bank")
public class BankController {
 
    @Autowired
    private BankService bankService;
 
    @PostMapping
    public ResponseEntity<ResponseStructure<Bank>> saveBank(@RequestBody Bank bank) {
        return new ResponseEntity<>(bankService.saveBank(bank), HttpStatus.CREATED);
    }
 
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks() {
        return new ResponseEntity<>(bankService.getAllBanks(), HttpStatus.OK);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable int id) {
        return new ResponseEntity<>(bankService.getBankById(id), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable int id) {
        return new ResponseEntity<>(bankService.deleteBank(id), HttpStatus.OK);
    }
 
    @GetMapping("/pagination/{page}/{size}")
    public ResponseEntity<ResponseStructure<Page<Bank>>> getBankByPagination(
            @PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(bankService.getBankByPagination(page, size), HttpStatus.OK);
    }
 
    @GetMapping("/ifsc/{ifscCode}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByIfsc(@PathVariable String ifscCode) {
        return new ResponseEntity<>(bankService.getBankByIfsc(ifscCode), HttpStatus.OK);
    }
 
    @GetMapping("/address/{addressId}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBankByAddress(@PathVariable int addressId) {
        return new ResponseEntity<>(bankService.getBankByAddress(addressId), HttpStatus.OK);
    }
 
    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(@PathVariable String city) {
        return new ResponseEntity<>(bankService.getBankByCity(city), HttpStatus.OK);
    }
 
    @GetMapping("/contact/{contact}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBankByContact(@PathVariable long contact) {
        return new ResponseEntity<>(bankService.getBankByContact(contact), HttpStatus.OK);
    }
}
 