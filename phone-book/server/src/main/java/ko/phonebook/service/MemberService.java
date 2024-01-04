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

    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);

        return member.orElseGet(() -> new Member("Null", "Null"));
    }

    @Transactional
    public Member updateMember(Long id, Member member) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (findMember.isPresent()) {
            Member updateMember = findMember.get();

            updateMember.setName(member.getName());
            updateMember.setPhoneNumber(member.getPhoneNumber());
            return updateMember;
        }
        else {
            return findMember.orElseGet(() -> new Member("Null", "Null"));
        }
    }


    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

}
