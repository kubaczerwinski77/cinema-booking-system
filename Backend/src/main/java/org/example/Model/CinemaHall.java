package org.example.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSize;

    @JsonIgnore
    @JoinColumn(name = "cinema_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cinema cinema;

  //  @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cinema_hall_id")
    private List<Seat> seats = new ArrayList<>();


   /* @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cinema_hall_id")
    private List<Seanse> seanse;*/

    public CinemaHall(String name, int totalSize)
    {
        this.name = name;
        this.totalSize = totalSize;
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

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinemaId) {
        this.cinema = cinemaId;
    }

 /*   public List<Seanse> getSeanse() {
        return seanse;
    }

    public List<Seat> getSeats() {
        return seats;
    }*/
}
