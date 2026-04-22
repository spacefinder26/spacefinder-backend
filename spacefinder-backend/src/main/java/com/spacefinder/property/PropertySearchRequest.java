package com.spacefinder.property;

import lombok.Data;

@Data
public class PropertySearchRequest {
    private String keyword;       // search title and description
    private String location;      // filter by location
    private String propertyType;  // Apartment, House, Townhouse etc
    private String status;        // For Sale, For Rent, Sold, Rented
    private Double minPrice;      // minimum price
    private Double maxPrice;      // maximum price
    private Integer bedroom;      // minimum number of bedrooms
    private Integer bathroom;     // minimum number of bathrooms
    private Boolean pets;         // pets allowed
    private Boolean transferDuty; // transfer duty included
    private Integer page;         // page number (default 0)
    private Integer size;         // page size (default 10)
    private String sortBy;        // field to sort by (default "listingDate")
    private String sortDir;       // asc or desc (default "desc")
}
