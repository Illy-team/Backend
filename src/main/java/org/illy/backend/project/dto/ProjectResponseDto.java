package org.illy.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.illy.backend.project.entity.Project;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDto {
    private Long projectId;
    private String title;
    private String subject;
    private String category;
    private String role;
    private String date;
    private String content;
    private String thumbnail;

    public ProjectResponseDto(Project project) {
        projectId = project.getId();
        title = project.getTitle();
        subject = project.getSubject();
        category = project.getCategory();
        role = project.getRole();
        date = project.getDate();
        content = project.getContent();
        thumbnail = project.getThumbnail();
    }
}
