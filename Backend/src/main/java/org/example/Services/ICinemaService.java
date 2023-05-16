package org.example.Services;

import org.example.Model.Cinema;

import java.util.List;

public interface ICinemaService {

        public List<Cinema> getCinemas();

        Cinema getCinema(long id);
        Cinema addCinema(String name, String city, String address);
        Cinema updateCinema(long id, String name, String city, String address);
        boolean deleteCinema(long id);

}
