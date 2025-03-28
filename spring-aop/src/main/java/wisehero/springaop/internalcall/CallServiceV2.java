package wisehero.springaop.internalcall;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

	// private final ApplicationContext applicationContext;
	private final ObjectProvider<CallServiceV2> callServiceProvider;

	public void external() {
		log.info("call external");
		// CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
		CallServiceV2 callServiceV2 = callServiceProvider.getObject();
		callServiceV2.internal();
	}

	public void internal() {
		log.info("call internal");
	}
}
