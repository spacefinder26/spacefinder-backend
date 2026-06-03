package com.spacefinder.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property_images")
public class PropertyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private String imageUrl;
    private Boolean isPrimary;
    private Integer sortOrder;

    public PropertyImage() {
    }

    public PropertyImage(Property property, String imageUrl, Boolean isPrimary, Integer sortOrder) {
        this.property = property;
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
        this.sortOrder = sortOrder;
    }
}
