package org.illy.backend.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.illy.backend.project.dto.ProjectResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileUpdateReqDto {
    private String name;
    private String intro;
    private List<TagDto> tags;
    private List<EducationDto> educations;
    private String interestField;
    private String interestProject;
}
