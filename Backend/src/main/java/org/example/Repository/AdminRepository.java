package org.example.Repository;

import org.example.Model.Admin;
import org.example.Model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query("select a from Admin a where a.email = ?1")
    Optional<Admin> findByEmail(String email);

}
