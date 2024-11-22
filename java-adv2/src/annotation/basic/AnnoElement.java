package annotation.basic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import util.MyLogger;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoElement {

	/**
	 * 데이터 타입은
	 *
	 * 기본 타입, String, Class(메타데이터), 인터페이스
	 * enum, 다른 애노테이션 타입
	 * 위 타입들의 배열 등이 올 수 있다.
	 *
	 */

	String value();

	int count() default 0; // 이처럼 디폴트를 지정할 수 있다.

	String[] tags() default {};

	Class<? extends MyLogger> annoData() default MyLogger.class;
}
