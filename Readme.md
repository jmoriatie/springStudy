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



