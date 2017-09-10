package con.anand.axon.demo.account.transfer;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import con.anand.axon.demo.account.coreapi.AccountCreatedEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferRequestedEvent;
import con.anand.axon.demo.account.coreapi.RequestMoneyTransferCommand;

public class MoneyTransferTest {
	
	private FixtureConfiguration<MoneyTransfer> fixtureConfiguration;
	
	@Before
	public void setUp(){
		fixtureConfiguration=new AggregateTestFixture<>(MoneyTransfer.class);
	}
	
	@Test
	public void testCreateAccount(){
		fixtureConfiguration.given(new AccountCreatedEvent("AC1001", 1000L), new AccountCreatedEvent("AC1002", 1000L))
			.when(new RequestMoneyTransferCommand("TXN1", "AC1001", "AC1002", 500L))
			.expectEvents(new MoneyTransferRequestedEvent("TXN1", "AC1001", "AC1002", 500L))
			;
	}
	

}
