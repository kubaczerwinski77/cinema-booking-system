package org.example.Services;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.ReservedSeatsRepository;
import org.example.Repository.SeanseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservedSeatsService implements IReservedSeats{
    private final ReservedSeatsRepository reservedSeatsRepository;

    public ReservedSeatsService(ReservedSeatsRepository reservedSeatsRepository) {
        this.reservedSeatsRepository = reservedSeatsRepository;
    }

    public List<ReservedSeats> getReservedSeates() {return reservedSeatsRepository.findAll();
    }

    @Override
    public List<ReservedSeats> getReservedSeatInSeanse(long id) {
        return reservedSeatsRepository.findAll().stream().filter(r -> r.getSeanse().getId() == id).toList();
    }
}
