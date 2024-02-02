package org.illy.backend.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.user.dto.*;
import org.illy.backend.user.entity.Education;
import org.illy.backend.user.entity.Tag;
import org.illy.backend.user.entity.User;
import org.illy.backend.user.repository.EducationRepository;
import org.illy.backend.user.repository.TagRepository;
import org.illy.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserHomeMapper userHomeMapper;
    private final TagRepository tagRepository;
    private final EducationRepository educationRepository;

    public List<ProjectResponseDto> getUserProjects(User user) {
        return userRepository.getProjectsByUser(user.getId());
    }

    @Transactional
    public UserProfileDto getUserDetailAndProjects(User user) {
        return userHomeMapper.mapUserToDTO(user);
    }

    // 관심 분야 / 주제 저장
    public UserInterestDto updateUserInterest(User user, UserInterestDto userInterest) {
        user.updateInterest(userInterest.getInterestField(), userInterest.getInterestProject());
        userRepository.save(user);
        return UserInterestDto.builder()
                .interestField(user.getInterestField())
                .interestProject(user.getInterestProject()).build();
    }
}
