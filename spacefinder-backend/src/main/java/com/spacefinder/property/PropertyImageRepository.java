package com.spacefinder.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    List<PropertyImage> findByPropertyIdOrderBySortOrderAsc(Long propertyId);

    Optional<PropertyImage> findByIdAndPropertyId(Long id, Long propertyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM PropertyImage i WHERE i.property.id = :propertyId")
    void deleteByPropertyId(Long propertyId);

    int countByPropertyId(Long propertyId);
}
