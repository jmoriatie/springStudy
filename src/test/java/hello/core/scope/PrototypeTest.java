package hello.core.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    @DisplayName("prototype scope 는 계속 새로운 객체를 생성함, 쓰고 버림, close 도 되지 않음")
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        // ㄴ 이렇게 해주면 PrototypeBean 에 @Component 를 붙이지 않아도, ComponentScan 의 대상으로 되기 때문에 자동 등록됨
        // ㄴ (참고) 파라미터 부분 자료형 또는 객체에 cmd + p : 파라미터 보기
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2); // isSameAs : ==

        ac.close(); // close 할 때 종료까지 됨 @PreDestroy 붙은 메서드 실행 : 실행안됨

        // 수작업으로 닫아야 할 떄는 아래와 같이 수작업 -> 조회한 클라이언트가 종료관리
//        prototypeBean1.destroy();
//        prototypeBean2.destroy();
    }

    @Scope("prototype")
    static class PrototypeBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }
        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
}
