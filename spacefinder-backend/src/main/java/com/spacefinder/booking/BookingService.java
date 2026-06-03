package com.spacefinder.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking addBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBooking(Long id){
        return bookingRepository.findBookingById(id);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }

    public Booking updateBooking(Booking booking){
       bookingRepository.findBookingById(booking.getId())
               .orElseThrow(() -> new RuntimeException("Booking was not found: " + booking.getId()));

       return bookingRepository.save(booking);
    }

    //MAPPER - Entity -> Response
    public BookingResponse mapToResponse(Booking booking){
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setNotes(booking.getNotes());
        response.setStatus(String.valueOf(booking.getStatus()));
        response.setViewingDate(booking.getViewingDate());
        return response;
    }

    //MAPPER - Request -> Entity
    public Booking mapToEntity(BookingRequest request){
        Booking booking = new Booking();
        booking.setNotes(request.getNotes());
        booking.setStatus(BookingStatus.valueOf(request.getStatus()));
        booking.setViewingDate(request.getViewingDate());
        return booking;
    }
}
