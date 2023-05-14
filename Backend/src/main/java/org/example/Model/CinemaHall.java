package org.example.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSize;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int width;

    @JoinColumn(name = "cinema_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cinema cinema;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cinema_hall_id")
    private List<Seat> seats = new ArrayList<>();

    @JoinColumn(name = "cinema_hall_id")
    @OneToMany
    private List<Seanse> seanse;

    public CinemaHall(String name, int totalSize, int length, int width)
    {
        this.name = name;
        this.totalSize = totalSize;
        this.length = length;
        this.width = width;
    }

    public CinemaHall() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinemaId) {
        this.cinema = cinemaId;
    }

    public List<Seanse> getSeanse() {
        return seanse;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
