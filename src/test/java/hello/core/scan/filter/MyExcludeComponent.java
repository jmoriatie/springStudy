package hello.core.scan.filter;

import java.lang.annotation.*;

// 얘가 붙은애는 @ComponentScan 에서 제외할거야
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
