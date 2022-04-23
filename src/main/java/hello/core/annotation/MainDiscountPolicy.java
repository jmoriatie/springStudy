package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

// cmd 누르고 인터페이스(또는 어노테이션) 이름을 누르면, 사용하는 코드들 추적 가능
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
// ------------- 위부분은 @Qualifier 에서 가져온 어노테이션 들 -------------
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    // RateDiscountPolicy 가서 필요한 곳(클래스)에 @MainDiscountPolicy 붙여주고
    // @Qualifier("mainDiscountPolicy") 를 등록했기 때문에, 해당 부분을 불러오는 OrderService 의 파라미터에서도
    // @MainDiscountPolicy 를 붙여주면 같은 효과를 볼 수 있다
}
