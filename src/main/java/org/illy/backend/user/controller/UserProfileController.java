package org.illy.backend.user.controller;


import lombok.RequiredArgsConstructor;
import org.illy.backend.common.response.ApiResponse;
import org.illy.backend.common.response.SuccessMessage;
import org.illy.backend.event.dto.EventReqDto;
import org.illy.backend.project.dto.ProjectCreateDto;
import org.illy.backend.project.dto.ProjectRequestDto;
import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.project.dto.ProjectUploadDto;
import org.illy.backend.project.service.ProjectService;
import org.illy.backend.user.dto.UserInterestDto;
import org.illy.backend.user.dto.UserProfileDto;
import org.illy.backend.user.entity.User;
import org.illy.backend.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;
    private final ProjectService projectService;

    // 홈 조회
    @GetMapping
    public ApiResponse<UserProfileDto> getUserDetailAndProjects(@AuthenticationPrincipal User user) {
        UserProfileDto data = userService.getUserDetailAndProjects(user);
        return ApiResponse.success(SuccessMessage.GET_PORTFOLIO_SUCCESS, data);
    }

    // 포트폴리오 업로드 (gpt 사용, 이미지와 내용 같이 등록)
    @PostMapping(value="/projects",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ProjectResponseDto> addUserProject(@RequestPart(value="project", required = true) ProjectUploadDto project, @RequestPart(value="thumbnail", required = false) MultipartFile image, @AuthenticationPrincipal User user) {
        ProjectResponseDto data = projectService.addUserProject(project, user, image);
        return ApiResponse.success(SuccessMessage.ADD_PROJECT_SUCCESS, data);
    }

    // 포트폴리오 최종 저장
    @PostMapping(value="/projects/save")
    public ApiResponse<ProjectResponseDto> saveUserProject(@RequestBody ProjectCreateDto project, @AuthenticationPrincipal User user) {
        ProjectResponseDto data = projectService.createUserProject(project, user);
        return ApiResponse.success(SuccessMessage.ADD_PROJECT_SUCCESS, data);
    }

    // 관심 분야, 관심 프로젝트 저장
    @PostMapping(value="/interest")
    public ApiResponse<UserInterestDto> updateUserInterest(@AuthenticationPrincipal User user, @RequestBody UserInterestDto userInterest) {
        UserInterestDto data = userService.updateUserInterest(user, userInterest);
        return ApiResponse.success(SuccessMessage.UPDATE_USER_INTEREST_SUCCESS, data);
    }

    // 포트폴리오 수정
    @PostMapping("/projects/update")
    public ApiResponse<ProjectResponseDto> updateUserProfile(@RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto data = projectService.updateUserProject(projectRequestDto);
        return ApiResponse.success(SuccessMessage.UPDATE_PROJECT_SUCCESS, data);
    }
}
