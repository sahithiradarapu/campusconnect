package com.campus.service;

import com.campus.model.Event;
import com.campus.model.Registration;
import com.campus.repository.EventRepository;
import com.campus.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final RegistrationRepository repo;
    private final EventRepository eventRepo;

    public RegistrationService(RegistrationRepository repo, EventRepository eventRepo) {
        this.repo = repo;
        this.eventRepo = eventRepo;
    }

    @Transactional
    public Registration register(Registration r) {
        if (r.getName() == null || r.getName().isBlank())
            throw new IllegalStateException("Name is required");
        if (r.getRollNo() == null || r.getRollNo().isBlank())
            throw new IllegalStateException("Roll number is required");
        if (repo.existsByRollNoAndEventId(r.getRollNo(), r.getEventId()))
            throw new IllegalStateException("Already registered");
        Event e = eventRepo.findById(r.getEventId())
                .orElseThrow(() -> new IllegalStateException("Event not found"));
        if (e.getSeatsLeft() <= 0)
            throw new IllegalStateException("Event is full");
        e.setSeatsLeft(e.getSeatsLeft() - 1);
        eventRepo.save(e);
        return repo.save(r);
    }
}
