package wisehero.springadvanced.proxy.trace.logtrace;

import wisehero.springadvanced.proxy.trace.TraceStatus;

public interface LogTrace {

	TraceStatus begin(String message);

	void end(TraceStatus status);

	void exception(TraceStatus status, Exception e);
}
