package org.illy.backend.gpt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.illy.backend.gpt.dto.ChatGptResponse;
import org.illy.backend.gpt.service.ChatGptService;
import org.illy.backend.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
@RestController
public class GptController {
    private final ChatGptService chatGptService;

    @PostMapping
    public ChatGptResponse sendQuestion(
            Locale locale,
            HttpServletRequest request,
            HttpServletResponse response,
            @AuthenticationPrincipal User user) {
        ChatGptResponse chatGptResponse = null;
        try {
            chatGptResponse = chatGptService.askQuestion(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return chatGptResponse;
//        return apiResponse.getResponseEntity(locale, code,
//                chatGptResponse != null ? chatGptResponse.getChoices().get(0).getMessage().getContent() : new ChatGptResponse());
    }
}
