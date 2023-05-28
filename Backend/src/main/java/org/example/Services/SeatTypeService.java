package org.example.Services;

import org.example.Repository.CinemaRepository;
import org.example.Repository.SeatRepository;
import org.example.Repository.SeatTypeRepository;
import org.example.Model.Cinema;
import org.example.Model.Seat;
import org.example.Model.SeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.Services.ISeatTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SeatTypeService implements ISeatTypeService {

    private SeatTypeRepository seatTypeRepository;
    private final SeatRepository seatRepository;

    public SeatTypeService(SeatTypeRepository seatTypeRepository,
                           SeatRepository seatRepository) {this.seatTypeRepository = seatTypeRepository;
        this.seatRepository = seatRepository;
    }

    public List<SeatType> getSeatTypes() {return seatTypeRepository.findAll();}

    public SeatType getSeatType(int id) {return seatTypeRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Seat type with id " + id + " does not exist"));}

    public SeatType addSeatType(String type, double price) {
        SeatType seatType= new SeatType(type, price);
        seatTypeRepository.saveAndFlush(seatType);
        return seatType;
    }


    public boolean deleteSeatType(int id){
        if(seatTypeRepository.existsById(id)) {
            seatTypeRepository.deleteById(id);
            return true;
        } else { throw new NoSuchElementException("Seat type with id " + id + " does not exist");}
    }
}
