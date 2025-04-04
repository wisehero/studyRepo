package wisehero.springadvanced.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

	private Subject target; // 실제 객체가 들어올 자리
	private String cacheValue;

	public CacheProxy(Subject target) {
		this.target = target;
	}

	@Override
	public String operation() {
		log.info("프록시 호출");
		if (cacheValue == null) {
			cacheValue = target.operation(); // 실제 객체를 호출하여 캐시에 값 저장
		}
		return cacheValue;
	}
}
