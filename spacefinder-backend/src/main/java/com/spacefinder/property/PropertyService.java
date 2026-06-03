package com.spacefinder.property;

import com.spacefinder.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    private static final int MAX_IMAGES = 10;

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

        // Upload images if provided
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            uploadImages(saved, request.getImages());
        }

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

        // Upload new images if provided
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            uploadImages(property, request.getImages());
        }
        Property saved = propertyRepository.save(property);

        return mapToResponse(saved);
    }

    // Delete Property
    public void deleteProperty(Long id) {
        propertyRepository.findPropertyById(id)
                .orElseThrow(() -> new RuntimeException("Property not found: " + id));
        List<PropertyImage> images = propertyImageRepository
                .findByPropertyIdOrderBySortOrderAsc(id);
        images.forEach(image -> storageService.deleteImage(image.getImageUrl()));

        propertyRepository.deleteById(id);
    }

    // Delete Single Image
    public void deleteImage(Long propertyId, Long imageId) {
        PropertyImage image = propertyImageRepository
                .findByIdAndPropertyId(imageId, propertyId)
                .orElseThrow(() -> new RuntimeException("Image not found: " + imageId));
        storageService.deleteImage(image.getImageUrl());
        propertyImageRepository.delete(image);
    }

    //Search
    public PropertySearchResponse searchProperties(PropertySearchRequest request) {

        // Pagination defaults
        int page     = request.getPage()    != null ? request.getPage()    : 0;
        int size     = request.getSize()    != null ? request.getSize()    : 10;
        String sortBy  = request.getSortBy()  != null ? request.getSortBy()  : "listingDate";
        String sortDir = request.getSortDir() != null ? request.getSortDir() : "desc";

        // Build sort direction
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Build dynamic filters
        Specification<Property> spec = PropertySpecification.withFilters(
                request.getKeyword(),
                request.getLocation(),
                request.getPropertyType(),
                request.getStatus(),
                request.getMinPrice(),
                request.getMaxPrice(),
                request.getBedroom(),
                request.getBathroom(),
                request.getPets(),
                request.getTransferDuty()
        );

        // Execute query
        Page<Property> result = propertyRepository.findAll(spec, pageable);

        // Build response
        PropertySearchResponse response = new PropertySearchResponse();
        response.setProperties(result.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
        response.setCurrentPage(result.getNumber());
        response.setTotalPages(result.getTotalPages());
        response.setTotalResults(result.getTotalElements());
        response.setPageSize(result.getSize());
        response.setHasNext(result.hasNext());
        response.setHasPrevious(result.hasPrevious());

        return response;
    }

    // HELPER — Upload images to R2 and save URLs to DB
    private void uploadImages(Property property, List<MultipartFile> files) {
        Integer existingCount = propertyImageRepository.countByPropertyId(property.getId());

        if (existingCount + files.size() > MAX_IMAGES) {
            throw new RuntimeException("Maximum " + MAX_IMAGES
                    + " images allowed. Currently has: " + existingCount);
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed: "
                        + file.getOriginalFilename());
            }

            // Upload to R2 — get back public URL
            String imageUrl = storageService.uploadImage(file, property.getId());

            // First image is primary if no images exist
            Boolean isPrimary = existingCount == 0 && i == 0;

            // Save URL to DB
            existingCount += i;
            propertyImageRepository.save(new PropertyImage(property, imageUrl, isPrimary, existingCount));
        }
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

        // Image URLs — just the list of public URLs
        List<String> imageUrls = propertyImageRepository
                .findByPropertyIdOrderBySortOrderAsc(property.getId())
                .stream()
                .map(PropertyImage::getImageUrl)
                .collect(Collectors.toList());
        response.setImageUrls(imageUrls);

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
