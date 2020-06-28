package unsw.venues;

import java.time.LocalDate;
import org.json.JSONObject;

// This class is used for successful reservations
public class Reservation {
    private final String id;
    private final Room room;
    private final LocalDate start;
    private final LocalDate end;

    public Reservation(final ReservationRequest resReq, final Room room) {
        this.id = resReq.getID();
        this.room = room;
        this.start = resReq.getStartDate();
        this.end = resReq.getEndDate();
    }

    public String getID() {
        return id;
    }

    public String getRoomName() {
        return this.getRoom().getRoomName();
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return start;
    }

    public LocalDate getEndDate() {
        return end;
    }

    public JSONObject resInfo() {
        final JSONObject result = new JSONObject();
        result.put("start",this.getStartDate().toString());
        result.put("end", this.getEndDate().toString());
        result.put("id", this.getID());
        return result;
    }

    public JSONObject getJSONResDetails() {
        JSONObject result = new JSONObject();
        result.put("start", this.getStartDate());
        result.put("end", this.getEndDate());
        result.put("id", this.getID());
        return result;
    }


}