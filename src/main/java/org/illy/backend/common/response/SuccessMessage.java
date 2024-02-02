package org.illy.backend.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public enum SuccessMessage {
    /**
     * auth
     */
    SIGNUP_SUCCESS(CREATED, "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(CREATED, "로그인이 성공했습니다."),

    /**
     * user
     */
    GET_USER_DATA(OK, "유저 정보 조회에 성공했습니다."),
    PATCH_USER_SUCCESS(OK, "유저 수정에 성공했습니다."),
    HOME_CHECK_SUCCESS(OK, "홈 조회 성공했습니다."),
    UPDATE_USER_SUCCESS(OK, "유저 프로필을 수정했습니다."),
    UPDATE_USER_INTEREST_SUCCESS(OK, "유저 관심분야를 수정했습니다."),

    /**
     * portfolio
     */
    GET_PORTFOLIO_SUCCESS(OK, "유저 포트폴리오 조회에 성공했습니다."),
    ADD_PROJECT_SUCCESS(CREATED, "유저 프로젝트를 추가했습니다."),
    ADD_PROJECT_IMAGE_SUCCESS(CREATED, "유저 프로젝트 이미지를 추가했습니다."),
    UPDATE_PROJECT_SUCCESS(OK, "유저 프로젝트를 수정했습니다."),

    /**
     * event
     */
    GET_EVENT_SUCCESS(OK, "공고 조회에 성공했습니다."),
    SAVE_EVENT_SUCCESS(CREATED, "공고 저장에 성공했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
