package org.example.Repository;

import org.example.Model.Admin;
import org.example.Model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
