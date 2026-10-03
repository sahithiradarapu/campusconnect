package com.campus;

import com.campus.model.Event;
import com.campus.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CampusConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusConnectApplication.class, args);
    }

    /** Seed sample events at startup (in-memory H2 database). */
    @Bean
    CommandLineRunner seed(EventRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Event("Hackathon 2026", "15 Nov 2026", "CSE Seminar Hall", 100));
                repo.save(new Event("Tech Quiz", "18 Nov 2026", "Block B, Room 204", 60));
                repo.save(new Event("DevOps Workshop", "22 Nov 2026", "Computer Lab 3", 40));
            }
        };
    }
}
