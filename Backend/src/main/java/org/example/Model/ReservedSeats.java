package org.example.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@IdClass(ReservedSeats.class)
public class ReservedSeats implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "seanse_id", insertable=false, updatable=false)
    private Seanse seanse;

    @ManyToOne
    @JoinColumn(name = "booking_id", insertable=false, updatable=false)
    private Booking booking;

    @Id
    @ManyToOne
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

 /*   public Seanse getSeanse() {
        return seanse;
    }*/

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setSeanse(Seanse seanse) {
        this.seanse = seanse;
    }

    public Seanse getSeanse() {
        return seanse;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
