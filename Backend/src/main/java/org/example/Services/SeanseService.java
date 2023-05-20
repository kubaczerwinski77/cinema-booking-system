package org.example.Services;

import org.example.Model.CinemaHall;
import org.example.Model.Seanse;
import org.example.Repository.CinemaHallRepository;
import org.example.Repository.SeanseRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeanseService implements ISeansService{

    private final SeanseRepository seanseRepository;
    private final CinemaHallRepository cinemaHallRepository;

    public SeanseService(SeanseRepository seanseRepository, CinemaHallRepository cinemaHallRepository) {
        this.seanseRepository = seanseRepository;
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public List<Seanse> getSeanses() {
        return seanseRepository.findAll();
    }

    public Seanse getSeanse(long id) {
        return seanseRepository.findById(id).get();
    }

    public Seanse updateSeanse(long id, LocalDateTime date, long movieId, long cinemaHallId) throws NotFoundException {
        if (seanseRepository.existsById(id) && cinemaHallRepository.existsById(cinemaHallId)) {
            Seanse seanse = seanseRepository.findById(id).get();
            seanse.setDate(date);
            seanse.setMovieId(movieId);
            CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
            seanse.setCinemaHall(cinemaHall);
            return seanse;

        } else {
            throw new NotFoundException("not found seanse or cinemaHall");
        }
    }

    public Seanse addSeanse(LocalDateTime date, long movieId, long cinemaHallId) throws NotFoundException {
        if (cinemaHallRepository.existsById(cinemaHallId)) {
            Seanse seanse = new Seanse();
            seanse.setDate(date);
            seanse.setMovieId(movieId);
            CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
            seanse.setCinemaHall(cinemaHall);
            seanseRepository.saveAndFlush(seanse);
            return seanse;
        } else {
            throw new NotFoundException("not found cinema hall");
        }
    }


    public boolean deleteSeanse(long id) throws NotFoundException {
        if(seanseRepository.existsById(id)) {
            seanseRepository.deleteById(id);
            return true;

        } else {
            throw new NotFoundException("not found seanse");
        }

    }

    public List<Seanse> getSeanseOfMovie(long movieId) throws NotFoundException {
        List<Seanse> seanses = seanseRepository.findAll().stream().filter(r -> r.getMovieId() == movieId).toList();

        if (seanses.isEmpty())
            throw new NotFoundException("not found movie");

        return seanses;
    }

    public List<Seanse> getDaysSeanses(LocalDate date) throws NotFoundException {
        List<Seanse> seanses = seanseRepository.findAll().stream().filter(r -> r.getDate().toLocalDate() == date).toList();

        if (seanses.isEmpty())
            throw new NotFoundException("not found date");

        return seanses;
    }


}
