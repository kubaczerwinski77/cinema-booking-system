package org.example.Services;

import org.example.Repository.CinemaHallRepository;
import org.example.Repository.CinemaRepository;
import org.example.Model.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.example.Services.ICinemaService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CinemaService implements ICinemaService{


    private CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {this.cinemaRepository = cinemaRepository;}

    public List<Cinema> getCinemas() {return cinemaRepository.findAll();}

    public Cinema getCinema(long id) {return cinemaRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Cinema with id " + id + " does not exist"));}

    public Cinema updateCinema(long id, String name, String city, String address){
        if (cinemaRepository.existsById(id) && name != null && city != null && address != null) {
            Cinema cinema = cinemaRepository.findById(id).get();
            cinema.setName(name);
            cinema.setCity(city);
            cinema.setAddress(address);
            cinemaRepository.saveAndFlush(cinema);
            return cinema;
        }
        {throw new NoSuchElementException("Cinema with id " + id + " does not exist");
        }
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
