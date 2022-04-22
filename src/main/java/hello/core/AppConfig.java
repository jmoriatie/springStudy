package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// soutm : 메서드 system out

// ** @Configuration 을 뺴버릴 시, AppConfig를 불렀을 때 GCLIB 이 아닌 순수한 자바클래스(AppConfig)가 반환되며,
// 문제1 : 아래에 있는 Bean들도 자바코드가 실행되며, 여러번 호출하여 등록됨
// 문제2 : 그렇게 되면 각자의 Bean 들은 각각 다른 Bean 됨
// 문제3 :
@Configuration
public class AppConfig {

    // 1) 생성자를 통해서 주입하는 Constructor injection
    // 2) 역할과 구현이 딱 한눈에 나눠지는 것을 보이게 + new 중복 제거

    // 역할
    // @Bean("ms") // 이름 따로 지을 수도 있음

    // 스프링은 싱글톤이라는데.. 객체가 두번 생성되는 것처럼 보인다
    // @Bean memberService -> new MemoryMemberRepository();
    // @Bean orderService -> new RateDiscountPolicy();

    // 자바코드만 봤을 떄 예상 호출 (순서는 보장하지 않음)
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository

    // 스프링의 실제 호출 (순서는 보장하지 않음)
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    @Bean
    public MemberService memberService() {
        // 구현
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl( memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }



}
