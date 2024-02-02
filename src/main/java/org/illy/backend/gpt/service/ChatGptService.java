package org.illy.backend.gpt.service;

import lombok.RequiredArgsConstructor;
import org.illy.backend.config.gpt.ChatGptConfig;
import org.illy.backend.gpt.dto.ChatGptMessage;
import org.illy.backend.gpt.dto.ChatGptRequest;
import org.illy.backend.gpt.dto.ChatGptResponse;
import org.illy.backend.project.entity.Project;
import org.illy.backend.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ChatGptService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${dev.gpt.api-key}")
    private String apiKey;

    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatGptRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        httpHeaders.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + apiKey);
        return new HttpEntity<>(chatGptRequest, httpHeaders);
    }

    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatGptRequestHttpEntity){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60 * 1000);   //  답변 시간 1분 제한
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.CHAT_URL,
                chatGptRequestHttpEntity,
                ChatGptResponse.class);

        return responseEntity.getBody();
    }

    public ChatGptResponse askQuestionToMakePortfolio(String project){
        List<ChatGptMessage> messages = new ArrayList<>();
        String prompt = project + "\n 이 프로젝트 설명을 바탕으로 프로젝트 이름, 주제, 분야, 역할, 기간, 상세 내용을 작성해줘. 마땅한 내용이 없으면 '없음'으로 작성해주고 형식은 '프로젝트 이름: ' 이런 식으로 작성해줘. " +
                "이 모든 결과를 총 600자 이내로 리턴해줘.";
        messages.add(ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(prompt)
                .build());
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.CHAT_MODEL,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.STREAM,
                                messages
                        )
                )
        );
    }
    public ChatGptResponse askQuestion(User user){
        List<ChatGptMessage> messages = new ArrayList<>();
        List<Project> projects = user.getProjects();
        List<String> contents = new ArrayList<>();

        for (Project project : projects) {
            String content = project.getContent();
            if (content != null) {
                contents.add(content);
            }
        }

        String contentJoin = String.join(",", contents);

        String prompt = contentJoin + "\n 이 프로젝트 내용들을 바탕으로 기업 자기소개서를 1500자 내로 작성해줘";
        messages.add(ChatGptMessage.builder()
                .role(ChatGptConfig.ROLE)
                .content(prompt)
                .build());
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfig.CHAT_MODEL,
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.STREAM,
                                messages
                        )
                )
        );
    }
}
