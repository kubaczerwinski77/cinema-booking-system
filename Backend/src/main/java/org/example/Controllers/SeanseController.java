package org.example.Controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.Seanse;
import org.example.Services.SeanseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:6000")
@RestController
public class SeanseController {

    private final SeanseService seanseService;

    public SeanseController(SeanseService seanseService) {
        this.seanseService = seanseService;
    }

    @GetMapping(value = "/seanses")
    public ResponseEntity<List<Seanse>> getAllSeanses() {
        return new ResponseEntity<>(seanseService.getSeanses(), HttpStatus.OK);
    }

    @GetMapping(value = "/seanses/{id}")
    public ResponseEntity<Seanse> getSeanseById(@PathVariable("id") long id){
        return new ResponseEntity<>(seanseService.getSeanse(id), HttpStatus.OK);
    }

    @PostMapping(value = "/seanses")
    public ResponseEntity<Seanse> addSeanse(@RequestBody ObjectNode json){
        if (!json.has("date") || !json.has("movieId") || !json.has("cinemaHallId"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            return new ResponseEntity<>(seanseService.addSeanse(LocalDateTime.parse(json.get("date").asText()), json.get("movieId").asInt(), json.get("cinemaHallId").asInt()), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/seanses/{id}")
    public ResponseEntity<Seanse> updateSeanse(@PathVariable Long id, @RequestBody ObjectNode json){
        if (!json.has("date") || !json.has("movieId") || !json.has("cinemaHallId"))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            Seanse seanse = seanseService.updateSeanse(id, LocalDateTime.parse(json.get("date").asText()), json.get("movieId").asInt(), json.get("cinemaHallId").asInt());
            return new ResponseEntity<>(seanse, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/seanses/{id}")
    public ResponseEntity<Seanse> deleteSeanse(@PathVariable("id") Long id){
        try{
            seanseService.deleteSeanse(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "seanses/movie/{id}")
    public ResponseEntity<List<Seanse>> getSeanseOfMovie(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(seanseService.getSeanseOfMovie(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "seanses/day/{date}")
    public ResponseEntity<List<Seanse>> getDaysSeanse(@PathVariable("date") LocalDate date) {
        try {
            return new ResponseEntity<>(seanseService.getDaysSeanses(date), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
