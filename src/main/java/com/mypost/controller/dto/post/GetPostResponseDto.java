package com.mypost.controller.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetPostResponseDto {
    private final Long id;

    private final String title;

    private final String content;
}

