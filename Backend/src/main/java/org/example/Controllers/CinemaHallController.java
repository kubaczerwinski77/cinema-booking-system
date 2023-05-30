package org.example.Controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.Model.CinemaHall;
import org.example.Services.CinemaHallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    public CinemaHallController(CinemaHallService cinemaHall) {
        this.cinemaHallService= cinemaHall;
    }

    @GetMapping(value = "/cinemaHalls")
    public ResponseEntity<List<CinemaHall>> getAllCinemaHalls() {
        return new ResponseEntity<>(cinemaHallService.getCinemaHalls(), HttpStatus.OK);
    }

    @GetMapping(value = "/cinemaHalls/{id}")
    public ResponseEntity<CinemaHall> getCinemaHallById(@PathVariable("id") long id){
        return new ResponseEntity<>(cinemaHallService.getCinemaHall(id), HttpStatus.OK);
    }

    @PostMapping(value = "/cinemaHalls")
    public ResponseEntity<CinemaHall> addCinemaHall(@RequestBody ObjectNode json){
        if (
                !json.has("name") ||
                        !json.has("totalSize") ||
                        !json.has("cinemaId")
        )
            throw new IllegalArgumentException("Wrong values");

       // try {
            return new ResponseEntity<>( cinemaHallService.addCinemaHall(
                    json.get("name").asText(),
                    json.get("totalSize").asInt(),
                    json.get("cinemaId").asLong()),
                    HttpStatus.OK);
     //   } catch (NotFoundException e) {
      //      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
     //   }
    }

    @PutMapping(value = "/cinemaHalls/{id}")
    public ResponseEntity<CinemaHall> updateCinemaHall(@PathVariable Long id, @RequestBody ObjectNode json){
        if (
                !json.has("name") ||
                        !json.has("totalSize")
        )
            throw new IllegalArgumentException("Wrong values");
      //  try {
            CinemaHall cinemaHall = cinemaHallService.updateCinemaHall(
                    id,
                    json.get("name").asText(),
                    json.get("totalSize").asInt()
            );
            return new ResponseEntity<>(cinemaHall, HttpStatus.OK);
    //    } catch (NotFoundException e) {
    //        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
     //   }
    }


    @DeleteMapping(value = "/cinemaHalls/{id}")
    public ResponseEntity<CinemaHall> deleteRoom(@PathVariable("id") Long id){
            cinemaHallService.deleteCinemaHall(id);
            return new ResponseEntity<>(null,HttpStatus.OK);

    }
}
