package unsw.venues;

import java.time.LocalDate;

// This class is used for successful reservations
public class Reservation {
    private String id;
    private Room room;
    private LocalDate start;
    private LocalDate end;

    public Reservation(ReservationRequest resReq, Room room) {
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

}