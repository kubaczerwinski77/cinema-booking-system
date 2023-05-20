package org.example.Repository;

import org.example.Model.Booking;
import org.example.Model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
}

