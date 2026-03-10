package com.spacefinder.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property addProperty(Property property){
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }
}
