package org.example.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String email;
    private String name;
    private String lastName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<ReservedSeats> reservedSeats = new ArrayList<>();

    public Booking(String email, String name, String lastName)
    {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }


    public Booking() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ReservedSeats> getReservedSeats() {
        return reservedSeats;
    }
}
