package org.illy.backend.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterestDto {
    private String interestField;
    private String interestProject;

    @Builder
    public UserInterestDto(String interestField, String interestProject) {
        this.interestField = interestField;
        this.interestProject = interestProject;
    }
}
