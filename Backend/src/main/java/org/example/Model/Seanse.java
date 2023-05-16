package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @JoinColumn(name = "cinema_hall_id")
    @ManyToOne
    private CinemaHall cinemaHall;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservedSeats> reservedSeats = new ArrayList<>();

    public Seanse(LocalDateTime seanseDate)
    {
        this.seanseDate = seanseDate;
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
