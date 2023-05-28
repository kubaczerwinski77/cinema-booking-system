package org.example.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.Cinema;
import org.example.Model.Seat;
import org.example.Model.SeatType;
import org.example.Services.SeatTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class SeatTypeController {
    private final SeatTypeService seatTypeService;
    public SeatTypeController(SeatTypeService seatTypeService) {
        this.seatTypeService = seatTypeService;
    }

    @GetMapping(value = "/seatTypes")
    public ResponseEntity<List<SeatType>> getAllSeatTypes() {
        return new ResponseEntity<>(seatTypeService.getSeatTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/seatTypes/{id}")
    public ResponseEntity<SeatType> getSeatTypeById(@PathVariable("id") int id){
        return new ResponseEntity<>(seatTypeService.getSeatType(id), HttpStatus.OK);
    }

    @PostMapping(value = "/seatTypes")
    public ResponseEntity<SeatType> addSeatType(@RequestBody ObjectNode json){
        if (!json.has("type"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(seatTypeService.addSeatType(json.get("type").asText(), json.get("price").asDouble()), HttpStatus.CREATED);

    }

    @DeleteMapping(value = "/seatTypes/{id}")
    public ResponseEntity<SeatType> deleteSeatType(@PathVariable("id") int id){
        try{
            seatTypeService.deleteSeatType(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


}
