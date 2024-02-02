package org.illy.backend.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    // 채용공고: 회사 이름, 이미지, 분야, 채용 공고 제목, 지원 마감일, 모집 직무, 지원 자격, 우대 사항, 링크
    // 공모전 & 대외활동: 주최 이름, 이미지, 분야, 제목, 지원 날짜, 활동 날짜, 링크

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    // 공통
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;  // 채용 / 공모전 / 대외활동

    @Column(nullable = false)
    private String hostName;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String applyDate;

    @Column(nullable = false)
    private String link;

    // 채용 공고
    @Column(columnDefinition = "LONGTEXT")
    private String tasks;

    @Column(columnDefinition = "LONGTEXT")
    private String requirement;

    @Column(columnDefinition = "LONGTEXT")
    private String preferred;

    // 공모전 & 대외활동
    @Column
    private String activityDate;
}
