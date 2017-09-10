package con.anand.axon.demo.account.coreapi;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
@Value
public class CreateAccountCommand {
	
	@TargetAggregateIdentifier
	private String accountId;
	
	private Long overdraftLimit;

}
