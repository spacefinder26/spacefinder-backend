package com.spacefinder.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Double price;

    private String location;

    private Double size;

    private String status;

    public Property() {
    }

    public Property(Long id, String title, String description, Double price, String location, Double size, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.size = size;
        this.status = status;
    }
}