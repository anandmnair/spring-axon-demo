package con.anand.axon.demo.account.transfer;

import org.axonframework.test.saga.SagaTestFixture;
import org.junit.Before;
import org.junit.Test;

import con.anand.axon.demo.account.coreapi.CompleteMoneyTransferCommand;
import con.anand.axon.demo.account.coreapi.DepositMoneyCommand;
import con.anand.axon.demo.account.coreapi.MoneyDepositEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferCompletedEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferRequestedEvent;
import con.anand.axon.demo.account.coreapi.MoneyWithdrawnEvent;
import con.anand.axon.demo.account.coreapi.WithdrawMoneyCommand;

public class MoneyTransferSagaTest {

	private SagaTestFixture<MoneyTransferSaga> fixture;
	
	@Before
	public void setUp(){
		fixture=new SagaTestFixture<>(MoneyTransferSaga.class);
	}
	
	@Test
	public void testMoneyTransferRequest() {
		fixture.givenNoPriorActivity()
		.whenPublishingA(new MoneyTransferRequestedEvent("TXN1", "AC1001", "AC1002", 100L))
		.expectActiveSagas(1)
		.expectDispatchedCommands(new WithdrawMoneyCommand("AC1001","TXN1", 100L));
		;
	}

	@Test
	public void testDepositMoneyAfterWithdrawal() {
		fixture.givenAPublished(new MoneyTransferRequestedEvent("TXN1", "AC1001", "AC1002", 100L))
		.whenPublishingA(new MoneyWithdrawnEvent("AC1001","TXN1", 100L, 500L))
		.expectActiveSagas(1)
		.expectDispatchedCommands(new DepositMoneyCommand("AC1002","TXN1", 100L));
		;
	}
	
	@Test
	public void testTransferCompleteAfterDeposit() throws Exception {
		fixture.givenAPublished(new MoneyTransferRequestedEvent("TXN1", "AC1001", "AC1002", 100L))
		.andThenAPublished(new MoneyWithdrawnEvent("AC1001","TXN1", 100L, 500L))
		.whenPublishingA(new MoneyDepositEvent("AC1002","TXN1", 100L, 400L))
		.expectActiveSagas(1)
		.expectDispatchedCommands(new CompleteMoneyTransferCommand("TXN1"));
		;
	}
	
	@Test
	public void testSagaEndsAfterTransferCompleted() throws Exception {
		fixture.givenAPublished(new MoneyTransferRequestedEvent("TXN1", "AC1001", "AC1002", 100L))
		.andThenAPublished(new MoneyWithdrawnEvent("AC1001","TXN1", 100L, 500L))
		.andThenAPublished(new MoneyDepositEvent("AC1002","TXN1", 100L, 400L))
		.whenPublishingA(new MoneyTransferCompletedEvent("TXN1"))
		.expectActiveSagas(0)
		.expectNoDispatchedCommands();
		;
	}
	
}
