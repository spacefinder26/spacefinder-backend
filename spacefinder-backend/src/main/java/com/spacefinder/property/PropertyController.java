package com.spacefinder.property;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final ObjectMapper objectMapper;

    // Add property
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyResponse> addProperty(@RequestPart("data") String data,
                                                        @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException{
        PropertyRequest request = objectMapper.readValue(data, PropertyRequest.class);
        if(images != null){
            request.setImages(images);
        }
        PropertyResponse response = propertyService.addProperty(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET property
    @GetMapping("/get/{id}")
    public ResponseEntity<PropertyResponse> getProperty(@PathVariable("id") Long id) {
        PropertyResponse response = propertyService.getProperty(id);
        return ResponseEntity.ok(response);
    }

    //GET a list of all properties
    @GetMapping("/get/all")
    public ResponseEntity<List<PropertyResponse>> getAll(){
        List<PropertyResponse> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    // Update an existing property
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable Long id,
            @RequestPart("data") PropertyRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images){
        if (images != null){
            request.setImages(images);
        }

        return ResponseEntity.ok(propertyService.updateProperty(id, request));
    }

    // Delete property
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // Delete property image
    // AGENT/AGENCY/ADMIN only
    @DeleteMapping("/delete/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id, @PathVariable Long imageId){
        propertyService.deleteImage(id,imageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PropertySearchResponse> searchProperties(@RequestBody PropertySearchRequest request) {
        PropertySearchResponse response = propertyService.searchProperties(request);
        return ResponseEntity.ok(response);
    }
}
