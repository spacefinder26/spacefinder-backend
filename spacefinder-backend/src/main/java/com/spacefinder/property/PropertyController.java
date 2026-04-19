package com.spacefinder.property;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // Add property
    @PostMapping("/add")
    public ResponseEntity<PropertyResponse> addProperty(@RequestBody PropertyRequest request){
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
    @PutMapping("/update")
    public ResponseEntity<PropertyResponse> updateProperty(@PathVariable Long id, @RequestBody PropertyRequest request){
        PropertyResponse response = propertyService.updateProperty(id, request);
        return ResponseEntity.ok(response);
    }

    // Delete property
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
