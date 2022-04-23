package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// cmd + F12 : 작성된 생성자와 메서드 확인 가능 (Lombok 으로 만들어진 것도 확인 가능)
@Component
@RequiredArgsConstructor // final 이 붙으면 생성자를 만들어준다
public class OrderServiceImpl implements OrderService{

    // 1. 멤버리포지토리 회원찾기 - memoryMember
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    // 2. 할인정책 적용 관련 - 고정 할인 정책
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    private final DiscountPolicy discountPolicy;

    // AppConfig 만든 이후
    // @Autowired // @Bean 또는 @Component 로 관리되고 있으면, 생성자가 하나일 경우 @Autowired 를 생략해주더라도 자동으로 의존관계를 주입해준다
    // @Autowired(required = false) 로 바꿔주면, 주입할 대상이 없어도 오류 나지 않음 ( 반대의 경우 주입 대상이 없을 경우 오류남 )
    // 오류나지 않는 다는 것은 대상이 없으면, 메서드 자체가 호출되지 않는 다는 것을 의미
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }
//    >> @RequiredArgsConstructor

    // 매칭되는 타입이 2개 이상일 떄
    //@Autowired // 필드명으로 검색 후 안되면 빈 이름으로 검색함 -> 이름을 특정해주면 됨
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    // 타입이 중복되는 구현 클래스에 @Qualifier("이름") 를 넣어줌으로 추가적인 검색기준을 만들어주고
    // @Qualifier("이름") 을 해당 주입 파라미터에 넣어줌 + Qualifier 는 수정자, 필드 등 다 넣을 수 있음
    // 못찾으면 해당 이름의 스프링 빈을 추가로 찾으니 주의해야함
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    // 중복 bean 중 먼저 부르고자하는 bean에 @Primary 를 붙여준다 : 얘를 먼저 가져와!
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }



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
