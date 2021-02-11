# spring-kafka-practice

spring kafka practice on my own

## Spring-Kafka를 써보자.

- docker-compose up -d
- gradlew bootRun

### Kafka

0. Event Streaming?
   - 실시간으로 Event source(Database, sensor and so on)로부터 데이터를 포착하는 기능
1. Kafka의 3가지 주요 기능
   - Event의 stream을 발행(쓰기)하거나 구독(읽기)
   - 원하는 만큼 event의 stream을 오래 그리고 확실하게 저장
   - event의 stream을 처리
2. 주요 개념 및 용어
   - event는 비즈니스 영역이나 실제 세계에서 사실을 기록한다.
   - event는 key, value, timestamp 그리고, 추가적인 메타데이터 헤더를 가진다.
   - Producer(생산자)는 Kafka에 event를 발행하고, Consumer(소비자)는 이러한 event를 구독하는 client 어플리케이션이다.
