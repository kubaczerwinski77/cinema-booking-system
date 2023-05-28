package org.example.Services;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;
import org.example.Model.Seat;

import java.util.List;

public interface IReservedSeats {
    public List<ReservedSeats> getReservedSeates();

    List<Seat> getReservedSeatInSeanse(long id);
}
