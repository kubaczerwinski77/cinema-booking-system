package org.example.Controllers;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Services.CinemaHallService;
import org.example.Services.CinemaService;
import org.example.Services.ReservedSeatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.Services.ReservedSeatsService;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class ReservedSeatesController {

    private final ReservedSeatsService reservedSeatsService;


    public ReservedSeatesController(ReservedSeatsService reservedSeatsService) {
        this.reservedSeatsService = reservedSeatsService;
    }

    @GetMapping(value = "/reservedSeates")
    public ResponseEntity<List<ReservedSeats>> getAllReservedSeates() {
        return new ResponseEntity<>(reservedSeatsService.getReservedSeates(), HttpStatus.OK);
    }

    @GetMapping(value = "/reservedSeates/{id}")
    public ResponseEntity<List<ReservedSeats>> getAllReservedSeatesInSeanse(long id) {
            return new ResponseEntity<>(reservedSeatsService.getReservedSeatInSeanse(id), HttpStatus.OK);

    }
}
