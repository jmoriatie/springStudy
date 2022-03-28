package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 구현체가 하나만 있을 때는 관례상 Impl을 많이 씀

//  private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

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
}
