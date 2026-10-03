package com.campus.controller;

import com.campus.model.Registration;
import com.campus.repository.EventRepository;
import com.campus.repository.RegistrationRepository;
import com.campus.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {
    private final EventRepository eventRepo;
    private final RegistrationRepository regRepo;
    private final RegistrationService service;

    public EventController(EventRepository eventRepo, RegistrationRepository regRepo, RegistrationService service) {
        this.eventRepo = eventRepo;
        this.regRepo = regRepo;
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model m) {
        m.addAttribute("events", eventRepo.findAll());
        return "events";
    }

    @GetMapping("/register")
    public String form(Model m) {
        m.addAttribute("events", eventRepo.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String submit(@RequestParam String name, @RequestParam String rollNo,
                         @RequestParam(defaultValue = "") String email, @RequestParam Long eventId, Model m) {
        try {
            service.register(new Registration(name, rollNo, email, eventId));
            m.addAttribute("msg", "Registered successfully");
            m.addAttribute("ok", true);
        } catch (IllegalStateException ex) {
            m.addAttribute("msg", ex.getMessage());
            m.addAttribute("ok", false);
        }
        m.addAttribute("events", eventRepo.findAll());
        return "register";
    }

    @GetMapping("/organizer")
    public String organizer(Model m) {
        m.addAttribute("events", eventRepo.findAll());
        m.addAttribute("registrations", regRepo.findAll());
        return "organizer";
    }
}
