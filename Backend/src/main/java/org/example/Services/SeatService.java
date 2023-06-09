package org.example.Services;

import org.example.Model.CinemaHall;
import org.example.Model.Seanse;
import org.example.Model.Seat;
import org.example.Model.SeatType;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.SeatRepository;
import org.example.Repository.SeatTypeRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

        if(cinemaHallRepository.existsById(id))
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
        else {
            throw new NoSuchElementException("Cinema hall with id " + id + " does not exist");
        }
    }

   public Seat getSeat(Long id)
   {
       return seatRepository.findById(id).orElseThrow(
               () -> new NoSuchElementException("Seat with id " + id + " does not exist"));
   }

    public Seat addSeat(int row, int column, int seatTypeId, long cinemaHallId) throws NoSuchElementException {
        if (cinemaHallRepository.existsById(cinemaHallId) && seatTypeRepository.existsById(seatTypeId)) {
            Seat seat = new Seat();
            CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
            SeatType seatType = seatTypeRepository.findById(seatTypeId).get();
            seat.setSeatType(seatType);
            seat.setColumnInHall(column);
            seat.setRowInHall(row);
            seat.setCinemaHall(cinemaHall);

            seatRepository.saveAndFlush(seat);
            return seat;
        } else {
            throw new NoSuchElementException("Cinema hall with id " + cinemaHallId + " or seat type with id " + seatTypeId+ " does not exist");
        }
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
        else
        {
         throw  new NoSuchElementException("Seat with id " + id + " or seat type with id " + seatTypeId +" does not exist");
        }

    }
}
