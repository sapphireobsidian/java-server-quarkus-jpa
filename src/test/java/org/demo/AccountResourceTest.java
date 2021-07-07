package org.demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.demo.model.Account;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class AccountResourceTest {

	@Test
	@Order(1)
	public void testFindAccountSuccess() {
		
		Account account = given()
				.contentType(ContentType.JSON)
				.when().accept(ContentType.JSON).get("/account/{id}", "1")
				.then().statusCode(200)
				.extract().as(Account.class);

		assertThat(account, notNullValue());
		assertThat(account.getId(), equalTo(1L));
		assertThat(account.getAccountNumber(), equalTo(123456789L));
		
	}
	
	@Test
	@Order(2)
	public void testFindAccountFailedNotFound() {
			given()
				.contentType(ContentType.JSON)
				.when().accept(ContentType.JSON).get("/account/{id}", "99")
				.then().statusCode(400);
		
	}
	
	@Test
	@Order(1)
	public void testAccountDepositSuccess() {
		Account account = given()
				.contentType(ContentType.JSON)
				.when().accept(ContentType.JSON).get("/account/{id}", "1")
				.then().statusCode(200)
				.extract().as(Account.class);

		assertThat(account, notNullValue());
		
		BigDecimal amountBeforeDeposit = account.getBalance();
		BigDecimal anountToDeposit = BigDecimal.valueOf(256);
		account = given()
				.contentType(ContentType.JSON)
				.queryParam("amount", anountToDeposit)
				.when().accept(ContentType.JSON).put("/account/deposit/{id}", "1")
				.then().statusCode(200)
				.extract().as(Account.class);
		
		assertThat(account, notNullValue());
		assertThat(account.getBalance(), equalTo(amountBeforeDeposit.add(anountToDeposit)));
		
	}
	
	@Test
	@Order(1)
	public void testAccountWithdrawSuccess() {

		Account account = given()
				.contentType(ContentType.JSON)
				.when().accept(ContentType.JSON).get("/account/{id}", "1")
				.then().statusCode(200)
				.extract().as(Account.class);

		assertThat(account, notNullValue());
		
		BigDecimal amountBeforeDeposit = account.getBalance();
		BigDecimal anountToDeposit = BigDecimal.valueOf(256);
		account = given()
				.contentType(ContentType.JSON)
				.queryParam("amount", anountToDeposit)
				.when().accept(ContentType.JSON).put("/account/withdraw/{id}", "1")
				.then().statusCode(200)
				.extract().as(Account.class);
		
		assertThat(account, notNullValue());
		assertThat(account.getBalance(), equalTo(amountBeforeDeposit.subtract(anountToDeposit)));

	}

	
	@Test
	@Order(1)
	public void testAccountRegisterSuccess() {

		Account newAccount = new Account();
		newAccount.setAccountNumber(1782924367324L);
		newAccount.setCustomerNumber(8217397629232L);
		newAccount.setCustomerName("Customer Name");
		newAccount.setBalance(new BigDecimal("283.12"));
		
		Account registeredAccount = given()
				.contentType(ContentType.JSON)
				.body(newAccount)
				.when().accept(ContentType.JSON).post("/account/")
				.then().statusCode(200)
				.extract().as(Account.class);
		
		assertThat(registeredAccount, notNullValue());
		
		Account account = given()
				.contentType(ContentType.JSON)
				.when().accept(ContentType.JSON).get("/account/{id}", registeredAccount.getId())
				.then().statusCode(200)
				.extract().as(Account.class);

		assertThat(account, notNullValue());
		assertThat(account.getAccountNumber(), equalTo(newAccount.getAccountNumber()));
		assertThat(account.getCustomerNumber(), equalTo(newAccount.getCustomerNumber()));
		assertThat(account.getCustomerName(), equalTo(newAccount.getCustomerName()));
		assertThat(account.getBalance(), equalTo(newAccount.getBalance()));
		assertThat(account.getAccountStatus(), equalTo(newAccount.getAccountStatus()));
		
		System.out.println(">>>>>>>>>>>>> " + account);
		System.out.println(">>>>>>>>>>>>> " + account.getCustomerName());

	}
}
