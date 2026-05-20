// service/PaymentService.java
package in.sj.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.sj.dto.PaymentOrderResponse;
import in.sj.dto.PaymentVerificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	private final RazorpayClient razorpayClient;
	private final CartService cartService;

	@Value("${razorpay.key}")
	private String razorpayKey;

	@Value("${razorpay.secret}")
	private String razorpaySecret;

	@Value("${razorpay.currency}")
	private String currency;

	// ---- Create Razorpay Order ----
	public PaymentOrderResponse createOrder(String username) throws RazorpayException {
		double total = cartService.calculateTotal(username);
		long amountInPaise = Math.round(total * 100);

		JSONObject options = new JSONObject();
		options.put("amount", amountInPaise);
		options.put("currency", currency);
		options.put("receipt", "receipt_" + username + "_" + System.currentTimeMillis());
		options.put("payment_capture", 1); // auto capture

		Order order = razorpayClient.orders.create(options);
		log.info("Razorpay order created | orderId={} | user={}", order.get("id"), username);

		return new PaymentOrderResponse(order.get("id"), amountInPaise, currency, razorpayKey);
	}

	// ---- Verify Payment Signature ----
	public boolean verifySignature(PaymentVerificationRequest req) {
		try {
			String payload = req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId();
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(razorpaySecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
			byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}

			boolean valid = hexString.toString().equals(req.getRazorpaySignature());
			log.info("Signature verification | valid={} | orderId={}", valid, req.getRazorpayOrderId());
			return valid;
		} catch (Exception e) {
			log.error("Signature verification failed", e);
			return false;
		}
	}
}