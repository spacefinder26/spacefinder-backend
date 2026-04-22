package com.spacefinder.property;

import lombok.Data;

import java.util.List;

@Data
public class PropertySearchResponse {
    private List<PropertyResponse> properties;
    private int currentPage;
    private int totalPages;
    private long totalResults;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
