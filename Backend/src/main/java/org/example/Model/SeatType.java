package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    String type;

    @Column(nullable = false)
    private double price;

    @JsonIgnore
    @OneToMany()
    @JoinColumn(name = "seat_type_id")
    private List<Seat> seats = new ArrayList<>();

    public SeatType() {
    }

    public SeatType(String type, double price) {

        this.type = type;
        this.price = price;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
