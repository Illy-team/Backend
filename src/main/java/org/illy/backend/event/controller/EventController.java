package org.illy.backend.event.controller;

import lombok.RequiredArgsConstructor;
import org.illy.backend.common.response.SuccessMessage;
import org.illy.backend.event.dto.EventReqDto;
import org.illy.backend.event.dto.EventResponseDto;
import org.illy.backend.event.service.EventService;
import org.illy.backend.common.response.ApiResponse;
import org.illy.backend.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    // 추천받은 공고 조회
    @GetMapping("/{category}")
    public ApiResponse<List<EventResponseDto>> getRecommendedEvents(@AuthenticationPrincipal User user, @PathVariable String category, @RequestParam(value="keyword", required = false) String keyword) {
        if (keyword == null) {
            List<EventResponseDto> data = service.getRecommendedEvents(user, category);
            return ApiResponse.success(SuccessMessage.GET_EVENT_SUCCESS, data);
        }
        else {
            List<EventResponseDto> data = service.searchEvents(user, category, keyword);
            return ApiResponse.success(SuccessMessage.GET_EVENT_SUCCESS, data);
        }
    }

    // 맞춤 공고 중 검색
    @GetMapping("/search/{category}")
    public ApiResponse<List<EventResponseDto>> searchEvents(@AuthenticationPrincipal User user, @PathVariable String category, @RequestParam(value="keyword") String keyword) {
        List<EventResponseDto> data = service.searchEvents(user, category, keyword);
        return ApiResponse.success(SuccessMessage.GET_EVENT_SUCCESS, data);
    }

    // 캘린더 피드 조회
    @GetMapping("/saved")
    public ApiResponse<List<EventResponseDto>> getSavedEvents(@AuthenticationPrincipal User user) {
        List<EventResponseDto> data = service.getSavedEvents(user);
        return ApiResponse.success(SuccessMessage.GET_EVENT_SUCCESS, data);
    }

    // 공고 저장
    @PostMapping("/save")
    public ApiResponse<EventResponseDto> saveEvent(@AuthenticationPrincipal User user, @RequestBody EventReqDto eventReqDto) {
        EventResponseDto data = service.saveEvent(user, eventReqDto);
        return ApiResponse.success(SuccessMessage.SAVE_EVENT_SUCCESS, data);
    }

    // 저장했던 공고 삭제
    @DeleteMapping
    public ApiResponse<String> deleteUserProject(@RequestBody EventReqDto eventReqDto, @AuthenticationPrincipal User user) {
        service.deleteUserEvent(user, eventReqDto);
        return ApiResponse.success(SuccessMessage.DELETE_EVENT_SUCCESS, "삭제 완료");
    }
}
