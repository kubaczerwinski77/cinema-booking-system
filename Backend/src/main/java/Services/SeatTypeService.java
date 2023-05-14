package Services;

import Repository.CinemaRepository;
import Repository.SeatTypeRepository;
import org.example.Model.Cinema;
import org.example.Model.Seat;
import org.example.Model.SeatType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SeatTypeService implements ISeatTypeService{

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    public SeatTypeService(SeatTypeRepository seatTypeRepository) {this.seatTypeRepository = seatTypeRepository;}

    public List<SeatType> getSeatTypes() {return seatTypeRepository.findAll();}

    public SeatType getSeatType(int id) {return seatTypeRepository.findById(id).get();}

    public SeatType updateSeatType(int id, String type){
        if (seatTypeRepository.existsById(id) && type != null) {
            SeatType seatType = seatTypeRepository.findById(id).get();
            seatType.setType(type);
            seatTypeRepository.saveAndFlush(seatType);
            return seatType;
        } else {return null;}
    }

    public SeatType addSeatType(String type) {
        SeatType seatType= new SeatType(type);
        return seatType;
    }


    public boolean deleteSeatType(int id){
        if(seatTypeRepository.existsById(id)) {
            seatTypeRepository.deleteById(id);
            return true;
        } else {return false;}
    }
}
