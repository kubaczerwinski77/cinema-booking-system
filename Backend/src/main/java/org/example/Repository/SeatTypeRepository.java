package org.example.Repository;

import org.example.Model.Cinema;
import org.example.Model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {
}
