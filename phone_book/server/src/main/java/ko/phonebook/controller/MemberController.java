package ko.phonebook.controller;


import ko.phonebook.entity.Member;
import ko.phonebook.entity.MemberDto;
import ko.phonebook.entity.MembersDto;
import ko.phonebook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 등록
     */
    @PostMapping
    public ResponseEntity<MemberDto> save(@RequestBody MemberDto memberDto) {
        memberService.save(new Member(memberDto.getName(), memberDto.getPhoneAddress()));
        return new ResponseEntity(memberDto, OK);
    }

    /**
     * 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> update(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        Member updatedMember = memberService.update(id, new Member(memberDto.getName(), memberDto.getPhoneAddress()));
        return new ResponseEntity(new MemberDto(updatedMember.getName(), updatedMember.getPhoneAddress()), OK);
    }


    /**
     * 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MemberDto> delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity(new MemberDto("Null", "Null"), OK);
    }


    /**
     * 전체 조회
     */
    @GetMapping
    public ResponseEntity findAll() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity(new MembersDto(members.size(), members), OK);
    }


    /**
     * 단건 조회
    */
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> findById(@PathVariable Long id) {
        Member findMember = memberService.findOne(id);
        return new ResponseEntity(new MemberDto(findMember.getName(), findMember.getPhoneAddress()), OK);
    }

}
