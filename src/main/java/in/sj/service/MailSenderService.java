package in.sj.service;


public interface MailSenderService  {
	 void sendResetPasswordMail(String to, String link);
}
