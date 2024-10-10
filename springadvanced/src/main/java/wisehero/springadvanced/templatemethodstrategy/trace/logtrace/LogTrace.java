package wisehero.springadvanced.templatemethodstrategy.trace.logtrace;

import wisehero.springadvanced.templatemethodstrategy.trace.TraceStatus;

public interface LogTrace {

	TraceStatus begin(String message);

	void end(TraceStatus status);

	void exception(TraceStatus status, Exception e);
}
