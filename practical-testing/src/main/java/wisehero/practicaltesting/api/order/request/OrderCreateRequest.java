package wisehero.practicaltesting.api.order.request;

import java.util.List;

import lombok.Builder;

@Builder
public record OrderCreateRequest(List<String> productNumbers) {

}
