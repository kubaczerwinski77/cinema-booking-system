package Services;

import Repository.CinemaHallRepository;
import Repository.CinemaRepository;
import org.example.Model.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class CinemaService implements ICinemaService{

    @Autowired
    private CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {this.cinemaRepository = cinemaRepository;}

    public List<Cinema> getCinemas() {return cinemaRepository.findAll();}

    public Cinema getCinema(long id) {return cinemaRepository.findById(id).get();}

    public Cinema updateCinema(long id, String name, String city, String address){
        if (cinemaRepository.existsById(id) && name != null && city != null && address != null) {
            Cinema cinema = cinemaRepository.findById(id).get();
            cinema.setName(name);
            cinema.setCity(city);
            cinema.setAddress(address);
            cinemaRepository.saveAndFlush(cinema);
            return cinema;
        } else {return null;}
    }

    public Cinema addCinema(String name, String city, String address) {
        Cinema cinema= new Cinema(name,city,address);
        cinemaRepository.saveAndFlush(cinema);
        return cinema;
    }


    public boolean deleteCinema(long id){
        if(cinemaRepository.existsById(id)) {
            cinemaRepository.deleteById(id);
            return true;
        } else {return false;}
    }

}
