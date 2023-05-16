package org.example.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Seat{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private int rowInHall;
    @Column(nullable = false)
    private int columnInHall;

    @Column(nullable = false)
    private double price;

    @JsonIgnore
    @JoinColumn(name = "seat_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SeatType seatType;

    @JsonIgnore
    @JoinColumn(name = "cinema_hall_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CinemaHall cinemaHall;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservedSeats> reservedSeats = new ArrayList<>();


    public Seat() {
    }

    public Seat(int rowInHall, int columnInHall, float price, SeatType seatType, CinemaHall cinemaHall) {
        this.rowInHall = rowInHall;
        this.columnInHall = columnInHall;
        this.price = price;
        this.seatType = seatType;
        this.cinemaHall = cinemaHall;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getRowInHall() {
        return rowInHall;
    }

    public void setRowInHall(int row) {
        this.rowInHall = row;
    }

    public int getColumnInHall() {
        return columnInHall;
    }

    public void setColumnInHall(int column) {
        this.columnInHall = column;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }



    public List<ReservedSeats> getReservedSeats() {
        return reservedSeats;
    }
}
