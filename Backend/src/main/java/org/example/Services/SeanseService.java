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
import java.util.NoSuchElementException;

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
        return seanseRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Seanse with id " + id + " does not exist"));
    }

    public Seanse updateSeanse(long id, LocalDateTime date, long movieId, long cinemaHallId) throws NoSuchElementException {
        if (seanseRepository.existsById(id) && cinemaHallRepository.existsById(cinemaHallId)) {
            Seanse seanse = seanseRepository.findById(id).get();
            seanse.setDate(date);
            seanse.setMovieId(movieId);
            CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
            seanse.setCinemaHall(cinemaHall);
            return seanse;

        } else {
            throw new NoSuchElementException("Seanse with id " + id + " does not exist");
        }
    }

    public Seanse addSeanse(LocalDateTime date, long movieId, long cinemaHallId)  {
        if (cinemaHallRepository.existsById(cinemaHallId)) {
            Seanse seanse = new Seanse();
            seanse.setDate(date);
            seanse.setMovieId(movieId);
            CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId).get();
            seanse.setCinemaHall(cinemaHall);
            seanseRepository.saveAndFlush(seanse);
            return seanse;
        } else {
            throw new NoSuchElementException("Cinema hall with id " + cinemaHallId + " does not exist");
        }
    }


    public boolean deleteSeanse(long id) throws NotFoundException {
        if(seanseRepository.existsById(id)) {
            seanseRepository.deleteById(id);
            return true;

        } else {
            throw new NoSuchElementException("Seanse with id " + id + " does not exist");
        }

    }

    public List<Seanse> getSeanseOfMovie(long movieId) throws NoSuchElementException {
        List<Seanse> seanses = seanseRepository.findAll().stream().filter(r -> r.getMovieId() == movieId).toList();

        if (seanses.isEmpty())
            throw new NoSuchElementException("Movie with id " + movieId + " does not exist");

        return seanses;
    }

    public List<Seanse> getDaysSeanses(LocalDate date) throws NoSuchElementException {
        List<Seanse> seanses = seanseRepository.findAll().stream().filter(r -> r.getDate().toLocalDate() == date).toList();

        if (seanses.isEmpty())
            throw new NoSuchElementException("Seanses in " + date + " do not exist");

        return seanses;
    }


}
