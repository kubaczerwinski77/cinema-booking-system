package org.example.Services;

import org.example.Model.Seat;
import org.example.Model.SeatType;

import java.util.List;

public interface ISeatService {
    public List<Seat> getSeats(Long id);

    Seat getSeat(Long id);
    Seat updateSeat(Long id, int seatTypeId);

}
