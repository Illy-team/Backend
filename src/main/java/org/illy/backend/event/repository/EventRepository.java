package org.illy.backend.event.repository;

import org.illy.backend.event.dto.EventResponseDto;
import org.illy.backend.event.entity.Event;
import org.illy.backend.event.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e JOIN UserEvent ue ON ue.event.id = e.id" +
            " WHERE ue.user.id = :user_id and ue.type=org.illy.backend.event.entity.RelationType.RECOMMENDED" +
            " and e.type=:type and (e.hostName LIKE %:keyword% or e.title LIKE %:keyword% or e.category LIKE %:keyword% or e.tasks LIKE %:keyword% or e.requirement LIKE %:keyword% or e.preferred LIKE %:keyword%)")
    List<EventResponseDto> findEventsByTypeAndKeyword(Long user_id, EventType type, String keyword);
}
