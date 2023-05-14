package org.example.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    String type;

    @OneToMany()
    @JoinColumn(name = "seat_type_id")
    private List<Seat> seats = new ArrayList<>();

    public SeatType() {
    }

    public SeatType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
