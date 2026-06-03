package com.spacefinder.property;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class PropertyRequest {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String location;
    private Double size;
    private String status;
    private Integer bedroom;
    private Integer bathroom;
    private Integer parking;
    private String propertyType;
    private Date listingDate;
    private Boolean transferDuty;
    private Boolean pets;
    private List<MultipartFile> images;
}
