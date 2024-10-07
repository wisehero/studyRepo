package wisehero.springadvanced.trace.logtrace;

import wisehero.springadvanced.trace.TraceStatus;

public interface LogTrace {

	TraceStatus begin(String message);

	void end(TraceStatus status);

	void exception(TraceStatus status, Exception e);
}
