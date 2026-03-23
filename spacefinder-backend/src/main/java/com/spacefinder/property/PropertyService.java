package com.spacefinder.property;

import com.spacefinder.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }

    public Optional<Property> getProperty(Long id){
        return propertyRepository.findPropertyById(id);
    }

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public void deleteProperty(Long id){
        propertyRepository.deleteById(id);
    }

    public Property updateProperty(Property property){
        propertyRepository.findPropertyById(property.getId())
                .orElseThrow(() -> new RuntimeException("Property not found: " + property.getId()));

        return propertyRepository.save(property);
    }
}
