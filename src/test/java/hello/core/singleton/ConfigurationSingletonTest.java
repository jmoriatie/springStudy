package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

// command + e 이전 수정한 파일들 보기
// new 할 객체에 command + option + v  : 객체(자료형)+변수명 자동완성
// soutv : value system out
// Assertions(assertj 소속임) option + enter -> static 변환 : assertThat 줄여준다
// ctrl + shift + R : 메서드 Test 돌리기
// ctrl + R         : 클래스 Test 돌리기
public class ConfigurationSingletonTest {

    @Test
    @DisplayName("서로다른 곳에서 불려진 Bean들이 계속 같은 인스턴스인 것인지 확인")
    void configurationTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 이렇게 구체 타입 꺼내는건 그리 좋은 방법이 아니지만 테스트용
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemoryMemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);// 실제 Bean으로 등록된 MemoryMemberRepository

        MemberRepository memberRepository1 = memberService.getMemberRepository(); // memberService 에서 꺼낸 MemoryMemberRepository
        MemberRepository memberRepository2 = orderService.getMemberRepository(); // orderService 에서 꺼낸 MemoryMemberRepository

        // 세개의 Bean이 같음 => 같은 인스턴스가 조회됨
        System.out.println("memberRepository REAL  = " + memberRepository);
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository2);
    }

    @Test
    @DisplayName("@Configuration 깊게 꺼내 보기")
    void configurationDeep(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = ac.getBean(AppConfig.class);

        System.out.println("appConfig.getClass() = " + appConfig.getClass()); // getClass 클래스 타입을 보는 메서드

        // 스프링이 따로만드는 객체 타입을 CGLIB 이라고 보통 부름
        // CGLIB이 스프링이 바이트 코드를 조작해서 내가 작성한 클래스를 상속받는 임의의 클래스를 하나 생성하여, Spring Bean 으로 등록한 것!
        // AppConfig -> AppConfig@CGLIB
        // AppConfig 조회해서 나온 이유는, 부모 타입을 조회해서 자식타입을 다 불러온거
    }
}
