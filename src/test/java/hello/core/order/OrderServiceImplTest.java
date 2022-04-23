package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    // Autowired 를 사용하는 상황에서 생성자 주입을 사용해야만 하는 이유
    // setter injection 을 사용할 시, 의존관계의 파악이 어렵다 (코드를 까봐야만 알 수 있음)
    // 따라서, 바로 확인할 수 있도록, 생성자 주입을 사용하는 것이 바람직 하며
    // 그렇게 되면 임의의 객체들을 넣어서 test 에 바로 사용해 볼 수 있게되는 것
    // + final 키워드를 쓸 수 있기 떄문임 -> 컴파일러를 통해 오류를 빨리 알 수 있다 (final 초기화 단계에서 값이 없으면 오류)
    @Test
    @DisplayName("순수한 java으로 테스트를 위해, 생성자 주입을 사용한다 무조건!")
    void createOrder(){
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save( new Member(1L, "testName", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}