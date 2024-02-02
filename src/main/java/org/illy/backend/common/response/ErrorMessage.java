package org.illy.backend.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {
    /**
     * user
     */
    EXPIRED_TOKEN(UNAUTHORIZED, "만료된 토큰입니다."),
    NOT_FOUND_USER_EXCEPTION(NOT_FOUND, "찾을 수 없는 유저입니다."),
    NOT_FOUND_USER_PASSWORD_EXCEPTION(NOT_FOUND, "잘못된 비밀번호입니다."),
    DUPLICATE_USER(CONFLICT, "이미 존재하는 유저입니다."),

    /**
     * event
     */
    NOT_FOUND_EVENT_EXCEPTION(NOT_FOUND, "찾을 수 없는 이벤트입니다."),

    /**
     * project
     */
    NOT_FOUND_PROJECT_EXCEPTION(NOT_FOUND, "찾을 수 없는 프로젝트입니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
