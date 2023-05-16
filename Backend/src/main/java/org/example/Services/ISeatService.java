package org.example.Services;

import org.example.Model.Seat;
import org.example.Model.SeatType;

import java.util.List;

public interface ISeatService {
    public List<Seat> getSeat();

    Seat getSeat(int id);
    Seat addSeat(String type);
    Seat updateSeat(int id, String type);
    boolean deleteSeat(int id);
}
