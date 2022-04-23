package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AllBeanTest {

    @Test
    @DisplayName("조회되는 모든 Bean 가져오기")
    void findAllBean(){
        // 스프링 빈으로 등록 하면서 의존성 주입을 함
        // AutoAppConfig 에 @ComponentScan 을 통해 걸린 객체들 빈으로 관리
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        // TDD : 내가 VIP 고 10,000원이면 할인율이 얼마나 되느냐?
        DiscountService discountService = ac.getBean(DiscountService.class);

        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");

        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService{

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        // Bean 들 중 추상화인 DiscountPolicy 를 통해 걸리는 fix, rate 를 모두 Map 과 List 에 주입해줌
        // fix, rate 에 @Component 가 있음, 타입으로 먼저 찾아 주입하기 때문에, 들어가게 됨
        // 보통 이런 경우 코드리뷰가 힘들어, 해당 @Component 들을 @Configuration 를 별도로 만들고 수동 @Bean 으로 한 설정정보에 관리하거나,
        // 구현 빈들을 한 패키지에라도 묶어두는 것이 유지보수에 유리
        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap = " + policyMap); // 이름(key)=Bean(value)
            System.out.println("policyList = " + policyList); // Bean
        }

        //
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode); //파라미터로 넣은 fixDiscountPolicy 나옴
            return discountPolicy.discount(member, price); // fixDiscountPolicy 의 메서드
        }
    }
}
