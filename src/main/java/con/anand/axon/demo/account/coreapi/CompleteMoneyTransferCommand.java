package con.anand.axon.demo.account.coreapi;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteMoneyTransferCommand {

	@TargetAggregateIdentifier
	private String transferId;

}
