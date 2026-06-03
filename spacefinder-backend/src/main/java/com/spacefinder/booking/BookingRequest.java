package com.spacefinder.booking;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long id;
    private LocalDateTime viewingDate;
    private String status;
    private String notes;
}
