package org.example.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReservedSeats {

    @EmbeddedId
    ReservedSeatsKey id;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "seanse_id", insertable=false, updatable=false)
    private Seanse seanse;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "booking_id", insertable=false, updatable=false)
    private Booking booking;


    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "seat_id", insertable=false, updatable=false)
    private Seat seat;

    public ReservedSeats(Seanse seanse, Booking booking, Seat seat)
    {
        this.seanse = seanse;
        this.booking = booking;
        this.seat = seat;
    }

    public ReservedSeats() {

    }

    public Booking getBooking() {
        return booking;
    }

    public Seanse getSeanse() {
        return seanse;
    }

    public Seat getSeat() {
        return seat;
    }
}
