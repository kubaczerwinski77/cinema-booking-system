package org.example.Services;

import org.example.Exceptions.AlreadyExistException;
import org.example.Model.Booking;
import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Model.Seat;
import org.example.Repository.BookingRepository;
import org.example.Repository.ReservedSeatsRepository;
import org.example.Repository.SeanseRepository;
import org.example.Repository.SeatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements  IBookingService {
    private final BookingRepository bookingRepository;
    private final SeanseRepository seanseRepository;
    private final SeatRepository seatRepository;

    private final ReservedSeatsRepository reservedSeatsRepository;


    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository,
                          SeanseRepository seanseRepository, ReservedSeatsRepository reservedSeatsRepository) {
        this.bookingRepository = bookingRepository;
        this.seanseRepository = seanseRepository;
        this.seatRepository = seatRepository;
        this.reservedSeatsRepository = reservedSeatsRepository;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(long id) {
        return bookingRepository.findById(id).get();
    }


    public Booking addBooking(String email, String name, String lastName, LocalDateTime date, Long seanseId, List<Long> seatId) throws NotFoundException, AlreadyExistException {
        Booking booking = new Booking();
        booking.setEmail(email);
        booking.setName(name);
        booking.setLastName(lastName);
        booking.setDate(date);
        List<Seat> seatList = new ArrayList<>();
        for (Long id : seatId) {
            if (seatRepository.existsById(id))
                seatList.add(seatRepository.findById(id).get());
            else throw new NotFoundException("not found seat");
        }
        Optional<Seanse> seanse = seanseRepository.findById(seanseId);
        if (seanse.isPresent()) {
            bookingRepository.saveAndFlush(booking);
            List<ReservedSeats> reserved = new ArrayList<>();
            for (Seat seat : seatList) {
                try {
                    ReservedSeats reservedSeats = new ReservedSeats(seanse.get(), booking, seat);
                    reserved.add(reservedSeats);

                } catch (Exception e) {
                    throw new NotFoundException("not found seanse");
                }
            }
                try {
                    reservedSeatsRepository.saveAllAndFlush(reserved);

                } catch (Exception e) {
                    bookingRepository.delete(booking);
                    throw new AlreadyExistException();
                }
            return booking;
        } else {
            throw new NotFoundException("not found seanse");
        }
    }




    public boolean deleteBooking(long id) throws NotFoundException {
        if(bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;

        } else {
            throw new NotFoundException("not found booking");
        }

    }
}
