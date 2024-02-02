package org.illy.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDto {
    private Long projectId;
    private String title;
    private String subject;
    private String category;
    private String role;
    private String date;
    private String content;
}
