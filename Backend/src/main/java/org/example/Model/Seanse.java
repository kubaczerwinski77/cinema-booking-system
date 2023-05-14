package org.example.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seanse")
public class Seanse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime seanseDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_hall_id", nullable = false)
    private CinemaHall cinemaHall;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservedSeats> reservedSeats = new ArrayList<>();

    public Seanse(LocalDateTime seanseDate, CinemaHall cinemaHall)
    {
        this.seanseDate = seanseDate;
        this.cinemaHall = cinemaHall;
    }

    public Seanse() {

    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return seanseDate;
    }

    public void setDate(LocalDateTime date) {
        this.seanseDate = date;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public List<ReservedSeats> getReservedSeats() {
        return reservedSeats;
    }
}
