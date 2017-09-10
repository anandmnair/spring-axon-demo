package con.anand.axon.demo.account.transfer;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import con.anand.axon.demo.account.coreapi.CompleteMoneyTransferCommand;
import con.anand.axon.demo.account.coreapi.DepositMoneyCommand;
import con.anand.axon.demo.account.coreapi.MoneyDepositEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferCompletedEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferRequestedEvent;
import con.anand.axon.demo.account.coreapi.MoneyWithdrawnEvent;
import con.anand.axon.demo.account.coreapi.WithdrawMoneyCommand;

@Saga
public class MoneyTransferSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	private String targetAccount;

	private String transactionId;
	
	
	@StartSaga
	@SagaEventHandler(associationProperty="transferId")
	public void on(MoneyTransferRequestedEvent event) {
		this.targetAccount=event.getTargetAccount();
		transactionId=UUID.randomUUID().toString();
		SagaLifecycle.associateWith("transactionId",transactionId);
		commandGateway.send(new WithdrawMoneyCommand(event.getSourceAccount(), transactionId, event.getAmount()));
	}

	@SagaEventHandler(associationProperty="transactionId", keyName="transferId")
	public void on(MoneyWithdrawnEvent event) {
		commandGateway.send(new DepositMoneyCommand(this.targetAccount, event.getTransactionId(), event.getAmount()));
	}
	
	@SagaEventHandler(associationProperty="transactionId", keyName="transferId")
	public void on(MoneyDepositEvent event) {
		commandGateway.send(new CompleteMoneyTransferCommand(event.getTransactionId()));
	}
	
	@EndSaga
	@SagaEventHandler(associationProperty="transferId")
	public void on(MoneyTransferCompletedEvent event) {
		//end();
	}
}
