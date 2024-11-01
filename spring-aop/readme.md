# 인프런

## 스프링 AOP

### 참고

스프링 AOP는 AspectJ의 문법만 사용하고 프록시 AOP 방식만 사용한다. AspectJ를 직접 사용하지 않는다.

`@Aspect` 어노테이션은 애스펙트라는 표식이지 컴포넌트 스캔이 되는 것은 아니다.

---

## 스프링 AOP 구현

### 참고 정보 획득

모든 어드바이스는 `org.aspectj.lang.JoinPoint`를 첫 번째 파라미터로 받을 수 있다. 이는 생략이 가능하다.  
단, `@Around`는 ProceedingJoinPoint를 첫 번째 파라미터로 받아야 한다.

### JoinPoint 인터페이스의 주요 기능

- `getArgs()` : 메서드의 인수를 반환한다.
- `getThis()` : 프록시 객체를 반환한다.
- `getTarget()` : 대상 객체를 반환한다.
- `getSignature()` : 호출되는 메서드에 대한 정보를 반환한다.

### ProceedingJoinPoint 인터페이스의 주요 기능

- `proceed()` : 타겟 객체의 메서드를 실행한다.

---

## 어드바이스 종류

### `@Before`
```java
@Before("hello.aop.order.aop.Pointcuts.orderAndService()")
public void doBefore(JoinPoint joinPoint) {
    log.info("[before] {}", joinPoint.getSignature());
}
```

- 조인 포인트 실행 전에 동작한다.
- `@Around`와는 달리 작업 흐름을 변경할 수는 없다.

### `@AfterReturning`
```java
@AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()",
returning = "result")
public void doReturn(JoinPoint joinPoint, Object result) {
    log.info("[return] {} return={}", joinPoint.getSignature(), result);
}
```
- `returning` 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
- `returning` 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행한다.
- `@Around`와 다르게 반환되는 객체를 변경할 수는 없다. 반환 객체를 변경하려면 `@Around`를 사용해야 한다.

### `@AfterThrowing`
```java
@AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()",
throwing = "ex")
public void doThrowing(JoinPoint joinPoint, Exception ex) {
    log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
}
```
- throwing 속성에 사용된 이름은 어드바이스 메서드의 매개변수 이름과 일치해야 한다.
- throwing 절에 지정된 타입과 맞는 예외를 대상으로 실행한다.

### `@After`

- 메서드 실행이 종료되면 실행된다.(finally)
- 정상 및 예외 반환 조건을 모두 처리한다.
- 일반적으로 리소스를 해제하는 데 사용한다.

### `@Around`
- 메서드의 실행 주변에서 실행된다. 메서드 실행 전후에 작업을 수행한다.
- 조인 포인트 실행 여부를 선택할 수 있다.
- 반환 값을 변환하고 예외를 변환하는 작업도 가능하다.
- try-catch-finally 블록을 사용할 수 있다.
- 어드바이스의 첫 번째 파라미터는 `ProceedingJoinPoint`이어야 한다.
- `proceed()`를 여러번 실행할 수 있다.

### 실행 순서(스프링 5.2.7 버전부터)
- `@Around` -> `@Before` ->  `@After` -> `@AfterReturning` -> `@AfterThrowing`

--- 

## 포인트컷 

### 포인트컷 지시자의 종류
- `execution` : 메서드 실행 지점을 매칭한다. 스프링 AOP에서 가장 많이 사용하고, 기능도 복잡하다.
- `within` : 특정 타입 내부의 메서드를 매칭한다. within은 부모 타입은 사용하면 안된다.(추상 클래스, 인터페이스 X)
- `args` : 인자가 주어진 타입의 인스턴스인 조인 포인트
- `this` : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
- `target` : 타겟 객체(스프링 AOP 프록시가 가리키는 실제 대상)를 대상으로 하는 조인 포인트

이외에도 많은 포인트컷 지시자가 있지만 execution이 가장 많이 사용된다.

