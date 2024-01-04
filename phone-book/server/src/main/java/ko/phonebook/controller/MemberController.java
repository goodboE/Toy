package ko.phonebook.controller;

import ko.phonebook.entity.CreateMemberDto;
import ko.phonebook.entity.Member;
import ko.phonebook.entity.MemberDto;
import ko.phonebook.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 저장
     */
    @PostMapping
    public MemberDto saveMember(@RequestBody CreateMemberDto createMemberDto) {
        return new MemberDto(1, memberService.saveMember(new Member(createMemberDto.getName(), createMemberDto.getPhoneNumber())));
    }

    /**
     * 수정
     */
    @PutMapping("/{id}")
    public MemberDto updateMember(@PathVariable Long id, @RequestBody CreateMemberDto createMemberDto) {
        return new MemberDto(1, memberService.updateMember(id, new Member(createMemberDto.getName(), createMemberDto.getPhoneNumber())));
    }

    /**
     * 삭제
     */
    @DeleteMapping("/{id}")
    public MemberDto deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);

        return new MemberDto(1, null);
    }

    /**
     * 전체 조회
     */
    @GetMapping
    public AllMembersDto findAll() {
        return new AllMembersDto(memberService.findAll().size(), memberService.findAll());
    }

    /**
     * 단건 조회
     */
    @GetMapping("/{id}")
    public MemberDto findMember(@PathVariable(name = "id") Long id) {
        return new MemberDto(1, memberService.findMemberById(id));
    }


    @Data
    @AllArgsConstructor
    static class AllMembersDto<T> {
        private final int size;
        private final T Data;

    }

}
