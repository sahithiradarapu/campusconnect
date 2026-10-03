package com.campus.repository;

import com.campus.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByRollNoAndEventId(String rollNo, Long eventId);
    List<Registration> findByEventId(Long eventId);
}
