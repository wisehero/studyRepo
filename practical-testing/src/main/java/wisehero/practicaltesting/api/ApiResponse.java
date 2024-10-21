package wisehero.practicaltesting.api;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
	int code,
	HttpStatus status,
	String message,
	T data
) {

	public ApiResponse(HttpStatus status, String message, T data) {
		this(status.value(), status, message, data);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new ApiResponse<>(httpStatus, message, data);
	}

	public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
		return new ApiResponse<>(httpStatus, httpStatus.name(), data);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return of(HttpStatus.OK, data);
	}
}
