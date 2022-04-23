package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterAppConfigTest {

    @Test
    @DisplayName("지정한 어노테이션만 스캔하고, 해당 Bean 만 가져오는지 출력")
    void filterScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);

        // assertj
        assertThat(beanA).isNotNull();
        System.out.println(beanA);

        // junit5
        assertThrows(
            NoSuchBeanDefinitionException.class,
            () -> ac.getBean("beanB", BeanB.class)
        );
    }

    // includeFilters = : filter include 할껀데
    // @ComponentScan.Filter() : ComponentScan 의 Filter 를 include 할꺼고
    // type = FilterType.ANNOTATION : type 은 Annotation 과 관련한 거야 >> default라 생략 가능
    // classes = MyIncludeComponent.class : 그리고 그 class 는 아까 내가 만든 @MyIncludeComponent 야
    @Configuration
    @ComponentScan(
            includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @ComponentScan.Filter(classes = MyExcludeComponent.class) // type default 로 생략한 모습
    )
    static class ComponentFilterAppConfig{

    }
}
