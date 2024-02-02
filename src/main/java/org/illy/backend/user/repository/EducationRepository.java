package org.illy.backend.user.repository;

import org.illy.backend.user.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
