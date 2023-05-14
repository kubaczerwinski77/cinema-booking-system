package org.example.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ReservedSeatsKey implements Serializable {

        @Column(name = "seanse_id")
        Long seanseId;

        @Column(name = "booking_id")
        Long bookingId;

        @Column(name = "seat_id")
        Long seatId;


}
