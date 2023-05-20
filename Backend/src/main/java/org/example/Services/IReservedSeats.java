package org.example.Services;

import org.example.Model.ReservedSeats;
import org.example.Model.Seanse;

import java.util.List;

public interface IReservedSeats {
    public List<ReservedSeats> getReservedSeates();

    List<ReservedSeats> getReservedSeatInSeanse(long id);
}
