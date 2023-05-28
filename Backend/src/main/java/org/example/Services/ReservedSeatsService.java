package org.example.Services;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Model.Seat;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.ReservedSeatsRepository;
import org.example.Repository.SeanseRepository;
import org.example.Repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservedSeatsService implements IReservedSeats{
    private final ReservedSeatsRepository reservedSeatsRepository;
    private final SeanseRepository seanseRepository;
    private final SeatRepository seatRepository;

    public ReservedSeatsService(ReservedSeatsRepository reservedSeatsRepository, SeanseRepository seanseRepository, SeatRepository seatRepository) {
        this.reservedSeatsRepository = reservedSeatsRepository;
        this.seanseRepository = seanseRepository;
        this.seatRepository = seatRepository;
    }

    public List<ReservedSeats> getReservedSeates() {return reservedSeatsRepository.findAll();
    }

    @Override
    public List<Seat> getReservedSeatInSeanse(long id) {
        if(seanseRepository.existsById(id)) {
            List<ReservedSeats> reservedSeats = reservedSeatsRepository.findAll().stream().filter(r -> r.getSeanse().getId() == id).toList();
            List<Seat> seats = new ArrayList<>();
            for(ReservedSeats reserved : reservedSeats)
            {
                seats.add(reserved.getSeat());
            }
            return seats;
        }
        else {
            throw new NoSuchElementException("Seanse with id " + id + " does not exist");
        }
    }
}
