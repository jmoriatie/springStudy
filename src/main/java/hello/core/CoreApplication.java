package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 들어가보면 @ComponentScan 이 있음
// 때문에 Spring boot에서는 아무 설정 하지 않아도 Component 등록하면,자동으로 Bean 등록해주는 것이 이 때문
// 해당 어노테이션을 루트위치에 두는 것은 관례이며, 여기서 필요 없을 시 excludeFilters 해서 사용하는 것을 권장
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
