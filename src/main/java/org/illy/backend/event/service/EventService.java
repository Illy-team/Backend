package org.illy.backend.event.service;

import lombok.RequiredArgsConstructor;
import org.illy.backend.common.exception.CustomException;
import org.illy.backend.common.response.ErrorMessage;
import org.illy.backend.event.dto.EventReqDto;
import org.illy.backend.event.dto.EventResponseDto;
import org.illy.backend.event.entity.Event;
import org.illy.backend.event.entity.EventType;
import org.illy.backend.event.entity.RelationType;
import org.illy.backend.event.entity.UserEvent;
import org.illy.backend.event.repository.EventRepository;
import org.illy.backend.event.repository.UserEventRepository;
import org.illy.backend.user.entity.User;
import org.illy.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserEventRepository userEventRepository;

    public List<EventResponseDto> getRecommendedEvents(User user, String category) {
        EventType eventType = null;
        if (Objects.equals(category, "jobs")) {
            eventType = EventType.JOB;
        }
        else if (Objects.equals(category, "contests")) {
            eventType = EventType.CONTEST;
        }
        else if (Objects.equals(category, "activities")) {
            eventType = EventType.ACTIVITY;
        }

        return userEventRepository.findEventsByUserAndType(user.getId(), eventType, RelationType.RECOMMENDED);
    }

    public List<EventResponseDto> searchEvents(User user, String type, String keyword) {
        EventType enum_type = null;
        if (Objects.equals(type, "jobs")) {
            enum_type = EventType.JOB;
        }
        else if (Objects.equals(type, "contests")) {
            enum_type = EventType.CONTEST;
        }
        else if (Objects.equals(type, "activities")) {
            enum_type = EventType.ACTIVITY;
        }
        return eventRepository.findEventsByTypeAndKeyword(user.getId(), enum_type, keyword);
    }

    // 추천 / 저장 공고 저장
    public EventResponseDto saveEvent(User userReq, EventReqDto eventReqDto) {
        User user = userRepository.findById(userReq.getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));

        Event event = eventRepository.findById(eventReqDto.getEventId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_EVENT_EXCEPTION));

        UserEvent userEvent = UserEvent.builder().user(user).event(event).type(RelationType.SAVED).build();
        userEventRepository.save(userEvent);
        return EventResponseDto.builder()
                .eventId(event.getId())
                .type(event.getType())
                .hostName(event.getHostName())
                .image(event.getImage())
                .category(event.getCategory())
                .title(event.getTitle())
                .applyDate(event.getApplyDate())
                .link(event.getLink())
                .tasks(event.getTasks())
                .requirement(event.getRequirement())
                .preferred(event.getPreferred())
                .activityDate(event.getActivityDate())
                .build();
    }

    // 캘린더 피드
    public List<EventResponseDto> getSavedEvents(User user) {
        return userEventRepository.findSavedEvents(user.getId());
    }

}
