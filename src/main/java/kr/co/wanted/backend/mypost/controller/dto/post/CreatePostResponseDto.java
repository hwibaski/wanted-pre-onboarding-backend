package kr.co.wanted.backend.mypost.controller.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatePostResponseDto {
    private final Long postId;
}
