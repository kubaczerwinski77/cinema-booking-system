package org.example.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.Cinema;
import org.example.Services.CinemaHallService;
import org.example.Services.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaHallService cinemaHallService;

    public CinemaController(CinemaService cinemaService, CinemaHallService cinemaHallService) {
        this.cinemaService = cinemaService;
        this.cinemaHallService = cinemaHallService;
    }

    @GetMapping(value = "/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        return new ResponseEntity<>(cinemaService.getCinemas(), HttpStatus.OK);
    }

    @GetMapping(value = "/cinemas/{id}")
    public ResponseEntity<Cinema> getCinemaById(@PathVariable("id") long id){
        return new ResponseEntity<>(cinemaService.getCinema(id), HttpStatus.OK);
    }

    @PostMapping(value = "/cinemas")
    public ResponseEntity<Cinema> addCinema(@RequestBody ObjectNode json){
        if (!json.has("name") || !json.has("city") || !json.has("address"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cinemaService.addCinema(json.get("name").asText(),json.get("city").asText(),json.get("address").asText()), HttpStatus.CREATED);

    }

    @PutMapping(value = "/cinemas/{id}")
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody ObjectNode json){
        if (!json.has("name") || !json.has("city"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            Cinema cinema = cinemaService.updateCinema(id, json.get("name").asText(), json.get("address").asText(), json.get("city").asText());
            return new ResponseEntity<>(cinema, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/cinemas/{id}")
    public ResponseEntity<Cinema> deleteCinema(@PathVariable("id") Long id){
        if(cinemaHallService.hallsInCinema(id)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        try{
            cinemaService.deleteCinema(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
