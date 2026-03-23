package com.spacefinder.booking;

import com.spacefinder.property.Property;
import com.spacefinder.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private LocalDateTime viewingDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private String notes;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (status == null) status = BookingStatus.PENDING;
    }

    public Booking() {
    }

    public Booking(Long id, User user, User agent, Property property, LocalDateTime viewingDate, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.agent = agent;
        this.property = property;
        this.viewingDate = viewingDate;
        this.notes = notes;
        this.createdAt = createdAt;
    }
}
