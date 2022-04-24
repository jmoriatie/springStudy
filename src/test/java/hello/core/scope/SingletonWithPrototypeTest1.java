package hello.core.scope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// cmd + option + N : 한 라인에 쓸 수 있는 것들은 합쳐버린다 ( 필요없는 변수 등, 근데 가독성을 위해 남겨놓는 것도 좋은 것 같음 )
// cmd + O : 클래스 찾는 기능 , shift 두 번 누르는 거에서도 찾을 수 있음
public class SingletonWithPrototypeTest1 {

    @Test
    @DisplayName("prototype 만의 logic!: 객체를 항상 새로 생성해서 반환")
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("sigleton 안의 prototype logic! 생성시점이 이미 있었기 때문에, 객체를 새로 생성하지 않음 sigleton 따름")
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class); // 두개다 컴포넌트 스캔
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton") // default 라 원래 안해줘도 됨
    static class ClientBean{
//        private final PrototypeBean prototypeBean; // 1) 생성시점에 이미 주입! -> 계속 같은걸 씀
        // prototype 을 쓸 때는, 의도가 객체를 계속 생성해달라고 하기 위해 쓰는 것일테니 1) 처럼 쓰면 의도와는 다름 2) 처럼 쓰는 방법이 있음
        // 하지만, ApplicationContext 전체를 불러와 DL(Dependency Lookup)만 이용하는 것은 매우 비효율적이며, 스프링에 의존적임(단위테스트도 어려워짐)
        // 스프링이 DL 정도만 대신 해줄 수 있는 기능이 필요 3) 또는 4) 번처럼

        // 2) 무식한 방법이지만...가능은 함
//        @Autowired
//        ApplicationContext applicationContext;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean){
//            this.prototypeBean = prototypeBean;
//        }

        // 3) 이걸로 사용 ObjectFactory
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 테스트라서, 실제론 생성자주입으로 하자!

        // 4) javax.inject.Provider : gradle 추가하고 사용하는 자바표준 라이브러리
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider; // 테스트라서, 실제론 생성자주입으로 하자!

        public int logic(){
//            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class); //2) 프로토타입 빈의 클래스를 쓸때마다 계속 가져와달라 (너무 스프링에 의존적)
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); // 3) ObjectProvider<PrototypeBean> 생성도 해서 주입해줌
            PrototypeBean prototypeBean = prototypeBeanProvider.get(); // 4) Provider<PrototypeBean> 생성도 해서 주입해줌
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this); // 나를 찍어줌
        }

        // 어차피 호출 안되는데 테스트
        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
