package Services;

import org.example.Model.CinemaHall;

import java.util.List;

public interface ICinemaHallService {
    public List<CinemaHall> getCinemaHalls();

    CinemaHall getCinemaHall(long id);
    CinemaHall addCinemaHall(String name, int totalSize, int length, int width, long cinemaId);
    CinemaHall updateCinemaHall(long id,String name, int totalSize, int length, int width );
    boolean deleteCinemaHall(long id);
}
