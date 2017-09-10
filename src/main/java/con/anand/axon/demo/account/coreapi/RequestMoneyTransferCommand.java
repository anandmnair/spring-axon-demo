package con.anand.axon.demo.account.coreapi;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMoneyTransferCommand {

	@TargetAggregateIdentifier
	private String transferId;
	
	private String sourceAccount;
	
	private String targetAccount;
	
	private Long amount;
	
}
