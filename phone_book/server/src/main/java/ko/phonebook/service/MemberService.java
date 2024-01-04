package ko.phonebook.service;


import ko.phonebook.entity.Member;
import ko.phonebook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 등록
     */
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 수정
     */
    @Transactional
    public Member update(Long id, Member member) {
        Optional<Member> findOptional = memberRepository.findById(id);

        if (findOptional.isEmpty()) {
            return new Member("Null", "Null");
        } else {
            Member findMember = findOptional.get();
            findMember.setName(member.getName());
            findMember.setPhoneAddress(member.getPhoneAddress());
            return findMember;
        }
    }

    /**
     * 삭제
     */
    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }


    /**
     * 전체 조회
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }


    /**
     * 단건 조회
     */
    public Member findOne(Long id) {
        Optional<Member> findOptional = memberRepository.findById(id);

        if (findOptional.isEmpty()) {
            return new Member("Null", "Null");
        } else {
            Member findMember = findOptional.get();
            return findMember;
        }
    }


}
