package wisehero.springaop.internalcall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV1 {

	// 자기 자신을 주입받기
	CallServiceV1 callServiceV1;

	@Autowired
	public void setCallServiceV1(CallServiceV1 callServiceV1) {
		this.callServiceV1 = callServiceV1;
	}

	public void external() {
		log.info("call external");
		callServiceV1.internal();
	}

	public void internal() {
		log.info("call internal");
	}
}
