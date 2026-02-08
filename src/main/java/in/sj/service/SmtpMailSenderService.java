package in.sj.service;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class SmtpMailSenderService implements MailSenderService {

	private final JavaMailSender mailSender;
	
	@Override
	public void sendResetPasswordMail(String to, String link) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Reset your password");
        msg.setText("Click here to reset: " + link);
        mailSender.send(msg);
	}

}
