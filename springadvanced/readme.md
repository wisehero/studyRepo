### 프록시 패턴과 데코레이터 패턴

프록시 패턴은 접근 제어가 목적이다.

- 권한에 따라 접근을 차단하거나
- 캐싱을 하거나
- 지연 로딩을 하는데 사용할 수 있다.

데코레이터 패턴은 부가 기능을 추가하는 것이 목적이다.

- 원래 서버가 제공하는 기능에 부가 기능을 더할 수 있다.
    - 예를 들면 요청 값이나, 응답 값을 중간에 변형하거나
    - 실행 시간을 측정해서 추가 로그를 남길 수 있다.

둘 다 프록시를 사용한다는 점은 동일하나, 의도가 다르다.

---

### 리플렉션

자바가 기본적으로 제공하는 JDK 동적 프록시 기술이나 CGLIB 같은 프록시 생성 오픈소스 기술을 활용하면
프록시 객체를 동적으로 만들어낼 수 있다. 이 말은 프록시 객체를 계속해서 생성하지 않아도 된다는 뜻이다.

하지만 동적 프록시를 이해하기 전에 자바의 리플렉션을 이해해야 한다.
리플렉션 기술을 사용하면 클래스나 메서드의 메타정보를 런타임에서 얻고 코드도 동적으로 호출할 수 있다.

리플렉션 기술은 런타임에 동작하기 때문에 컴파일 시점에 오류를 잡을 수 없다.
따라서 리플렉션은 일반적으로 사용하면 안된다. 개발자는 컴파일 오류가 발생하는 코드 기법을
우선시해야 한다. 리플렉션은 프레임워크 개발이나 또는 매우 일반적인 공통 처리가 필요할 때 부분적으로 사용한다.

### 동적 프록시

동적 프록시를 사용하면 프록시 객체를 동적으로 만들 수 있다. 그리고 동적 프록시에 원하는 실행 로직을 지정할 수 있다.
동적 프록시를 통해서 얻는 이점은 개발자가 직접 프록시 클래스를 만들지 않아도 된다는 것이다.
다만 JDK 동적 프록시는 인터페이스를 기반으로 프록시를 동적으로 만들어주기 때문에 인터페이스가 필수다.
과거에 스프링이 스프링 빈을 등록할 때 인터페이스를 강제했던 것은 이런 이유 때문이다.

```java
package java.lang.reflect;

public interface InvocationHandler {
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
```

- Object proxy : 프록시 객체 자신
- Method method : 메서드 정보
- Object[] args : 메서드의 매개변수

### CGLIB : Code Generator Library

CGLIB는 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리다. JDK 동적 프록시와는 달리
인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어낼 수 있다. 원래는 외부 라이브러리인데, 스프링에 포함되었다.
개발자가 스프링을 사용하면서 CGLIB를 직접 사용하는 경우는 없다.

```java
package org.springframework.cglib.proxy;

public interface MethodInterceptor extends Callback {
	Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
}
```

- obj : CGLIB가 적용된 객체
- method : 호출된 메서드
- args : 메서드를 호출하면서 전달된 인수
- proxy : 메서드 호출에 사용

CGLIB를 사용할 때는 기본 생성자가 있는지 체크해야하고 클래스와 메서드에 ```final``` 키워드가 있는지 확인해야 한다.
```final``` 키워드가 있는 메서드는 오버라이드할 수 없기 때문이다. 클래스 역시 ```final``` 키워드가 있으면 상속할 수 없다.

---

## 스프링이 지원하는 프록시

### 프록시 팩토리

인터페이스가 있으면 JDK 동적 프록시를 적용하고 그렇지 않으면 CGLIB를 적용할 수 있는 것을 알게 되었다.  
만약 두 가지를 함께 사용해야 한다면 ```InvocationHandler```와 ```MethodInterceptor```를 모두 구현하면 된다. 하지만 이러한 중복을
피하고 싶을 수도 있다.  
그러면 특정 조건에 따라 프록시 로직을 적용하는 기능도 공통으로 적용되었으면 하는 마음도 있을 것이다.

스프링은 동적 프록시를 통합해서 편리하게 만들어주는 프록시 팩토리 기능을 제공한다. 앞서 말한 두 가지 프록시를 함께 사용하는 경우에도  
프록시 팩토리를 사용해서 편리하게 동적으로 프록시를 생성할 수 있다. 프록시 팩토리는 인터페이스가 있으면 JDK 동적 프록시를 사용하고  
구체 클래스만 있다면 CGLIB를 사용한다. 그리고 이 설정을 변경할 수도 있다.

프록시 팩토리는 ```InvocationHandler```와 ```MethodInterceptor```를 직접 구현하지 않고 Advice라는 새로운 개념을 사용한다.  
따라서 개발자는 ``Advice``만 구현하면 된다. 그러면 ```InvocationHandler```와 ```MethodInterceptor```는 ```Adivce```를 호출하게 된다.

앞서 인터페이스는 JDK 동적 프록시를 사용하고 구체 클래스는 CGLIB를 사용한다고 했다.

하지만 프록시 팩토리 코드에

```java
proxyFactory.setProxyTargetClass(true);
```

이런 코드를 추가하면 CGLIB를 강제로 사용할 수 있다. 이렇게 하면 인터페이스가 있어도 CGLIB를 사용한다.  
스프링 부트는 AOP를 적용할 때 기본적으로 proxyTargetClass를 true로 설정한다. 따라서 인터페이스가 있어도  
항상 CGLIB을 사용해서 구체 클래스를 기반으로 프록시를 생성한다.

---

## 포인트컷, 어드바이스, 어드바이저

### 포인트컷(어디에)

어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 않을지 판단하는 필터링 로직이다.  
주로 클래스와 메서드 이름으로 필터링 한다. 이름 그대로 어떤 포인트에 기능을 적용하지 않을지 잘라서 구분하는 것이다.

### 어드바이스(무엇을)

이전에 본 것 처럼 프록시가 호출하는 부가 기능이다. 단순하게 프록시 로직이라고 생각하자.

### 어드바이저(누가)

하나의 포인트컷과 하나의 어드바이스를 가지고 있는 것이다.

**어드바이저를 만들때 착각하지 말아야할 점은, N개의 AOP를 만들었다고 해서 N개의 프록시를**  
**만드는 것이 아니라는 것이다. 프록시는 하나만 만들고, 하나의 프록시에 여러 어드바이저를 적용한다.**

--- 

## 빈 후처리기

`@Bean`이나 컴포넌트 스캔으로 스프링 빈을 등록하면, 스프링은 대상 객체를 생성하고 스프링 컨테이너 내부에 있는  
빈 저장소에 등록한다. 그리고 이후에는 모든 기능이 스프링 컨테이너를 통해 등록한 스프링 빈을 조회해서 사용하여  
동작된다.

그런데 빈 저장소에 등록하기 전에 빈을 조작하고 싶다면 빈 후처리기를 사용한다. 빈 후처리기는 객체 조작뿐만 아니라  
객체를 다른 객체로 바꿔치기하는 것도 가능하다.

스프링 부트에서는 자동 설정으로 `AnnotationAwareAspectJAutoProxyCreator`라는 빈 후처리기가 스프링 빈에 자동으로  
등록된다. 이름 그대로 자동으로 프록시를 생성해주는 빈 후처리기다. 동작 순서는 다음과 같다.

1. 스프링이 스프링 빈 대상이되는 객체를 생성한다.
2. 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. 빈 후처리기는 스프링 컨테이너에서 모든 Advisor를 조회한다.
4. Advisor에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지를 판단한다.
5. 프록시 적용 대상이면 프록시를 생성하고 반환해서 프록시를 스프링 빈으로 등록한다.
6. 반환된 객체는 스프링 빈으로 등록된다.

프록시는 모든 곳에 만들어서는 안된다. 이것은 비용 낭비다. 꼭 필요한 곳에 최소한의 프록시를 적용해야 한다. 그래서  
프록시를 적용할 때 매우 정밀하게 포인트컷을 설정해야 한다. 이것을 도와주는 것이 `AspectJExpressionPointcut`이다.

```java

@Bean
public Advisor advisor3(LogTrace logTrace) {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
	LogTraceAdvice advice = new LogTraceAdvice(logTrace);
	//advisor = pointcut + advice
	return new DefaultPointcutAdvisor(pointcut, advice);
}
```

---

## @Aspect

위에서 살펴본 바와 같이 스프링 애플리케이션에 프록시를 적용하려면 포인트컷과 어드바이스로 구성되어 있는 어드바이저를  
만들어서 스프링 빈으로 등록하면 된다. 그러면 나머지는 앞서 배운 자동 프록시 생성기가 모두 자동으로 처리해준다.  
스프링은 더 나아가 어드바이스와 포인트컷을 한번에 정의하고 관리할 수 있는 `@Aspect`라는 기능을 제공한다.

```java

@Slf4j
@Aspect
public class LogTraceAspect {

	private final LogTrace logTrace;

	public LogTraceAspect(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

	@Around("execution(* wisehero.springadvanced.proxy.app..*(..))")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

		TraceStatus status = null;
		try {
			String message = joinPoint.getSignature().toShortString();
			status = logTrace.begin(message);

			Object result = joinPoint.proceed();

			logTrace.end(status);
			return result;
		} catch (Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
```

`@Aspect`는 애노테이션 기반 프록시를 적용할때 필요하다. 자동 프록시 생성기는 `@Aspect`를 찾아서 Advisor로 만들어준다.  
과정음 다음과 같다.

1. 스프링 애플리케이션 로딩 시점에 자동 프록시 생성기를 호출한다.
2. 모든 `@Aspect`를 찾아서 Advisor로 만든다.
3. 생성한 어드바이저를 `@Aspect`어드바이저 빌더 내부에 저장한다.

여기서 말하는 `@Aspect`어드바이저 빌더는 `BeanFactoryAspectJAdvisorsBuilder` 클래스를 의미한다. 이 클래스는  
`@Aspect`의 정보를 기반으로 포인트컷, 어드바이스, 어드바이저를 생성하고 보관하는 것을 담당한다. 내부적으로 캐시도 가지고 있어서  
이미 만들어져 있는 경우 캐시에 저장된 어드바이저를 반환한다. 이렇게 만든 어드바이저를 자동 프록시 생성기가 조회한다.  
그리고 적용 대상인 어드바이저를 찾으면 프록시를 적용한 뒤에 프록시를 생성하고 스프링 빈으로 등록한다. 만약 프록시 적용  
대상 객체가 아니라면 원본 객체를 반환해서 스프링 빈으로 등록한다.

--- 

## 스프링 AOP

### 부가 기능 적용의 문제

기능은 핵심 기능과 부가 기능으로 나눌 수 있다. 핵심 기능이란 주문 서비스의 경우 상품 주문 로직이 핵심 기능이고 이외에  
로그를 남기거나 트랜잭션을 관리하는 기능들은 부가 기능이다. 그리고 이 부가 기능은 주문 서비스뿐만 아니라 다른 서비스에서  
사용될 수도 있다. 그럼 이 부가 기능을 적용하려면 모든 클래스에서 부가 기능을 작성해주면 된다. 하지만 이 방식은 너무 품이 많이 든다.  
아주 많은 반복이 필요하고, 중복 코드를 만들어내며 부가 기능의 변경이 생기면 많은 수정이 필요하다.

**소프트웨어 개발에서 변경 지점은 하나가 될 수 있도록 잘 모듈화 되어야 한다.**

### 애스펙트

그렇다면 부가 기능을 어떻게 모듈화할 수 있을까? 이때 사용하는 것이 애스펙트다. 애스펙트는 부가 기능을 모듈화한 것이다.  
애스펙트의 사용으로 인해서 부가 기능을 한 곳에서 관리할 수 있게 되었고 이러한 프로그래밍을 관점 지향 프로그래밍이라고 한다.  
AOP는 OOP를 대체하기 위해서 등장한 반대의 패러다임과 같은 개념이 아닌, OOP를 보완하는 개념이다. 부가 기능을 따로 분리함으로써  
객체가 자신의 주요 목적, 주요 비즈니스 로직에만 집중할 수 있도록 도와주기 때문이다.

### AOP 적용 방식

AOP를 사용할 때 부가 기능 로직은 어떻게 실제 로직에 추가될까? 크게 3가지 방법이 있다.

1. 컴파일 시점에 코드에 부가 기능을 추가하는 방법
2. 클래스 로딩 시점에 코드에 부가 기능을 추가하는 방법
3. 런타임 시점

컴파일 시점에는 AspectJ가 제공하는 특별한 컴파일러를 사용해서 `.java` 코드를 `.class`로 변환할 때 부가 기능을 추가한다.  
이 방식의 단점은 부가 기능을 적용하기 위해 특별한 컴파일러가 필요하다는 점이다.

클래스 로딩시점에 적용하는 방식은 클래스 로더에 등록되기 전에 `.class` 파일을 조작해서 JVM에 올린다. 참고로 수많은 모니터링  
툴들이 이 방식을 사용한다. 이 방식을 로드 타임 위빙이라고 하는데 자바를 실행할 때 특별한 옵션을 주어서 사용한다.

```java
java -javaagent
```

마지막으로 런타임 시점에 적용하는 AOP는 프록시를 통해 부가 기능을 적용하는 방식이다. 따라서 항상 프록시를 통해 부가 기능을  
사용할 수 있다. 스프링 AOP는 이 방식을 사용한다. 이 방식은 AOP의 적용 포인트를 메서드 실행으로 제한하고, 해당 메서드가 작성된  
클래스는 스프링 빈으로 등록되어야 한다. 즉, 프록시 방시을 사용하는 스프링 AOP는 스프링 컨테이너가 관리할 수 있는 스프링 빈에만  
AOP를 적용할 수 있다.

기억해두어야할 점은, 스프링은 AspectJ의 문법만 차용하고 프록시 방식의 AOP를 적용하는 것이다. AspectJ를 직접 사용하지 않는다.  
또한 스프링 AOP의 조인 포인트는 메서드로 제한된다. 즉, 메서드 실행 시점에만 부가 기능을 적용할 수 있다.  
static 메서드나 생성자, 필드 값 접근에는 프록시 개념이 적용될 수 없다.


