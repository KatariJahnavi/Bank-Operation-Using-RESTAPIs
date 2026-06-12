package jsp.springboot.Bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jsp.springboot.Bankmanagement.entity.Account;
import jsp.springboot.Bankmanagement.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	 Optional<Account> findByAccountNumber(long accountNumber);
	 
	    List<Account> findByBank_BankId(int bankId);
	 
	    List<Account> findByAccountType(AccountType accountType);
	 
	    List<Account> findByBalanceGreaterThan(double balance);
	 
	    Page<Account> findAll(Pageable pageable);

}
