package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    // 1. 멤버리포지토리 회원찾기 - memoryMember
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 2. 할인정책 적용 관련 - 고정 할인 정책
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final DiscountPolicy discountPolicy;

    // AppConfig 만든 이후
//    @Autowired // @Bean 또는 @Component 로 관리되고 있으면, 생성자가 하나일 경우 @Autowired 를 생략해주더라도 자동으로 의존관계를 주입해준다
    // @Autowired(required = false) 로 바꿔주면, 주입할 대상이 없어도 오류 나지 않음 ( 반대의 경우 주입 대상이 없을 경우 오류남 )
    // 오류나지 않는 다는 것은 대상이 없으면, 메서드 자체가 호출되지 않는 다는 것을 의미
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 할인은 값만 바꿔줘 >> 단일책임원칙 잘지킨거
        // 나중에 문제 생겼을 때 할인 부분만 바꾸면 됨
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 싱글톤 테스트용 // 인터페이스에 작성해주지 않음
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
