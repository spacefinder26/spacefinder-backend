package com.spacefinder.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
