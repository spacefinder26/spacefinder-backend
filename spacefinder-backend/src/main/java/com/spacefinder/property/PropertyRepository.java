package com.spacefinder.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findPropertyById(Long id);
    //Property findPropertyByRadius(Integer radius);
}
