package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 2. Bean 찾기 2번 Test
class ApplicationContextBasicFindTest {
    // cmd + E : '내가 수정한' 파일 리스트 출력
    // cmd + E + enter : '내가 수정한' 전 파일로 돌아가기
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 메서드 안에서 ctr(^) + shift + R 해당 메서드 실행
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        Object memberService = ac.getBean("memberService", MemberService.class);

//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());

        // option + enter : Assertions 스태틱으로 만들기
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // memberService 가 MemberServiceImpl 의 인스턴스냐
        // ㄴ Impl 을 등록해놨기 때문에, 인터페이스로 조회하더라도 스프링에서 memberService 라는 이름으로 관리되고 있는 인스턴스는 MemberServiceImpl 이 맞음
    }

    // 메서드 안에서 ctr(^) + shift + R 해당 메서드 실행
    @Test
    @DisplayName("이름 없이 타입으로만 조회") // 같은 타입이 여러개일 경우 곤란해지기는 함
    void findBeanByType() {
        Object memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        // 구체적으로 적는 것은 좋지 않음 => 역할과 구현 구분해야하며, 역할에 의존해야하기 때문에
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // 항상 실패 테스트도 만들어야함
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
//        ac.getBean("xxxxx", MemberService.class);

        // 테스트 코드 검증 : 이 assertThrows() 는 Assertion jUnit   << AssertThat은 Assertion이 assertj
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class)); // 예외가 터져야만 성공
    }

}
