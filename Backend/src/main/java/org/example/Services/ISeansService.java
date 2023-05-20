package org.example.Services;

import org.example.Model.Cinema;
import org.example.Model.Seanse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ISeansService {

    public List<Seanse> getSeanses();

    Seanse getSeanse(long id);
    Seanse addSeanse(LocalDateTime date, long movieId, long cinemaHallId);
    Seanse updateSeanse(long id, LocalDateTime date, long movieId, long cinemaHallId);
    boolean deleteSeanse(long id);
    public List<Seanse> getSeanseOfMovie(long movieId);
    public List<Seanse> getDaysSeanses(LocalDate date);
}
