package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    @DisplayName("Autowired 옵션들 Test")
    void AutoWiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    // spring bean 이 관리하는 객체가 아닌 Member 로 test
    static class TestBean{

        @Autowired(required = false) // 자동 주입할 대상이 없으면, 수정자 자체가 호출 X
        public void  setNoBean1(Member member1){
            System.out.println("member = " + member1);
        }

        @Autowired // @Nullable 호출은 되나 없을 경우 null 로 나옴
        public void setNoBean2(@Nullable Member member2){
            System.out.println("member2 = " + member2);
        }

        @Autowired // Optional<Member> 호출은 되나 없을 경우 optional.empty (java8에서 지원하는 것)
        public void setNoBean3(Optional<Member> member3){
            System.out.println("member3 = " + member3);
        }
    }
}
