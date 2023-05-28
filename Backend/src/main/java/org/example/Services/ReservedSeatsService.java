package org.example.Services;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.ReservedSeatsRepository;
import org.example.Repository.SeanseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservedSeatsService implements IReservedSeats{
    private final ReservedSeatsRepository reservedSeatsRepository;
    private final SeanseRepository seanseRepository;

    public ReservedSeatsService(ReservedSeatsRepository reservedSeatsRepository, SeanseRepository seanseRepository) {
        this.reservedSeatsRepository = reservedSeatsRepository;
        this.seanseRepository = seanseRepository;
    }

    public List<ReservedSeats> getReservedSeates() {return reservedSeatsRepository.findAll();
    }

    @Override
    public List<ReservedSeats> getReservedSeatInSeanse(long id) {
        if(seanseRepository.existsById(id)) {
            return reservedSeatsRepository.findAll().stream().filter(r -> r.getSeanse().getId() == id).toList();
        }
        else {
            throw new NoSuchElementException("Seanse with id " + id + " does not exist");
        }
    }
}
