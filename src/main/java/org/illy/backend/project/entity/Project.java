package org.illy.backend.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.illy.backend.project.dto.ProjectRequestDto;
import org.illy.backend.user.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String date;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public void updateProject(ProjectRequestDto projectRequestDto) {
        this.title = projectRequestDto.getTitle();
        this.subject = projectRequestDto.getSubject();
        this.category = projectRequestDto.getCategory();
        this.role = projectRequestDto.getRole();
        this.date = projectRequestDto.getDate();
        this.content = projectRequestDto.getContent();
    }
}
