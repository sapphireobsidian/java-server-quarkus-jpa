package org.demo.service;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.demo.model.Account;

public interface IAccountService {
	
	List<Account> list(int pageNumber, int pageSize);
	
	Account find(long id);
	
	Account findByAccontNumber(long accountNumber);
	
	Account deposit(long id, BigDecimal amount);
	
	Account withdraw(long id, BigDecimal amount);

	Account register(@Valid Account account);

}
