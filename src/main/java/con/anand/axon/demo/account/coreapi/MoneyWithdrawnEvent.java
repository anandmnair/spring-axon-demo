package con.anand.axon.demo.account.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoneyWithdrawnEvent {

	private String accountId;

	private Long amount;
	
	private Long balance;
}
