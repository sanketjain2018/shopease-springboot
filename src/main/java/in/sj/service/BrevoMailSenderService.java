package in.sj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("prod")
public class BrevoMailSenderService implements MailSenderService {

	@Value("${BREVO_API_KEY}")
	private String apiKey;

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public void sendResetPasswordMail(String to, String link) {

		String url = "https://api.brevo.com/v3/smtp/email";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("api-key", apiKey);

		String body = """
				{
				  "sender": { "name": "ShopEase", "email": "sanketjavafs@gmail.com" },
				  "to": [ { "email": "%s" } ],
				  "subject": "Reset your password",
				  "htmlContent": "<p>Click here to reset your password: <a href='%s'>Reset</a></p>"
				}
				""".formatted(to, link);

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		restTemplate.postForEntity(url, entity, String.class);
	}
}
