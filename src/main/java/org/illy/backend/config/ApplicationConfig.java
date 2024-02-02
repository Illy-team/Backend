package org.illy.backend.config;

import lombok.RequiredArgsConstructor;
import org.illy.backend.common.exception.CustomException;
import org.illy.backend.common.response.ErrorMessage;
import org.illy.backend.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));
    }

}
