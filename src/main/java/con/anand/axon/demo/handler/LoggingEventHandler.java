package con.anand.axon.demo.handler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingEventHandler {

	@EventHandler
	public void on(Object event){
		log.info("event received :: {}", event);
	}
}
