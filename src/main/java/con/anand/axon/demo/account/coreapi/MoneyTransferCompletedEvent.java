package con.anand.axon.demo.account.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferCompletedEvent {
	
	private String transferId;

}
