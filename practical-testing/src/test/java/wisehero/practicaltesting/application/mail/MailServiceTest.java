package wisehero.practicaltesting.application.mail;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import wisehero.practicaltesting.client.MailSendClient;
import wisehero.practicaltesting.domain.history.mail.MailSendHistory;
import wisehero.practicaltesting.domain.history.mail.MailSendHistoryRepository;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Spy
	private MailSendClient mailSendClient;

	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;

	@InjectMocks
	private MailService mailService;

	@Test
	@DisplayName("메일 전송 테스트")
	void sendMail() {
		// given
		// when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
		doReturn(true)
			.when(mailSendClient)
			.sendEmail(anyString(), anyString(), anyString(), anyString());

		// when
		boolean result = mailService.sendClient("", "", "", "");

		// then
		assertThat(result).isTrue();
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
	}

}