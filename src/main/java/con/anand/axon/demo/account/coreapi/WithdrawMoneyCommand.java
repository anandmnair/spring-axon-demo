package con.anand.axon.demo.account.coreapi;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class WithdrawMoneyCommand {

	@TargetAggregateIdentifier
	private String accountId;

	private Long amount;

}
