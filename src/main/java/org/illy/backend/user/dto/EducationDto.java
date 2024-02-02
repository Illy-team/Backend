package org.illy.backend.user.dto;

import lombok.*;
import org.illy.backend.user.entity.EducationType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {
    private Long educationId;
    private String major;
    private String school;
    private String duration;
    private EducationType type;
}
