package wisehero.practicaltesting.client;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSendClient {

	public boolean sendEmail(String fromMail, String toEmail, String subject, String content) {
		log.info("Send mail from {} to {} with subject {} and content {}", fromMail, toEmail, subject, content);
		throw new IllegalArgumentException("메일 전송");
	}

	public void a() {
		log.info("a");
	}

	public void b() {
		log.info("b");
	}

	public void c() {
		log.info("c");
	}
}
