package com.campus.model;

import jakarta.persistence.*;

@Entity
public class Event {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String eventDate;
    private String venue;
    private int seatsLeft;

    public Event() {}
    public Event(String title, String eventDate, String venue, int seatsLeft) {
        this.title = title; this.eventDate = eventDate; this.venue = venue; this.seatsLeft = seatsLeft;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public int getSeatsLeft() { return seatsLeft; }
    public void setSeatsLeft(int seatsLeft) { this.seatsLeft = seatsLeft; }
}
