package org.example.Services;

import org.example.Repository.CinemaHallRepository;
import org.example.Repository.CinemaRepository;
import org.example.Model.Cinema;
import org.example.Model.CinemaHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.example.Services.ICinemaHallService;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaHallService implements ICinemaHallService {

    private CinemaHallRepository cinemaHallRepository;
    private  CinemaRepository cinemaRepository;

    public CinemaHallService(CinemaHallRepository cinemaHallRepository, CinemaRepository cinemaRepository)
    {this.cinemaHallRepository = cinemaHallRepository;
    this.cinemaRepository = cinemaRepository;}

    public List<CinemaHall> getCinemaHalls() {return cinemaHallRepository.findAll();}

    public CinemaHall getCinemaHall(long id) {return cinemaHallRepository.findById(id).get();}

    public CinemaHall updateCinemaHall(long id,String name, int totalSize){
        if (cinemaHallRepository.existsById(id) && name != null && totalSize > 0) {
            CinemaHall cinemaHall = cinemaHallRepository.findById(id).get();
            cinemaHall.setName(name);
            cinemaHall.setTotalSize(totalSize);
            cinemaHallRepository.saveAndFlush(cinemaHall);
            return cinemaHall;
        } else {return null;}
    }

    public CinemaHall addCinemaHall(String name, int totalSize, long cinemaId) {
        CinemaHall cinemaHall= new CinemaHall();
        cinemaHall.setName(name);
        cinemaHall.setTotalSize(totalSize);
        if (cinemaRepository.findById(cinemaId).isPresent()) {
            cinemaHall.setCinema(cinemaRepository.findById(cinemaId).get());
            return cinemaHallRepository.save(cinemaHall);
        } else {throw new NotFoundException("Cinema doesn't exist");
        }
       // return cinemaHall;
    }

    public boolean hallsInCinema(long id) {

        if (cinemaRepository.findById(id) != null) {
            Cinema cinema = cinemaRepository.getReferenceById(id);
            for (CinemaHall hall : cinemaHallRepository.findAll()) {
                if (hall.getCinema().getId() == id)
                    return true;
            }
        }
        return false;
    }
    public boolean deleteCinemaHall(long id){
        if(cinemaHallRepository.existsById(id)) {
            cinemaHallRepository.deleteById(id);
            return true;
        } else {return false;}
    }
}
