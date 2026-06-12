package jsp.springboot.Bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.Bankmanagement.dto.ResponseStructure;
import jsp.springboot.Bankmanagement.entity.Bank;

public interface BankRepository extends JpaRepository<Bank,Integer>{

	 Optional<Bank> findByIfscCode(String ifscCode);
	 
	    List<Bank> findByAddress_City(String city);
	 
	    List<Bank> findByAddress_AddressId(int addressId);
	 
	    Page<Bank> findAll(Pageable pageable);
	 
	    List<Bank> findByContact(long contact);

		boolean existsByContact(long contact);

		boolean existsByIfscCode(String ifscCode);

}
