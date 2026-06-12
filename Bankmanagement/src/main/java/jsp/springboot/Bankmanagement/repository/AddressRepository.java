package jsp.springboot.Bankmanagement.repository;
 
import jsp.springboot.Bankmanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
 
    List<Address> findByCity(String city);
 
    List<Address> findByCityAndStreet(String city, String street);
 
    List<Address> findByPincode(long pincode);
 
    List<Address> findByBank_BankId(int bankId);
}