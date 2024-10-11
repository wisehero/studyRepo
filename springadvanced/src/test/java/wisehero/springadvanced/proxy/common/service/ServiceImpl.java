package wisehero.springadvanced.proxy.common.service;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.common.ServerInterface;

@Slf4j
public class ServiceImpl implements ServerInterface {

	@Override
	public void save() {
		log.info("save 호출");
	}

	@Override
	public void find() {
		log.info("find 호출");
	}
}
