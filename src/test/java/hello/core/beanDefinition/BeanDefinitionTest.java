package hello.core.beanDefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

class BeanDefinitionTest {

//    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class); // 이걸로 했을 때 GetBeanDefinition 을 할 수 없음
//      ㄴ BeanDefinition 은 AnnotationConfigApplicationContext 에서 상속된 BeanDefinitionReader 를 통해 불러와지는 것이기 때문
//      ㄴ 그리고, 해당 인터페이스는 사용할 일이 거의 없기 때문에, 복잡한 메서드들은 구현되어있지 않음

    //    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);  // Factory 메서드를 통해 등록하는 방법
    GenericXmlApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml"); // 직접 등록하는 방법

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                                " beanDefinition = " + beanDefinition);
            }
        }
    }
}
