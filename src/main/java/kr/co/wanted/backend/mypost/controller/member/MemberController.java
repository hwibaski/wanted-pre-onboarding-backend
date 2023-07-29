package kr.co.wanted.backend.mypost.controller.member;

import kr.co.wanted.backend.mypost.controller.dto.ResponseTemplate.ResponseTemplate;
import kr.co.wanted.backend.mypost.controller.dto.member.MemberSignUpRequestDto;
import kr.co.wanted.backend.mypost.controller.dto.member.MemberSignUpResponseDto;
import kr.co.wanted.backend.mypost.domain.member.Member;
import kr.co.wanted.backend.mypost.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseTemplate signUp(@Valid @RequestBody MemberSignUpRequestDto requestDto) {
        Member member = memberService.signUp(requestDto);
        MemberSignUpResponseDto result = new MemberSignUpResponseDto(member.getId());

        return ResponseTemplate.successResponse(result);
    }
}
