package wisehero.springaop.member;

import org.springframework.stereotype.Component;

import wisehero.springaop.member.annotation.ClassAop;
import wisehero.springaop.member.annotation.MethodAop;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

	@Override
	@MethodAop("test value")
	public String hello(String param) {
		return "ok";
	}

	public String internal(String param) {
		return "ok";
	}
}
