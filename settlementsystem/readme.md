## 무엇을 배웠나?

### 외부 API 의존에 따른 재시도 작업의 필요성

- 몇 번 재시도 할 것인지?
- 재시도를 했는데도 실패했다면 어떻게 핸들링 할 것인가?

### ParallelStream

- 병렬 스트림은 내부적으로 어떤 스레드풀을 사용할까?
- 데드락 상황을 예방할 수 있는 독립적인 스레드 풀 사용을 고려하기

### Bulk Insert

- JDBC의 Batch Insert는 SQL을 모아서 쓰는 점을 고려해 많은 양의 데이터 삽입에 효율적이다.
- 너무 많은 데이터를 한 번에 처리하면 메모리 사용량이 증가할 수 있다. 그렇기 때문에 batch size를 잘 지정하자.
- 배치를 한꺼번에 수행할 때 실패한 건에 대한 예외처리, 예외 발생 인지를 할 수 있는 장치를 구현하자.