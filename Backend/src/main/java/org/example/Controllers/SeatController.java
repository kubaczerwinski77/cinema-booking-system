package org.example.Controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.CinemaHall;
import org.example.Model.Seat;
import org.example.Repository.SeatRepository;
import org.example.Services.CinemaHallService;
import org.example.Services.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class SeatController {

    private final SeatService seatService;


    public SeatController(SeatService seatService) {
        this.seatService= seatService;

    }

    @GetMapping(value = "/seatsInHall/{id}")
    public ResponseEntity<List<Seat>> getSeatsInHall(@PathVariable("id")Long id) {
        return new ResponseEntity<>(seatService.getSeats(id), HttpStatus.OK);
    }

    @GetMapping(value = "/seat/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable("id") long id){
        return new ResponseEntity<>(seatService.getSeat(id), HttpStatus.OK);
    }

    @PutMapping(value = "/seat/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody ObjectNode json){
        if (!json.has("seatTypeId"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            Seat seat = seatService.updateSeat(id, json.get("seatTypeId").asInt());
            return new ResponseEntity<>(seat, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
