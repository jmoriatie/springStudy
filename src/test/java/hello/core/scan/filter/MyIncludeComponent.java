package hello.core.scan.filter;

import java.lang.annotation.*;

// 얘가 붙은애는 @ComponentScan 에 포함할거야
@Target(ElementType.TYPE) // << @Component 어노테이션에서 빼온 3개
@Retention(RetentionPolicy.RUNTIME) // << @Component 어노테이션에서 빼온 3개
@Documented // << @Component 어노테이션에서 빼온 3개
public @interface MyIncludeComponent {
}
