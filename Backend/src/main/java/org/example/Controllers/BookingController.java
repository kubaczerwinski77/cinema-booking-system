package org.example.Controllers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Exceptions.AlreadyExistException;
import org.example.Model.Booking;
import org.example.Services.BookingService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
    }

    @GetMapping(value = "/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id){
        return new ResponseEntity<>(bookingService.getBooking(id), HttpStatus.OK);
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity addBooking(@RequestBody ObjectNode json){
        if (!json.has("email") || !json.has("name") || !json.has("lastName") || !json.has("seatId")
                || !json.has("seanseId") || !json.has("date")
        )
            return  ResponseEntity.badRequest().body("Wrong values");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.convertValue(json.get("seatId"), JsonNode.class);
            List<Long> seatsId = objectMapper.convertValue(jsonNode, new TypeReference<>() {
            });
            return new ResponseEntity<>(bookingService.addBooking(
                    json.get("email").asText(), json.get("name").asText(), json.get("lastName").asText(),
                    LocalDateTime.parse(json.get("date").asText()), json.get("seanseId").asLong(),
                    seatsId), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found seats or seanse");
        }
        catch(AlreadyExistException e1)
        {
            return ResponseEntity.badRequest().body("This booking already exists");
        }
    }


    @DeleteMapping(value = "/bookings/{id}")
    public ResponseEntity deleteBooking(@PathVariable("id") Long id){
        try{
            bookingService.deleteBooking(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This booking doesn't exist");
        }
    }

}
