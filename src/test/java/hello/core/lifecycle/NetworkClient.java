package hello.core.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient  {
    // 가짜 클라이언트

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url){
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String message){
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    //스프링컨테이너생성 - 스프링빈생성 - 의존관계주입 – "초기화콜백" -사용 - "소멸전콜백" - 스프링 종료
    // 1. implements InitializingBean, DisposableBean 은 단점 떄문에 요즘은 잘 사용하지 않음
    //  ㄴ 상속받아서 사용하는데 부담, 외부 라이브러리를 변경할 수 없음, 메서드명 변경 불가능 등
    // 2. @Bean(initMethod = "init", destroyMethod = "close")
    //  ㄴ 외부라이브러리 적용할 때는 요걸 사용하자
    // 3. @PostConstruct, @PreDestroy
    // javax >> 자바진영에서 공식적으로 지원하는 것 Spring 이 아니더라도 그대로 적용이 됨 // spring에서 권장하는 방식이기도 함

    // 빈 생명주기 콜백은 요 어노테이션들로 사용!
    // @PostConstruct
    // 스프링 의존관계 설정이 끝나고 초기화 단계에서 호출 -> 초기화 콜백
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    // @PreDestroy
    // 초기화 종료시 호출 -> 소멸전 콜백
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }



    // implements InitializingBean 을 통해 상속받은 메서드
    // 스프링 의존관계 설정이 끝나고 초기화 단계에서 호출 -> 초기화 콜백
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }

    // implements DisposableBean
    // 초기화 종료시 호출 -> 소멸전 콜백
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }
}
