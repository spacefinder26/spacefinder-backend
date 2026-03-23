package com.spacefinder.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking){
        Booking newBooking = bookingService.addBooking(booking);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBooking(@PathVariable("id") Long id) {
        return bookingService.getBooking(id)
                .map(booking -> ResponseEntity.ok(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Booking>> getAll(){
        List<Booking> bookings = new ArrayList<>();
        bookings = bookingService.getAllBookings();

        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBooking(@RequestBody Booking booking){
        Booking updateBooking = bookingService.updateBooking(booking);
        return new ResponseEntity<>(updateBooking, HttpStatus.OK);
    }

}
