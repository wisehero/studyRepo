package wisehero.springaop.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import wisehero.springaop.exam.aop.RetryAspect;
import wisehero.springaop.exam.aop.TraceAspect;

@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
public class ExamTest {

	@Autowired
	ExamService examService;

	@Test
	void test1() {
		for (int i = 0; i < 5; i++) {
			examService.request("data" + i);
		}
	}
}
