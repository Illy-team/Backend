package org.illy.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.illy.backend.common.response.ErrorMessage;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    ErrorMessage errorMessage;
}