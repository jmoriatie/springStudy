package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 1. Bean 찾기 1번 Test
// JUnit5 부터는 public 생략 가능
class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 빈 정의된 이름 불러오기

        // iter + tab 누르면 배열의 for문 자동 완성
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object = " + bean); // 단축키: soutv - 변수명 출력
//            System.out.println("ApplicationContextInfoTest.findAllBean"); // 단축키: soutm - 메서드명 출력
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 빈 정의된 이름 불러오기

        // iter + tab 누르면 배열의 for문 자동 완성
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // bean 하나하나에 대한 메타데이터 정보들

            // BeanDefinition.ROLE_APPLICATION      : 직접 등록한 애플리케이션 빈
            // BeanDefinition.ROLE_INFRASTRUCTURE   : 스프링이 내부에서 사용하는 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean); // 단축키: soutv - 변수명 출력
            }
        }
    }
}
