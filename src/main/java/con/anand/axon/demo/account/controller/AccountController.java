package con.anand.axon.demo.account.controller;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import con.anand.axon.demo.account.coreapi.CreateAccountCommand;
import con.anand.axon.demo.account.coreapi.RequestMoneyTransferCommand;
import con.anand.axon.demo.account.coreapi.WithdrawMoneyCommand;
import lombok.extern.slf4j.Slf4j;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import java.util.UUID;


@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

	@Autowired
	private CommandBus commandBus;
	
	@GetMapping("/load")
	public String load() {
		createAccount("AC1001", 500L);
		createAccount("AC1002", 500L);
		transferMoney("AC1001", "AC1002", 100L);
		//withdrawMoney("AC1001", 150L);
		
		//withdrawMoney("AC1001", 251L);
		return "loaded..";
	}
	
	@GetMapping("/create/{accountId}/{overdraftLimit}")
	public String createAccount(@PathVariable("accountId") String accountId, @PathVariable("overdraftLimit") Long overdraftLimit) {
		final StringBuilder resultBuilder = new StringBuilder();
		commandBus.dispatch(asCommandMessage(new CreateAccountCommand(accountId, overdraftLimit)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("account created successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("account creation failed").append(" :: ").append(cause.getMessage());
				log.error("createAccount :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("createAccount :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
	@GetMapping("/withdraw/{accountId}/{amount}")
	public String withdrawMoney(@PathVariable("accountId") String accountId, @PathVariable("amount") Long amount) {
		final StringBuilder resultBuilder = new StringBuilder();
		String txnId=UUID.randomUUID().toString();
		commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand(accountId,txnId, amount)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("money withdrawn successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("money withdrawal failed").append(" :: ").append(cause.getMessage());
				log.error("withdrawMoney :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("withdrawMoney :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
	@GetMapping("/transfer/{source}/{target}/{amount}")
	public String transferMoney(@PathVariable("source") String sourceAccountId,@PathVariable("target") String targetAccountId, @PathVariable("amount") Long amount) {
		final StringBuilder resultBuilder = new StringBuilder();
		String txnId=UUID.randomUUID().toString();
		commandBus.dispatch(asCommandMessage(new RequestMoneyTransferCommand(sourceAccountId,targetAccountId,txnId, amount)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("money tranfered successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("money tranfer failed").append(" :: ").append(cause.getMessage());
				log.error("transferMoney :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("transferMoney :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
}
