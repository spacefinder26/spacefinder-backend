package com.spacefinder.property;

import com.spacefinder.booking.Booking;
import com.spacefinder.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    // Add Property
    // Agent extracted from JWT token
    public PropertyResponse addProperty(PropertyRequest request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User agent = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        Property property = mapToEntity(request);
        property.setAgent(agent);

        Property saved = propertyRepository.save(property);
        return mapToResponse(saved);
    }

    // Get Property by ID
    public PropertyResponse getProperty(Long id) {
        Property property = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found: " + id));
        return mapToResponse(property);
    }

    // Get All Properties
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Update Property
    public PropertyResponse updateProperty(Long id, PropertyRequest request) {
        Property property = propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found: " + id));

        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setLocation(request.getLocation());
        property.setSize(request.getSize());
        property.setStatus(request.getStatus());
        property.setBedroom(request.getBedroom());
        property.setBathroom(request.getBathroom());
        property.setParking(request.getParking());
        property.setPropertyType(request.getPropertyType());
        property.setListingDate(request.getListingDate());
        property.setTransferDuty(request.getTransferDuty());
        property.setPets(request.getPets());

        Property saved = propertyRepository.save(property);
        return mapToResponse(saved);
    }

    // Delete Property
    public void deleteProperty(Long id) {
        propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found: " + id));
        propertyRepository.deleteById(id);
    }

    // MAPPER — Entity → Response
    public PropertyResponse mapToResponse(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setTitle(property.getTitle());
        response.setDescription(property.getDescription());
        response.setPrice(property.getPrice());
        response.setLocation(property.getLocation());
        response.setSize(property.getSize());
        response.setStatus(property.getStatus());
        response.setBedroom(property.getBedroom());
        response.setBathroom(property.getBathroom());
        response.setParking(property.getParking());
        response.setPropertyType(property.getPropertyType());
        response.setListingDate(property.getListingDate());
        response.setTransferDuty(property.getTransferDuty());
        response.setPets(property.getPets());

        // Map agent summary — only safe fields, no password/authorities
        if (property.getAgent() != null) {
            AgentSummary agent = new AgentSummary();
            agent.setId(property.getAgent().getId());
            agent.setName(property.getAgent().getName());
            agent.setSurname(property.getAgent().getSurname());
            agent.setEmail(property.getAgent().getEmail());
            agent.setPhone(property.getAgent().getPhone());
            response.setAgent(agent);
        }
        return response;
    }

    // MAPPER — Request → Entity
    private Property mapToEntity(PropertyRequest request) {
        Property property = new Property();
        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setPrice(request.getPrice());
        property.setLocation(request.getLocation());
        property.setSize(request.getSize());
        property.setStatus(request.getStatus());
        property.setBedroom(request.getBedroom());
        property.setBathroom(request.getBathroom());
        property.setParking(request.getParking());
        property.setPropertyType(request.getPropertyType());
        property.setListingDate(request.getListingDate());
        property.setTransferDuty(request.getTransferDuty());
        property.setPets(request.getPets());
        return property;
    }
}
