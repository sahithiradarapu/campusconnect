package com.campus.unit;

import com.campus.model.Event;
import com.campus.model.Registration;
import com.campus.repository.EventRepository;
import com.campus.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RegistrationServiceTest {
    @Autowired RegistrationService service;
    @Autowired EventRepository eventRepo;

    @Test
    void validRegistrationReducesSeats() {
        Event e = eventRepo.save(new Event("Test Event", "01 Jan", "Hall", 2));
        service.register(new Registration("Asha", "R1", "a@x.com", e.getId()));
        assertEquals(1, eventRepo.findById(e.getId()).get().getSeatsLeft());
    }

    @Test
    void duplicateRegistrationIsRejected() {
        Event e = eventRepo.save(new Event("Test Event", "01 Jan", "Hall", 5));
        service.register(new Registration("Asha", "R2", "a@x.com", e.getId()));
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.register(new Registration("Asha", "R2", "a@x.com", e.getId())));
        assertEquals("Already registered", ex.getMessage());
    }

    @Test
    void fullEventIsRejected() {
        Event e = eventRepo.save(new Event("Tiny Event", "01 Jan", "Hall", 0));
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> service.register(new Registration("Ravi", "R3", "r@x.com", e.getId())));
        assertEquals("Event is full", ex.getMessage());
    }

    @Test
    void emptyNameIsRejected() {
        Event e = eventRepo.save(new Event("Test Event", "01 Jan", "Hall", 5));
        assertThrows(IllegalStateException.class,
                () -> service.register(new Registration("", "R4", "x@x.com", e.getId())));
    }
}
