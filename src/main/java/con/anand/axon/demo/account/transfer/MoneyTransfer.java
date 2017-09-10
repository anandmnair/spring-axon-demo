package con.anand.axon.demo.account.transfer;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import con.anand.axon.demo.account.coreapi.CancelMoneyTransferCommand;
import con.anand.axon.demo.account.coreapi.CompleteMoneyTransferCommand;
import con.anand.axon.demo.account.coreapi.MoneyTransferCancelledEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferCompletedEvent;
import con.anand.axon.demo.account.coreapi.MoneyTransferRequestedEvent;
import con.anand.axon.demo.account.coreapi.RequestMoneyTransferCommand;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;


@Data
@NoArgsConstructor
@Aggregate
public class MoneyTransfer {

	@AggregateIdentifier
	private String transferId;
	
	@CommandHandler
	public MoneyTransfer(RequestMoneyTransferCommand command) {
		apply(new MoneyTransferRequestedEvent(command.getTransferId(), command.getSourceAccount(), command.getTargetAccount(), command.getAmount()));
	}
	
	
	@CommandHandler
	public void handle(CompleteMoneyTransferCommand command) {
		apply(new MoneyTransferCompletedEvent(command.getTransferId()));
	}

	@CommandHandler
	public void handle(CancelMoneyTransferCommand command) {
		apply(new MoneyTransferCancelledEvent(command.getTransferId()));
	}	
	
	@EventSourcingHandler
	public void on(MoneyTransferRequestedEvent event) {
		this.transferId = event.getTransferId();
	}
	
	@EventSourcingHandler
	public void on(MoneyTransferCompletedEvent event) {
		markDeleted();
	}
	
	@EventSourcingHandler
	public void on(MoneyTransferCancelledEvent event) {
		markDeleted();
	}
}
