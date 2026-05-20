package in.sj.dto;

import lombok.Data;

@Data
public class PaymentOrderRequest {
	private Long amount; // ₹1 = 100 paise
	private String currency;
	private String receipt;
}
