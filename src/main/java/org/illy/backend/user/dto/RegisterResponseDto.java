package org.illy.backend.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponseDto {

    private Long userId;
}
