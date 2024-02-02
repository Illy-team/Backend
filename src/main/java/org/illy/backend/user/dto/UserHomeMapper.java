package org.illy.backend.user.dto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.project.entity.Project;
import org.illy.backend.user.entity.Education;
import org.illy.backend.user.entity.Tag;
import org.illy.backend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHomeMapper {
    public UserProfileDto mapUserToDTO(User user) {
        return UserProfileDto.builder()
                .user(user)
                .tags(user.getTags().stream().filter(Objects::nonNull).map(this::mapTagToDTO).collect(Collectors.toList()))
                .educations(user.getEducations().stream().filter(Objects::nonNull).map(this::mapEducationToDTO).collect(Collectors.toList()))
                .projects(user.getProjects().stream().filter(Objects::nonNull).map(this::mapProjectToDTO).collect(Collectors.toList()))
                .build();
    }

    public TagDto mapTagToDTO(Tag entity) {
        return TagDto.builder()
                .tagId(entity.getId())
                .name(entity.getName())
                .build();
    }

    public EducationDto mapEducationToDTO(Education entity) {
        return EducationDto.builder()
                .educationId(entity.getId())
                .major(entity.getMajor())
                .school(entity.getSchool())
                .duration(entity.getDuration())
                .type(entity.getType())
                .build();
    }

    public ProjectResponseDto mapProjectToDTO(Project entity) {
        return ProjectResponseDto.builder()
                .projectId(entity.getId())
                .title(entity.getTitle())
                .subject(entity.getSubject())
                .category(entity.getCategory())
                .role(entity.getRole())
                .date(entity.getDate())
                .content(entity.getContent())
                .thumbnail(entity.getThumbnail())
                .build();
    }
}
