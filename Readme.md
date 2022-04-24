> # Spring 기본 구동원리  학습

#### 도구: spring boot / IntelliJ 
___
#### [프로젝트 설명]
* 스프링 기본 학습
___
> 학습내용
##### java 활용
* 좋은 객체 지향 설계란? SOLID를 지킨 설계 방안
    (SRP 단일책임원칙, OCP 개방-폐쇄원칙(중요), LSP 리스코프 치환 원칙, 
    ISP 인터페이스 분리원칙, DIP 의존관계 역전 원칙(중요) )
* java 로 만든 Spring Containor AppConfig
* AppConfig -> Spring 으로 리팩토링
* IoC, DI, Containor
* AnnotationConfigApplicationContext 활용 
  * Bean find 원리와 방법
  * BeanDefinition 설정 관련 원리 학습
  * GenericXmlApplicationContext 의 원리 및 XML 설정방법 비교 학습 

###### Spring 활용
* Container의 이해
* BeanDefinition 이해
* spring 의 @Configuration 과 @Bean 에 대한 이해
* BeanDefiniton의 이해, Spring의@bean과 @configuration 어노테이션에 대한 이해
* @componentscan, @component, @Autowired 에 대한 이해
* 주입 시 2개 이상의 Bean 타입이 매칭됐을 때(상속 된 클래스가 2개 중복!)
  - @Autowired 필드 명 매칭 : 타입매칭 -> 빈 이름 매칭 (*파라미터명을 컨트롤 해서 특정)
  - @Qualifier @Qualifier끼리 매칭 -> 빈 이름 매칭 (*매칭하고 싶은 클래스에 지정하고, 메서드에서 파라미터 앞부분 수정)
  - @Primary 사용 -> 중복 bean 중 먼저 부르고자하는 bean에 @Primary 를 붙여준다 : 얘를 먼저 가져와!
* Qualifier 의 문제점
  - 문자열을 사용하기 때문에, compile 단계에서 체크가 안됨
  - 따라서, 별도의 커스텀 어노테이션을 만들어 사용 (MainDiscountPolicy)
* 조회되는 모든 빈을 가져오기
    - AllBeanTest 참고
~~~
* 자동? 수동? 등록 어떻게사용?
  - 편리한 자동기능 default
  - 직접 등록하는 기술 지원 객체는 수동 등록
  - 다형성을 적극 활용하는 비즈니스 로직같은 경우 수동 등록 고민
    ㄴ 다만, 수동등록 하더라도, 구현체들은 한눈에 알아보기 쉽게 @Configuration 또는 한 패키지에라도 모아두기
~~~
* 초기화 : 객체가 생성되고 사용할 수 있는 상태가 되는 것 ! (객체의 생성 != 초기화)
  - 외부와 연결하는 등 사용할 준비를 하는 것
---
* [빈생명주기 콜백]
* 스프링컨테이너생성 - 스프링빈생성 - 의존관계주입 - "초기화콜백" - 사용 - "소멸전콜백" - 스프링 종료
  - 초기화 콜백: 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출 
  - 소멸전 콜백: 빈이 소멸되기 직전에 호출
  ~~~
  * 객체 생성과 초기화는 분리하자! 
  객체를 생성하는 건 객체를 생성하는데 집중 << 메모리 할당, 최소한 필요한 데이터 셋팅
    ㄴ 무거운 작업을 하지 마라! 객체 내부의 값을 세팅하는 정도
  
  초기화 작업을 할 때는 외부와 커넥션을 맺는 등 동작하는 것 << 별도의 초기화 메서드로 분리
    ㄴ 생성된 값들을 활용해서, 무거운 동작을 수행할 때 사용
    ㄴ 따라서 별도의 초기화 메서드를 통해 처리 필요
    ㄴ + 생성해두고 기다리다가 최초의 요청이 올 때 하기 때문에, 동작을 지연시킬 수 있는 장점 
  ~~~

* [빈생명주기 콜백 사용 방법과 역사]
* 스프링컨테이너생성 - 스프링빈생성 - 의존관계주입 – "초기화콜백" -사용 - "소멸전콜백" - 스프링 종료
>>1. implements InitializingBean, DisposableBean 은 단점 떄문에 요즘은 잘 사용하지 않음
   - 상속받아서 사용하는데 부담, 외부 라이브러리를 변경할 수 없음, 메서드명 변경 불가능 등 (사용X)
>>2. @Bean(initMethod = "init", destroyMethod = "close")
   - 외부라이브러리 어노테이션이 작동하지 않음, 외부라이브러리에서 빈생명주기콜백 적용할 때는 요걸 사용하자
>>3. @PostConstruct, @PreDestroy
   - javax -> 자바진영에서 공식적으로 지원하는 것 
     - Spring 이 아니더라도 그대로 적용이 됨 
     - spring에서 권장하는 방식이기도 함

* [빈스코프]
  - 싱글 : 기본 스코프, 스프링 컨테이너와 똑같음
  - 프로토타입 : 스프링 컨테이너는 프로토타입 빈의 생성, 의e존관계 주입, 초기화 까지만 관여 
    - 초기화 메서드O / 종료메서드X
  - 웹 관련 스코프
    - application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프
    - session :웹 세션이 생성되고 종료될 떄 까지 유지되는 스코프
    - application : 웹 요청이 들어오고 나갈 떄 까지 유지되는 스코프
  * 싱글톤과 프로토타입을 함꼐 사용하는 경우 ( 아래 참조 )
    - SingletonWithPrototypeTest1
  * 의존관계의 외부주입(DI:Dependency Injection) >> 직접 찾는 것을(DL: Dependency Lookup)
    - 싱글톤 안에서 프로토타입을 객체를 사용해야할 때, 객체를 쓰고 버리는게 의도 
    - 하지만, DL은 스프링에 종속적인 코드가 됨 + 단위 테스트가 어려워짐 
      - applicationContext 자체를 상속받아 사용하는 것
    - 따라서, DL 기능만 딱 받는 기능을 스프링에게 양도 
      - ObjectProvider<스프링에서 찾을 객체> -> 변수명.getObject 로 활용 (편의기능도 제공)
      - 딱 찾아만주는 ObjectFactory 도 있음 
      - 이 기능은 prototype 에만 국한된 것이 아니라, 뭔가를 spring 에서 찾아올 때 사용한다 로 생각
      - +자바표준 Provider<스프링에서 찾을 객체> -> 변수명.get 로 활용 (그래들 추가 필요)
    ~~~
    ObjectProvider(스프링기능) : 스프링 바로 사용 가능, 편의기능 제공
    Provider(자바표준) : 의존관계 주입 필요, 표준(javax), 기능 심플
    ~~~
