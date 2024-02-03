package org.illy.backend.project.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.illy.backend.common.exception.CustomException;
import org.illy.backend.common.response.ErrorMessage;
import org.illy.backend.event.dto.EventReqDto;
import org.illy.backend.event.entity.Event;
import org.illy.backend.event.entity.EventType;
import org.illy.backend.event.repository.EventRepository;
import org.illy.backend.gpt.dto.ChatGptResponse;
import org.illy.backend.gpt.service.ChatGptService;
import org.illy.backend.project.ProjectRepository;
import org.illy.backend.project.dto.ProjectCreateDto;
import org.illy.backend.project.dto.ProjectRequestDto;
import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.project.dto.ProjectUploadDto;
import org.illy.backend.project.entity.Project;
import org.illy.backend.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EventRepository eventRepository;
    private final AmazonS3 amazonS3;
    private final ChatGptService chatGptService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    public String uploadImage(Long userId, MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().concat("-" + originalFilename);
            String fileName = userId + "/" + uuid;
            String fileUrl = "https://" + bucket + endpoint + "/" + fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String parseValue(String input, String pattern) {
        Pattern regex = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher matcher = regex.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "없음";
    }

    public ProjectResponseDto addUserProject(ProjectUploadDto projectUploadDto, User user, MultipartFile image) {
        // project 인풋으로 프로젝트 이름, 주제, 분야, 역할, 기간, 상세 내용을 작성
        String s3Url = this.uploadImage(user.getId(), image);
        ChatGptResponse chatGptResponse = chatGptService.askQuestionToMakePortfolio(projectUploadDto.getProject());
        String result = chatGptResponse.getChoices().get(0).getMessage().getContent();

        System.out.println("결과: " + result);

        String title = parseValue(result, "프로젝트 이름: (.+?)\\n주제:");
        String subject = parseValue(result, "주제: (.+?)\\분야:");
        String category = parseValue(result, "분야: (.+?)\\역할:");
        String role = parseValue(result, "역할: (.+?)\\기간:");
        String date = parseValue(result, "기간: (.+?)\\상세 내용:");
        String content = parseValue(result, "상세 내용: (.+)");

        StringBuilder contentRes = new StringBuilder();
        String[] sentences = content.split("\\.");

        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i].trim();
            if (!sentence.isEmpty()) {
                contentRes.append((i + 1) + ". " + sentence + ".\n");
            }
        }


        return ProjectResponseDto.builder()
                .title(title)
                .subject(subject)
                .category(category)
                .role(role)
                .date(date)
                .content(String.valueOf(contentRes))
                .thumbnail(s3Url)
                .build();
    }

    public ProjectResponseDto updateUserProject(ProjectRequestDto projectRequestDto) {
        Optional<Project> project = projectRepository.findById(projectRequestDto.getProjectId());
        
        if (project.isPresent()) {
            project.get().updateProject(projectRequestDto);
            var res = projectRepository.save(project.get());
            return ProjectResponseDto.builder()
                    .projectId(res.getId())
                    .title(res.getTitle())
                    .subject(res.getSubject())
                    .role(res.getRole())
                    .date(res.getDate())
                    .category(res.getCategory())
                    .content(res.getContent())
                    .thumbnail(res.getThumbnail())
                    .build();
        }
        else {
            throw new CustomException(ErrorMessage.NOT_FOUND_PROJECT_EXCEPTION);
        }

    }

    public ProjectResponseDto createUserProject(ProjectCreateDto project, User user) {
        Project saveProject = Project.builder()
                .title(project.getTitle())
                .subject(project.getSubject())
                .user(user)
                .category(project.getCategory())
                .role(project.getRole())
                .date(project.getDate())
                .content(project.getContent())
                .thumbnail(project.getThumbnail())
                .build();

        var res = projectRepository.save(saveProject);
        return ProjectResponseDto.builder()
                .projectId(res.getId())
                .title(res.getTitle())
                .subject(res.getSubject())
                .role(res.getRole())
                .date(res.getDate())
                .category(res.getCategory())
                .content(res.getContent())
                .thumbnail(res.getThumbnail())
                .build();
    }
}
