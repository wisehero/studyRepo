package wisehero.settlementsystem.payment.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wisehero.settlementsystem.payment.service.PortOneRequestUrl;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentClient {

	private final RestClient restClient;

	@Value("${payment.imp-key}")
	private String impKey;

	@Value("${payment.imp-secret}")
	private String impSecret;

	private static final String BASE_URL = "https://api.iamport.kr";

	public Map getAccessToken() {
		String url = BASE_URL + PortOneRequestUrl.ACCESS_TOKEN_URL.getUrl();
		try {
			String requestBody = String.format("{\"imp_key\": \"%s\", \"imp_secret\": \"%s\"}", impKey, impSecret);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Send POST request
			return restClient
				.post()
				.uri(url)
				.headers(h -> h.addAll(headers))
				.body(requestBody)
				.retrieve()
				.body(Map.class);
		} catch (RestClientException e) {
			throw new RuntimeException("Failed to get access token from PortOne API", e);
		}
	}

	@Retryable(
		value = RestClientException.class, // RestClientException이 발생하면 재시도
		maxAttempts = 2, // 최대 2번까지 재시도
		backoff = @Backoff(delay = 1000), // 1초 간격으로 재시도
		recover = "handlePaymentCancellationFailure"
	)
	public String cancelPayment(String impUid) {

		String accessToken = ((LinkedHashMap)getAccessToken().get("response")).get("access_token").toString();

		String url = BASE_URL + PortOneRequestUrl.CANCEL_PAYMENT_URL.getUrl();
		String requestBody = String.format("{\"imp_uid\": \"%s\"}", impUid);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(accessToken);

		return restClient
			.post()
			.uri(url)
			.headers(h -> h.addAll(headers))
			.body(requestBody)
			.retrieve()
			.body(String.class);

		// if (true) {
		// 	// 아래의 코드는 재시도를 위해서 일부러 발생시키는 예외다.
		// 	throw new RestClientException("Forced RestClientException");
		// } else {
		// 	// 원래는 아래의 코드로 충분
		// 	return restClient
		// 		.post()
		// 		.uri(url)
		// 		.headers(h -> h.addAll(headers))
		// 		.body(requestBody)
		// 		.retrieve()
		// 		.body(String.class);
		// }
	}

	@Recover
	public String handlePaymentCancellationFailure(RestClientException e, String impUid) {
		log.error("RestClientException 예외로 인해 실패: " + e.getClass().getName() + " impUid : " + impUid);
		// 실패에 대한 처리 로직
		// 담당자가 인지 할 수 있게 처리 구현
		return HttpStatus.INTERNAL_SERVER_ERROR.toString(); // 실패 후 대체 반환 값
	}

	public String createPayment(String paymentRequest, String token) {
		String url = BASE_URL + PortOneRequestUrl.CREATE_PAYMENT_URL.getUrl();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);

			// Send POST request
			return restClient
				.post()
				.uri(url)
				.headers(h -> h.addAll(headers))
				.body(paymentRequest)
				.retrieve()
				.body(String.class);
		} catch (RestClientException e) {
			throw new RuntimeException("Failed to create payment", e);
		}
	}
}

