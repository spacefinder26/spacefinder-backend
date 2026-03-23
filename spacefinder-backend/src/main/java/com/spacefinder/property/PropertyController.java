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

    @PostMapping("/add")
    public ResponseEntity<?> createProperty(@RequestBody Property property){
        try{
            Property newProperty = propertyService.addProperty(property);
            return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while creating a property", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProperty(@PathVariable("id") Long id) {
        return propertyService.getProperty(id)
                .map(property -> ResponseEntity.ok(property))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Property>> getAll(){
        List<Property> properties = new ArrayList<>();
        properties = propertyService.getAllProperties();

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable("id") Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProperty(@RequestBody Property property){
        Property updateProperty = propertyService.updateProperty(property);
        return new ResponseEntity<>(updateProperty, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProperty() {
        return null;
    }
}
