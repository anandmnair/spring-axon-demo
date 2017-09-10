package con.anand.axon.demo.account.transfer;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Data
//@NoArgsConstructor
@Aggregate
public class MoneyTransfer {

	@AggregateIdentifier
	private String transferId;
	
	public MoneyTransfer() {
		
	}
	
}
