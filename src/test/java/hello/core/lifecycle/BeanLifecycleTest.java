package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifecycleConfig.class); // 빈 등록 ConfigurableApplicationContext 부모
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifecycleConfig.class); // 빈 등록 AnnotationConfigApplicationContext 자식
        NetworkClient client = ac.getBean(NetworkClient.class); // 빈 조회
        ac.close(); // close 를 쓰려면 AnnotationConfigApplicationContext, ConfigurableApplicationContext 사용
    }

    @Configuration
    static class LifecycleConfig{

        // destroyMethod = "(inferred)" << default 추론: 외부라이브러리는 shutdown close 등으로 종료메서드가 등록되어 있어서
        // 설정하지 않아도 해당 이름의 메서드가 있으면, 종료 시점에 선언함 (문자열로 등록되어 있음)
        // 종료하지 않을거야 하면, destroyMethod = "" 로 ㅋㅋㅋㅋㅋㅋ >> 망가짐
        // @Bean(initMethod = "init", destroyMethod = "close")
        // 이것도 옛날방식, 어노테이션 방식으로 쓰자 PostConstruct PreDestroy

        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
