package jsp.springboot.Bankmanagement.service;

import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Address;
import jsp.springboot.Bankmanagement.exception.AddressNotFoundException;
import jsp.springboot.Bankmanagement.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Get Address By ID
    public ResponseStructure<Address> getAddressById(int id) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isPresent()) {
            ResponseStructure<Address> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Address fetched successfully.");
            res.setData(optional.get());
            return res;
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Update Address
    public ResponseStructure<Address> updateAddress(int id, Address updatedAddress) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isPresent()) {
            Address address = optional.get();
            address.setStreet(updatedAddress.getStreet());
            address.setCity(updatedAddress.getCity());
            address.setState(updatedAddress.getState());
            address.setPincode(updatedAddress.getPincode());
            ResponseStructure<Address> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Address updated successfully.");
            res.setData(addressRepository.save(address));
            return res;
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Delete Address
    public ResponseStructure<String> deleteAddress(int id) {
        Optional<Address> optional = addressRepository.findById(id);
        if (optional.isPresent()) {
            addressRepository.delete(optional.get());
            ResponseStructure<String> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMessage("Address deleted successfully.");
            res.setData("Deleted address with ID: " + id);
            return res;
        } else {
            throw new AddressNotFoundException("Address not found with ID: " + id);
        }
    }

    // Get Address By Bank
    public ResponseStructure<List<Address>> getAddressByBank(int bankId) {
        List<Address> addresses = addressRepository.findByBank_BankId(bankId);
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Addresses fetched by bank.");
        res.setData(addresses);
        return res;
    }

    // Get Address By City
    public ResponseStructure<List<Address>> getAddressByCity(String city) {
        List<Address> addresses = addressRepository.findByCity(city);
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Addresses fetched by city.");
        res.setData(addresses);
        return res;
    }

    // Get Address By City and Street
    public ResponseStructure<List<Address>> getAddressByCityAndStreet(String city, String street) {
        List<Address> addresses = addressRepository.findByCityAndStreet(city, street);
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Addresses fetched by city and street.");
        res.setData(addresses);
        return res;
    }

    // Get Address By Pincode
    public ResponseStructure<List<Address>> getAddressByPincode(long pincode) {
        List<Address> addresses = addressRepository.findByPincode(pincode);
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Addresses fetched by pincode.");
        res.setData(addresses);
        return res;
    }
}