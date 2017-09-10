package con.anand.axon.demo.account;

import org.axonframework.test.aggregate.*;
import org.junit.Before;
import org.junit.Test;

import con.anand.axon.demo.account.coreapi.AccountCreatedEvent;
import con.anand.axon.demo.account.coreapi.CreateAccountCommand;
import con.anand.axon.demo.account.coreapi.MoneyWithdrawnEvent;
import con.anand.axon.demo.account.coreapi.WithdrawMoneyCommand;
import con.anand.axon.demo.account.exception.OverdraftLimitExceedException;

public class AccountTest {

	private FixtureConfiguration<Account> fixtureConfiguration;
	
	@Before
	public void setUp(){
		fixtureConfiguration=new AggregateTestFixture<>(Account.class);
	}
	
	@Test
	public void testCreateAccount(){
		fixtureConfiguration.givenNoPriorActivity()
			.when(new CreateAccountCommand("AC1001", 1000L))
			.expectEvents(new AccountCreatedEvent("AC1001", 1000L))
			;
	}
	
	@Test
	public void testWithdrawMoney(){
		fixtureConfiguration.given(new AccountCreatedEvent("AC1001", 1000L))
			.when(new WithdrawMoneyCommand("AC1001","TXN1", 600L))
			.expectEvents(new MoneyWithdrawnEvent("AC1001","TXN1", 600L, -600L))
			;
	}
	
	@Test
	public void testWithdrawMoneyCauseOverlimit(){
		fixtureConfiguration.given(new AccountCreatedEvent("AC1001", 1000L))
			.when(new WithdrawMoneyCommand("AC1001","TXN1", 1001L))
			.expectNoEvents()
			.expectException(OverdraftLimitExceedException.class)
			;
	}
}
