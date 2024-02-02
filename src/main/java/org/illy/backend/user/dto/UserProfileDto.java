package org.illy.backend.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.user.entity.User;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileDto {
    private Long userId;
    private String name;
    private String intro;
    private List<TagDto> tags;
    private List<EducationDto> educations;
    private List<ProjectResponseDto> projects;
    private String interestField;
    private String interestProject;


    @Builder
    public UserProfileDto(User user, List<TagDto> tags, List<EducationDto> educations, List<ProjectResponseDto> projects) {
        this.userId = user.getId();
        this.name = user.getName();
        this.intro = user.getIntro();
        this.tags = tags;
        this.educations = educations;
        this.projects = projects;
        this.interestField = user.getInterestField();
        this.interestProject = user.getInterestProject();
    }
}
