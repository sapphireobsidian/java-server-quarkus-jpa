package org.demo.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity 
@NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Account a ORDER BY a.id ASC", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@NamedQuery(name = "Accounts.findByAccountNumber", 
	query = "SELECT a FROM Account a WHERE a.accountNumber = :accountNumber ORDER BY a.id ASC",
	hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable // Enable Local Caching / Not A Clustered Cache By Default
public class Account {

	@Id
	@SequenceGenerator(name = "accountsSequence", sequenceName = "accounts_id_seq", allocationSize = 1, initialValue = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountsSequence")
	private Long id;
	@NotNull(message="AccountNumber must not be null") @Min(value=1L, message="AccountNumber must not be zero")
	private Long accountNumber;
	@NotNull(message="CustomerNumber must not be null") @Min(value=1L, message="CustomerNumber must not be zero")
	private Long customerNumber;
	@NotBlank(message="CustomerName must not be blank")
	private String customerName;
	@DecimalMin(value = "0.0", inclusive = true, message="Balance must not be negative")
	private BigDecimal balance = BigDecimal.ZERO;
	@NotNull(message="AccountStatus must not be null")
	private AccountStatus accountStatus = AccountStatus.OPEN;
	@Min(value=0L, message="Version must not be negative")
	@Version @Column(nullable = false)
    private long version = 0L;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public long getVersion() {
		return version;
	}
	@Override
	public String toString() {
		String fmt = "Account [id=%s, accountNumber=%s, customerNumber=%s, customerName=%s, balance=%s, accountStatus=%s, version=%s]";
		return String.format(fmt, id, accountNumber, customerNumber, customerName, balance, accountStatus, version);
	}

}
