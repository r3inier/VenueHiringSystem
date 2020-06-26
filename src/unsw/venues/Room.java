package unsw.venues;

import java.util.ArrayList;
import java.time.LocalDate;

public class Room {
    private String name;
    private String size;
    private ArrayList<Reservation> reservedList;

    /**
     * @param name Name of room
     * @param size Size of room (small, medium or large)
     * @param reservedList List of rooms that have already been reserved
     */
    public Room(String name, String size) {
        this.name = name;
        this.size = size;
        this.reservedList = new ArrayList<>();
    }

    public String getRoomName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public ArrayList<Reservation> getReservedList() {
        return reservedList;
    }

    public void addReservation(Reservation r) {
        this.reservedList.add(r);
    }

    public void removeLastReservation() {
        reservedList.remove(reservedList.size() - 1);
    }

    public ArrayList<Reservation> checkAvailRes(LocalDate start, LocalDate end, ReservationRequest resRequest, ArrayList<Reservation> bufferReservationList, Reservation newRes) {
        for (Reservation resCheck : this.getReservedList()) {
            if (
                (resCheck.getStartDate().compareTo(start) < 0 && resCheck.getEndDate().compareTo(start) < 0) || 
                (resCheck.getStartDate().compareTo(end) > 0 && resCheck.getEndDate().compareTo(end) > 0)
            ) {
                bufferReservationList.add(newRes);
                this.reservedList.add(newRes);
                return bufferReservationList;
            }
        }
        return bufferReservationList;
    }

    public void emptyOutRooms(String id) {
        for (Reservation r : this.reservedList) {
            if (r.getRoomName().equals(id)) {
                this.reservedList.remove(r);
            }
        }
    }


}