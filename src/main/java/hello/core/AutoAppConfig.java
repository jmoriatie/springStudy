package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// shift 두번 클릭 : 네비게이션 서치
// cmd + shift + F : 내가 입력한 코드를 한번에 찾아줌
@Configuration
@ComponentScan(  // @Component 어노테이션이 붙은 클래스를 찾아 @Bean 으로 자동 등록
        // Confguration 도 들어가보면 @Component가 붙어있음, 때문에 필터로 예외처리
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)

        // ** 이 아래부터 찾는다, 어디서 부터 찾는지 지정할 수 있음 >> 특정하여 성능도 올림
        // basePackages = "hello.core.member"
        // basePackages = {"hello.core","hello.service"} 이렇게 여러개 지정할 수도 있음
        // basePackageClasses = AutoAppConfig.class // 해당 클래스가 가진 패키지부터 찾음 (여긴 hello.core)
        // default : 해당 @ComponentScan 가 붙은 클래스가 가진 패키지가 시작 위치가 됨 >> hello.core 우리 프로젝트 다 뒤짐 
        // 관례 사용하는걸 권장 >> 패키지 위치 지정 X -> 설정정보의 클래스를 프로젝트 최상단에 둠 -> @ComponentScan 붙이고 basePackages 생략
)
public class AutoAppConfig {

}
