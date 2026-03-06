package com.spacefinder.property;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    private String title;

    private String description;

    private Double price;

    private String location;

    private String status;

}