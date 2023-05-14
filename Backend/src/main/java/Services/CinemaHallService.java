package Services;

import Repository.CinemaHallRepository;
import Repository.CinemaRepository;
import org.example.Model.Cinema;
import org.example.Model.CinemaHall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaHallService implements ICinemaHallService{
    @Autowired
    private CinemaHallRepository cinemaHallRepository;
    private  CinemaRepository cinemaRepository;

    public CinemaHallService(CinemaHallRepository cinemaHallRepository, CinemaRepository cinemaRepository)
    {this.cinemaHallRepository = cinemaHallRepository;
    this.cinemaRepository = cinemaRepository;}

    public List<CinemaHall> getCinemaHalls() {return cinemaHallRepository.findAll();}

    public CinemaHall getCinemaHall(long id) {return cinemaHallRepository.findById(id).get();}

    public CinemaHall updateCinemaHall(long id,String name, int totalSize, int length, int width){
        if (cinemaHallRepository.existsById(id) && name != null && totalSize > 0 && length > 0 && width >0) {
            CinemaHall cinemaHall = cinemaHallRepository.findById(id).get();
            cinemaHall.setName(name);
            cinemaHall.setTotalSize(totalSize);
            cinemaHall.setLength(length);
            cinemaHall.setWidth(width);
            cinemaHallRepository.saveAndFlush(cinemaHall);
            return cinemaHall;
        } else {return null;}
    }

    public CinemaHall addCinemaHall(String name, int totalSize, int length, int width, long cinemaId) {
        CinemaHall cinemaHall= new CinemaHall(name,totalSize,length,width);
        if (cinemaRepository.findById(cinemaId) != null) {
            cinemaHall.setCinema(cinemaRepository.findById(cinemaId).get());
        } else {throw new NotFoundException("Cinema doesn't exist");
        }
        return cinemaHall;
    }


    public boolean deleteCinemaHall(long id){
        if(cinemaHallRepository.existsById(id)) {
            cinemaHallRepository.deleteById(id);
            return true;
        } else {return false;}
    }
}
