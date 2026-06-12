package jsp.springboot.Bankmanagement.controller;
 
import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Address;
import jsp.springboot.Bankmanagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/address")
public class AddressController {
 
    @Autowired
    private AddressService addressService;
 
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable int id) {
        return new ResponseEntity<>(addressService.getAddressById(id), HttpStatus.OK);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Address>> updateAddress(
            @PathVariable int id, @RequestBody Address address) {
        return new ResponseEntity<>(addressService.updateAddress(id, address), HttpStatus.OK);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteAddress(@PathVariable int id) {
        return new ResponseEntity<>(addressService.deleteAddress(id), HttpStatus.OK);
    }
 
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByBank(@PathVariable int bankId) {
        return new ResponseEntity<>(addressService.getAddressByBank(bankId), HttpStatus.OK);
    }
 
    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(@PathVariable String city) {
        return new ResponseEntity<>(addressService.getAddressByCity(city), HttpStatus.OK);
    }
 
    @GetMapping("/city/{city}/street/{street}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCityAndStreet(
            @PathVariable String city, @PathVariable String street) {
        return new ResponseEntity<>(addressService.getAddressByCityAndStreet(city, street), HttpStatus.OK);
    }
 
    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByPincode(@PathVariable long pincode) {
        return new ResponseEntity<>(addressService.getAddressByPincode(pincode), HttpStatus.OK);
    }
}
 

