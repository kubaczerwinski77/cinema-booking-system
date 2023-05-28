package org.example.Services;

import org.example.Exceptions.AlreadyExistException;
import org.example.Model.Booking;
import org.example.Model.Cinema;
import org.example.Model.Seanse;

import java.time.LocalDateTime;
import java.util.List;

public interface IBookingService {

    public List<Booking> getBookings();

    Booking getBooking(long id);
    Booking addBooking(String email, String name, String lastName, LocalDateTime date, Long seanseId, List<Long> seatId) throws AlreadyExistException;
    boolean deleteBooking(long id);
}
