package org.illy.backend.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.illy.backend.event.entity.Event;
import org.illy.backend.event.entity.EventType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long eventId;
    private EventType type;
    private String hostName;
    private String image;
    private String category;
    private String title;
    private String applyDate;
    private String link;
    private String tasks;
    private String requirement;
    private String preferred;
    private String activityDate;

    public EventResponseDto(Event event) {
        eventId = event.getId();
        type = event.getType();
        hostName = event.getHostName();
        image = event.getImage();
        category = event.getCategory();
        title = event.getTitle();
        applyDate = event.getApplyDate();
        link = event.getLink();
        tasks = event.getTasks();
        requirement = event.getRequirement();
        preferred = event.getPreferred();
        activityDate = event.getActivityDate();
    }
}
