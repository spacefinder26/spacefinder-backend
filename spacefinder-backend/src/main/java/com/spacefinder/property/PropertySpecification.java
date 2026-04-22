package com.spacefinder.property;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    // Builds a dynamic query based on whichever filters are provided
    public static Specification<Property> withFilters(
            String keyword,
            String location,
            String propertyType,
            String status,
            Double minPrice,
            Double maxPrice,
            Integer bedroom,
            Integer bathroom,
            Boolean pets,
            Boolean transferDuty
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Keyword — searches title and description
            if (keyword != null && !keyword.isBlank()) {
                String like = "%" + keyword.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), like),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), like)
                ));
            }

            // Location — partial match e.g "Sand" matches "Sandton"
            if (location != null && !location.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                        "%" + location.toLowerCase() + "%"
                ));
            }

            // Property type — exact match e.g "Apartment"
            if (propertyType != null && !propertyType.isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("propertyType")),
                        propertyType.toLowerCase()
                ));
            }

            // Status — e.g "For Sale", "For Rent"
            if (status != null && !status.isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("status")),
                        status.toLowerCase()
                ));
            }

            // Min price
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"), minPrice
                ));
            }

            // Max price
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"), maxPrice
                ));
            }

            // Bedrooms — minimum number of bedrooms
            if (bedroom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("bedroom"), bedroom
                ));
            }

            // Bathrooms — minimum number of bathrooms
            if (bathroom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("bathroom"), bathroom
                ));
            }

            // Pets allowed
            if (pets != null) {
                predicates.add(criteriaBuilder.equal(root.get("pets"), pets));
            }

            // Transfer duty included
            if (transferDuty != null) {
                predicates.add(criteriaBuilder.equal(root.get("transferDuty"), transferDuty));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
