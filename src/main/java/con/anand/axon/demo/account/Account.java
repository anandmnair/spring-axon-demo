package con.anand.axon.demo.account;


import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import con.anand.axon.demo.account.coreapi.AccountCreatedEvent;
import con.anand.axon.demo.account.coreapi.CreateAccountCommand;
import con.anand.axon.demo.account.coreapi.MoneyWithdrawnEvent;
import con.anand.axon.demo.account.coreapi.WithdrawMoneyCommand;
import con.anand.axon.demo.account.exception.OverdraftLimitExceedException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Aggregate
public class Account {

	@AggregateIdentifier
	private String accountId;
	
	private Long balance;
	
	private Long overdraftLimit;
	
	@CommandHandler
	public Account(CreateAccountCommand command) {
		apply(new AccountCreatedEvent(command.getAccountId(), command.getOverdraftLimit()));
	}
	
	@CommandHandler
	public void handle(WithdrawMoneyCommand command) {
		if(balance + overdraftLimit >= command.getAmount()) {
			apply(new MoneyWithdrawnEvent(command.getAccountId(), command.getAmount(), balance - command.getAmount()));
		}
		else {
			throw new OverdraftLimitExceedException("cannot wothdrawn..");
		}
	}
	
	@EventSourcingHandler
	public void on(AccountCreatedEvent accountCreatedEvent) {
		this.accountId=accountCreatedEvent.getAccountId();
		this.overdraftLimit=accountCreatedEvent.getOverdraftLimit();
		this.balance=0L;
	}
	
	@EventSourcingHandler
	public void on(MoneyWithdrawnEvent moneyWithdrawnEvent) {
		this.balance=moneyWithdrawnEvent.getBalance();
	}
	
}
