package com.campus.model;

import jakarta.persistence.*;

@Entity
public class Registration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String rollNo;
    private String email;
    private Long eventId;

    public Registration() {}
    public Registration(String name, String rollNo, String email, Long eventId) {
        this.name = name; this.rollNo = rollNo; this.email = email; this.eventId = eventId;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
