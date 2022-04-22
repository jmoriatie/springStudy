package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Test 만들 때 ctrl + shift + T 를 클래스에 올리고 누르면, Test 클래스를 하나 만들 수 있음
class StatefulServiceTest {
// command + E 수정하던 파일들 => command + E + Enter 전 수정 파일로 이동
    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA ; A사용자 10000원 주문
//        statefulService1.order("userA", 10000);
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadA ; B사용자 20000원 주문 : 끼어든걸로 치는 거
//        statefulService1.order("userB", 20000);
        int userBPrice = statefulService1.order("userB", 20000);
        // ThreadA : 사용자A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);
        System.out.println("price = " + userBPrice);

//        assertThat(statefulService1.getPrice()).isEqualTo(20000);

        // ctrl + shif + R 메서드 테스트 돌리기 // ctrl + R 클래스 테스트 돌리기
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}