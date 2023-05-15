package Services;

import org.example.Model.SeatType;

import java.util.List;

public interface ISeatTypeService {
    public List<SeatType> getSeatTypes();

    SeatType getSeatType(int id);
    SeatType addSeatType(String type);
    SeatType updateSeatType(int id, String type);
    boolean deleteSeatType(int id);
}
