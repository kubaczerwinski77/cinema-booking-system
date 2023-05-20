package org.example.Services;

import org.example.Model.CinemaHall;
import org.example.Model.Seat;
import org.example.Model.SeatType;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.SeatRepository;
import org.example.Repository.SeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService implements ISeatService{
    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final CinemaHallRepository cinemaHallRepository;


    public SeatService(SeatRepository seatRepo, SeatTypeRepository seatTypeRepo, CinemaHallRepository cinemaHallRepo) {
        this.seatRepository = seatRepo;
        this.seatTypeRepository = seatTypeRepo;
        this.cinemaHallRepository = cinemaHallRepo;

    }
    @Override
    public List<Seat> getSeats(Long id) {
        Optional<CinemaHall> cinemaHall = cinemaHallRepository.findById(id);
        if(cinemaHall != null)
        {
            List<Seat> seats = new ArrayList<>();
            List<Seat> allSeats = seatRepository.findAll();
            for( Seat seat : allSeats)
            {
                if(seat.getCinemaHall().getId() == id)
                    seats.add(seat);
            }
            return seats;

        }
        return null;
    }

   public Seat getSeat(Long id)
   {
       return seatRepository.findById(id).get();
   }

    @Override
    public Seat updateSeat(Long id, int seatTypeId) {
        if(seatRepository.existsById(id) && seatTypeRepository.existsById(seatTypeId))
        {
            Seat seat = seatRepository.findById(id).get();
            SeatType seatType =seatTypeRepository.findById(seatTypeId).get();
            seat.setSeatType(seatType);
            seatRepository.saveAndFlush(seat);
            return seat;
        }
        return null;

    }
}
