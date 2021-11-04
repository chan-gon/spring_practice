package hello.hellospring.service;

import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 테스트 실행 전 실행행
   @BeforeEach
    public void beforeEach(){
       // 같은 MemberRepository를 사용하도록 설정
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 각 테스트 이후 실행되어 설정된 메소드 실행
    public void afterEach(){
        memberRepository.clearStore(); // 메모리 초기화
    }

    @Test
    void 회원가입() {
        // given 주어진 상황에서 / 데이터
        Member member = new Member();
        member.setName("hello");

        // when 어떤 것을 실행했을 때 / 검증
        Long saveId = memberService.join(member);

        // then 어떤 결과가 나왔다 / 검증 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 테스틑 정상보다 예외 플로우가 더 중요하다.

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}