package org.illy.backend.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long id;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationType type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    public void updateEducation(String major, String school, String duration, String type) {
        this.major = major;
        this.school = school;
        this.duration = duration;
        this.type = EducationType.valueOf(type);
    }
}
