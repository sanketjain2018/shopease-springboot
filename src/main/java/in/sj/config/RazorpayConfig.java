
package in.sj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RazorpayConfig {

	@Value("${razorpay.key}")
	private String keyId;

	@Value("${razorpay.secret}")
	private String keySecret;

	@Bean
	public RazorpayClient razorpayClient() throws RazorpayException {
		return new RazorpayClient(keyId, keySecret);
	}
}