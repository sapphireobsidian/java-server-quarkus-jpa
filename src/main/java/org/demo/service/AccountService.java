package org.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.demo.ApplicationException;
import org.demo.model.Account;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AccountService implements IAccountService {
	
	private static final Logger LOG = Logger.getLogger(AccountService.class);

	@Inject
	EntityManager entityManager;
	
	@Override
	public List<Account> list(int pageNumber, int pageSize) {
		pageNumber = (pageNumber <= 0)? 1 : pageNumber;
		pageSize = (pageSize <= 0)? 1 : pageSize;
		int firstResult = (pageNumber - 1) * pageSize;
		return entityManager.createNamedQuery("Accounts.findAll", Account.class)
				.setFirstResult(firstResult).setMaxResults(pageSize)
				.getResultList();
	}
	
	@Override
	public Account find(long id) {

		LOG.debug(String.format("Find Account %s", id));

		Account account = entityManager.find(Account.class, id);
		if (account == null) {
			throw new ApplicationException(String.format("Failed to find Account with ID='%s'.", id));
		}
		
		return account;

	}
	
	@Override
	public Account findByAccontNumber(long accountNumber) {
		List<Account> matches = entityManager
				 .createNamedQuery("Accounts.findByAccountNumber", Account.class) 
				 .setParameter("accountNumber", accountNumber)
				 .setMaxResults(1)
				 .getResultList();
		if (matches.isEmpty()) {
			throw new ApplicationException();
		}
		return matches.get(0);
	}
	
	@Override @Transactional
	public Account deposit(long id, BigDecimal amount) {

		LOG.debug(String.format("Deposit %s to Account %s", amount, id));

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException("Amount is required and must be greater than 0.");
		}
		
		Account account = entityManager.find(Account.class, id);
		if (account == null) {
			throw new ApplicationException(String.format("Failed to find Account with ID='%s'.", id));
		}
		
		BigDecimal newAmount = account.getBalance().add(amount);
		account.setBalance(newAmount);
		
		return account;

	}
	
	@Override @Transactional
	public Account withdraw(long id, BigDecimal amount) {

		LOG.debug(String.format("Deposit %s to Account %s", amount, id));

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ApplicationException("Amount is required and must be greater than 0.");
		}
		
		Account account = entityManager.find(Account.class, id);
		if (account == null) {
			throw new ApplicationException(String.format("Failed to find Account with ID='%s'.", id));
		}
		
		BigDecimal newAmount = account.getBalance().subtract(amount);
		if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
			throw new ApplicationException(String.format("Account '%s' has insufficient funds.", id));
		}
		account.setBalance(newAmount);
		
		return account;

	}

	@Override
	@Transactional
	public Account register(Account account) {
		Objects.requireNonNull(account);
    	entityManager.persist(account);
        return account;
	}
	
}
