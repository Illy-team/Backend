package org.illy.backend.user.repository;

import org.illy.backend.project.dto.ProjectResponseDto;
import org.illy.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT p FROM Project p WHERE p.user.id=:user_id")
    List<ProjectResponseDto> getProjectsByUser(Long user_id);
}
