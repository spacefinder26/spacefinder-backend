package com.spacefinder.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spacefinder.booking.Booking;
import com.spacefinder.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "properties")
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String location;
    private Double size;
    private String status;
    private Integer bedroom;
    private Integer bathroom;
    private Integer parking;
    private String propertyType;
    private Date listingDate;
    private Boolean transferDuty;
    private Boolean pets;
    @Lob
    private Blob photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private User agent;

    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public Property() {
    }

    public Property(Long id, String title, String description, Double price, String location, Double size, String status, Integer bedroom, Integer bathroom, Integer parking, String propertyType, Date listingDate, Boolean transferDuty, Boolean pets, User agent, List<Booking> bookings) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.size = size;
        this.status = status;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.parking = parking;
        this.propertyType = propertyType;
        this.listingDate = listingDate;
        this.transferDuty = transferDuty;
        this.pets = pets;
        this.agent = agent;
        this.bookings = bookings;
    }
}