package wisehero.springaop.exam;

import org.springframework.stereotype.Repository;

import wisehero.springaop.exam.annotation.Retry;
import wisehero.springaop.exam.annotation.Trace;

@Repository
public class ExamRepository {

	private static int seq = 0;

	@Trace
	@Retry(value = 4)
	public String save(String itemId) {
		seq++;
		if (seq % 5 == 0) {
			throw new IllegalArgumentException("예외 발생");
		}

		return "ok";
	}
}
