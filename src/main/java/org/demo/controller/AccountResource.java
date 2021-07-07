package org.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.demo.model.Account;
import org.demo.model.AccountStatus;
import org.demo.service.IAccountService;

@Path("/account")
public class AccountResource {
	
	@Inject
	IAccountService accountService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> list(
    		@QueryParam("page") @DefaultValue("1") int pageNumber,
    		@QueryParam("size") @DefaultValue("5") int pageSize) {

    	return accountService.list(pageNumber, pageSize);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account get(@PathParam("id") Long id) {
    	return accountService.find(id);
    }
    
    @PUT
    @Path("/deposit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account deposit(@PathParam("id") long id, @QueryParam("amount") BigDecimal amount) {
    	return accountService.deposit(id, amount);
    }
    
    @PUT
    @Path("/withdraw/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account withdraw(@PathParam("id") long id, @QueryParam("amount") BigDecimal amount) {
    	return accountService.withdraw(id, amount);
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Account register(@Valid Account account) {
    	return accountService.register(account);
    }
    
    
    @GET
    @Path("/populate")
    @Produces(MediaType.TEXT_PLAIN)
    public String populate() {
    	Account account = new Account();
    	account.setAccountNumber(Long.valueOf(223432));
    	account.setAccountStatus(AccountStatus.OPEN);
    	account.setBalance(BigDecimal.valueOf(1882));
    	account.setCustomerName("My Customer");
    	account.setCustomerNumber(Long.valueOf(2));
    	accountService.register(account);
        return account.toString();
    }
   
}