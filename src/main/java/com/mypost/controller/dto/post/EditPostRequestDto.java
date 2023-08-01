package com.mypost.controller.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class EditPostRequestDto {
    @NotBlank(message = "글 제목을 확인해주세요")
    private final String title;

    @NotBlank(message = "글 본문을 확인해주세요")
    private final String content;
}
