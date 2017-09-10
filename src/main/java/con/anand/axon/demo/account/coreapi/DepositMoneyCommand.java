package con.anand.axon.demo.account.coreapi;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class DepositMoneyCommand {

	@TargetAggregateIdentifier
	private String accountId;

	private String transactionId;

	private Long amount;
}
