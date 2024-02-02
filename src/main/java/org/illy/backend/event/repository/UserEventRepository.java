package org.illy.backend.event.repository;

import org.illy.backend.event.dto.EventResponseDto;
import org.illy.backend.event.entity.EventType;
import org.illy.backend.event.entity.RelationType;
import org.illy.backend.event.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    @Query("SELECT e FROM Event e JOIN UserEvent ue ON ue.event.id = e.id " +
            "WHERE e.type = :eventType and ue.user.id = :user_id AND ue.type = :relationType")
    List<EventResponseDto> findEventsByUserAndType(Long user_id, EventType eventType, RelationType relationType);

    @Query("SELECT ue FROM UserEvent ue " +
            "WHERE ue.event.id = :event_id and ue.user.id = :user_id AND ue.type = :relationType")
    Optional<UserEvent> findEventsByUserAndEventId(Long user_id, Long event_id, RelationType relationType);

    @Query("SELECT e FROM Event e JOIN UserEvent ue ON ue.event.id = e.id " +
            "WHERE ue.user.id = :user_id AND ue.type=org.illy.backend.event.entity.RelationType.SAVED" +
            " ORDER BY " +
            "  CASE " +
            "    WHEN LOCATE('~', e.applyDate) > 0 THEN FUNCTION('STR_TO_DATE', SUBSTRING(e.applyDate, LOCATE('~', e.applyDate) + 2), '%Y.%m.%d') " +
            "    WHEN FUNCTION('STR_TO_DATE', e.applyDate, '%Y-%m-%d') IS NOT NULL THEN FUNCTION('STR_TO_DATE', e.applyDate, '%Y-%m-%d') " +
            "    ELSE FUNCTION('STR_TO_DATE', '9999-12-31', '%Y-%m-%d') " +
            "  END")
    List<EventResponseDto> findSavedEvents(Long user_id);
}
