package kr.co.wanted.backend.mypost.controller.dto.member;

import lombok.Getter;

@Getter
public class MemberSignUpResponseDto {
    Long memberId;

    public MemberSignUpResponseDto(Long memberId) {
        this.memberId = memberId;
    }
}
