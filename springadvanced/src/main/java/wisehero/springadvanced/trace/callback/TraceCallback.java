package wisehero.springadvanced.trace.callback;

public interface TraceCallback<T> {
	T call();
}
