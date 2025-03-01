package wisehero.springadvanced.proxy.trace.logtrace;

import lombok.extern.slf4j.Slf4j;
import wisehero.springadvanced.proxy.trace.TraceId;
import wisehero.springadvanced.proxy.trace.TraceStatus;

@Slf4j
public class FieldLogTrace implements LogTrace {

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";

	private TraceId traceIdHolder; // 동시성 이슈 발생 가능

	@Override
	public TraceStatus begin(String message) {
		syncTraceId(); // TraceId 가져오고
		TraceId traceId = traceIdHolder; // 가져온 TraceId를 사용
		Long startTimeMs = System.currentTimeMillis(); // 시작 시간 기록
		// 로그 출력
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
		return new TraceStatus(traceId, startTimeMs, message);
	}

	@Override
	public void end(TraceStatus status) {
		complete(status, null);
	}

	@Override
	public void exception(TraceStatus status, Exception e) {
		complete(status, e);
	}

	private void complete(TraceStatus status, Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs = stopTimeMs - status.getStartTimeMs();
		TraceId traceId = status.getTraceId();

		if (e == null) {
			log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()),
				status.getMessage(), resultTimeMs);
		} else {
			log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()),
				status.getMessage(), resultTimeMs, e.toString());
		}

		releaseTraceId();
	}

	private void syncTraceId() {
		if (traceIdHolder == null) {
			traceIdHolder = new TraceId();
		} else {
			traceIdHolder = traceIdHolder.createNextId();
		}
	}

	private void releaseTraceId() {
		// Level이 0이면 제거
		if (traceIdHolder.isFirstLevel()) {
			traceIdHolder = null;
		} else {
			traceIdHolder = traceIdHolder.createPreviousId();
		}
	}

	private static String addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append((i == level - 1) ? "|" + prefix : "|   ");
		}
		return sb.toString();
	}
}
