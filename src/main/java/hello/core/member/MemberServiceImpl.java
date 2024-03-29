package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    // 구현체가 하나만 있을 때는 관례상 Impl을 많이 씀

//  private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired // == ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {
        // AppConfig에서 선택해서 넣어줌
        // 생성자 주입
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 싱글톤 테스트 용도 -> 상위 인터페이스에 작성하지 않음
    public MemberRepository getMemberRepository() {
         return memberRepository;
    }
}
