package con.anand.axon.demo.account.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferRequestedEvent {

	private String transferId;
	
	private String sourceAccount;
	
	private String targetAccount;
	
	private Long amount;
	
}
