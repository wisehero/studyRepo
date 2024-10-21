package wisehero.practicaltesting.application.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import wisehero.practicaltesting.client.MailSendClient;
import wisehero.practicaltesting.domain.history.mail.MailSendHistory;
import wisehero.practicaltesting.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendClient(String fromEMail, String toEmail, String subject, String content) {
		boolean result = mailSendClient.sendEmail(fromEMail, toEmail, subject, content);
		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
				.fromEmail(fromEMail)
				.toEmail(toEmail)
				.subject(subject)
				.content(content)
				.build());
			return true;
		}
		return false;
	}

}
