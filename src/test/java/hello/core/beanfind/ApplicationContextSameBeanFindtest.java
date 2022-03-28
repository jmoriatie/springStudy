package hello.core.beanfind;


import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 2. Bean 찾기 2번 Test
public class ApplicationContextSameBeanFindtest {
    // 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류 발생 -> 이름을 지정하자
    // ac.getBeansOfType() 을 사용하면 해당 타입의 모든 빈을 조회할 수 있음


    // 밑에 만든 Test class : SameBeanConfig 실행 - 같은 타입의 클래스 실험을 위해 간단히 만든 test class
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    // 임시 AppConfig 파일 만든 것
    // 클래스 안에서 static 선언 시 scope를 얘 안에서만 쓰겠다는 뜻
    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다")
    void findBeanByTypeDuplicate() {
//        MemberRepository bean = ac.getBean(MemberRepository.class); // 타입으로만 조회하면 중복 남
        //  ㄴ 일단 예외 터트려 봄, 그리고 오류 확인 후에 해당 오류가 났다면 성공하는 실패 테스트케이스 만들기

        // 얘는 JUnit
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class)); // 저걸 실행했을 때, 해당 오류가 나면 성공
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    // cmd + shift + enter : 다음 줄로 넘어갈 때 누르면, 꼭 열의 끝에가지 않아도 다음으로 내려감
    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2); // 간단하게 테스트
    }

}
