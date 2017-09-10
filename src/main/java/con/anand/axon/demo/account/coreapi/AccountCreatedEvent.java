package con.anand.axon.demo.account.coreapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedEvent {

	private String accountId;
	
	private Long overdraftLimit;

}
