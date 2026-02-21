# Stockengine 아키텍처 개요

이 문서는 프로젝트의 주요 계층(Controller, Service, Engine 등)과 각 클래스의 역할을 간단히 요약합니다. 코드를 빠르게 파악하고 기능별로 확장/수정할 때 참고용으로 사용하세요.

## 계층 구조
- Controller: HTTP 요청을 받아 Service로 위임하고 응답을 반환
- Service: 도메인 로직 실행 및 외부/엔진 계층과의 조율
- Engine: 핵심 엔진 구성요소 및 워크플로(상태머신 등) 관리
- Assistants: LLM 어시스턴트 모음 및 실행/레지스트리 관리
- Model (Request/Response): API 입출력용 DTO
- Util: 공통 유틸리티

## Controller
- `src/main/java/shiroi/stockengine/api/controller/ShiroiV1Controller.java`
  - 엔드포인트
    - `GET /api/v1/analyze`: 장기 전략 기반 분석 실행(현재 스텁)
    - `POST /api/v1/steps`: 스텝 생성(현재 스텁)
    - `POST /api/v1/step-transitions`: 스텝 전이 생성(현재 스텁)
    - `POST /api/v1/indicators`: 지표 계산 요청을 받아 Service에 위임
  - 책임
    - 요청 검증(기본), DTO 매핑, Service 호출, 응답 생성
    - 비즈니스 로직은 포함하지 않음

## Service
- `src/main/java/shiroi/stockengine/api/service/ShiroiService.java`
  - 역할: 전략 기반 분석, 스텝/스텝전이 생성 등 상위 도메인 로직의 조율자
  - 구현 상태: 현재 대부분 스텁이며, 향후 `Engine`과 저장소 계층 연동 예정

- `src/main/java/shiroi/stockengine/api/service/IndicatorService.java`
  - 역할: 가격 시계열을 TA4J `BarSeries`로 변환하고 대표 지표를 계산
  - 입력: `timeframe`(예: `1m`, `5m`, `1h`...), `List<BarDto>`
  - 주요 처리
    - 시계열 변환: `BarDto -> BarSeries`
    - 지표 계산
      - 이동평균: SMA(20), EMA(50), EMA(200)
      - 오실레이터: RSI(14), Stochastic K(14)
      - MACD(12,26) + Signal(EMA 9)
      - 보조치: 최근 고가/저가(20), 평균 거래량(20)
    - 안정성: 시리즈 경계 내에서만 인덱싱하도록 SMA/EMA 구현을 보강하여 짧은 시계열에서도 `IndexOutOfBoundsException`이 발생하지 않도록 처리
  - 출력: 지표 이름→값 맵을 반환(응답 DTO에서 래핑)

## Engine
- `src/main/java/shiroi/stockengine/engine/StockEngine.java`
  - 역할: 전략 실행(상태머신 기반 워크플로)을 담당할 엔진 컴포넌트의 진입점
  - 구현 상태: 현재 스텁. 향후 `StateMachine` 생성/시작 및 결과 집계를 수행하도록 확장 예정

## Assistants
- 공통 인터페이스: `src/main/java/shiroi/stockengine/engine/assistants/Assistant.java`
  - 메서드: `getType()`, `execute(message)` — 메시지를 입력으로 받아 실행 결과 문자열 반환
- 레지스트리/관리자: `src/main/java/shiroi/stockengine/engine/assistants/AssistantManager.java`
  - 등록된 어시스턴트를 병렬로 실행하거나, 특정 타입만 선택 실행
- 레지스트리 구성: `src/main/java/shiroi/stockengine/engine/assistants/config/AssistantRegistryConfiguration.java`
  - 스프링 빈으로 `Map<AssistantType, Assistant>` 생성 및 주입
- 도구(Tools)
  - `NewsTools`, `DisclosureTools`: LangChain4j `@Tool` 예시 메서드 포함(향후 실제 데이터 소스 연동 확장 지점)

## Model (Request/Response)
- Request DTO
  - `IndicatorRequest`: 심볼, 타임프레임, `List<BarDto>`(OHLCV + epochMillis)
  - `CreateStepRequest`, `CreateStepTransitionRequest`: 전략 정의용 입력(현재 스텁)
- Response DTO
  - `IndicatorResponse`: 심볼, 타임프레임, 바 개수, 계산된 지표 맵

## Util
- `src/main/java/shiroi/stockengine/engine/util/ResourceFileReader.java`
  - 리소스 경로(스프링 `ResourceLoader`)에서 파일을 읽어 문자열로 반환

## 데이터 흐름 예시: 지표 계산
1) Client → `POST /api/v1/indicators`
2) Controller(`ShiroiV1Controller`) → `IndicatorService.calculate(timeframe, bars)` 위임
3) Service(`IndicatorService`)
   - `BarDto` 목록을 `BarSeries`로 변환
   - 경계 안전한 SMA/EMA, RSI, Stoch K, MACD/Signal 등 계산
4) Controller가 `IndicatorResponse`로 포장하여 반환

## 최근 변경 사항(안정성)
- EMA/SMA 계산 시 인덱스를 시리즈 경계로 클램핑하고, 사용 가능한 구간만으로 시드/반복을 수행하도록 수정
- 짧은 시계열(예: 10개 바)에서도 `IndexOutOfBoundsException`이 발생하지 않음
