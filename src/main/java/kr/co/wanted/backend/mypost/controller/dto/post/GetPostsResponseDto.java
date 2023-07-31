package kr.co.wanted.backend.mypost.controller.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetPostsResponseDto {
    private final Long id;

    private final String title;

    private final String content;
}

