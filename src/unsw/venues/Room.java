package unsw.venues;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import org.json.JSONArray;

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

    public JSONArray getJSONReservedList() {
        JSONArray resList = new JSONArray();
        this.sortList();
        for (Reservation r : this.getReservedList()) {
            resList.put(r.getJSONResDetails());
        }

        return resList;
    }

    public void addReservation(Reservation r) {
        this.reservedList.add(r);
    }

    public void removeLastReservation() {
        reservedList.remove(reservedList.size() - 1);
    }

    public int checkAvailRes(LocalDate start, LocalDate end, ReservationRequest resRequest, int counter, Reservation newRes) {
        for (Reservation resCheck : this.getReservedList()) {
            if (
                ((resCheck.getStartDate().compareTo(start) >= 0 && resCheck.getStartDate().compareTo(end) <= 0) || 
                (resCheck.getEndDate().compareTo(start) >= 0 && resCheck.getEndDate().compareTo(end) <= 0) ||
                (resCheck.getStartDate().compareTo(start) <= 0 && resCheck.getEndDate().compareTo(end) >= 0)) &&
                (resCheck.getID().equals(resRequest.getID()) == false)
            ) {
                return counter;
            }
        }
        counter--;
        return counter;
    }

    public void emptyOutRooms(String id) {
        this.reservedList.removeIf(a -> a.getID().equals(id) == true);
    }

    public JSONArray loadReservedList() {
        JSONArray resList = new JSONArray();
        for (Reservation res : this.reservedList) {
            resList.put(res.resInfo());
        }

        return resList;
    }

    public void sortList() {
        if (this.reservedList.size() > 1) {
            Collections.sort(this.reservedList, (a, b) -> a.getStartDate().compareTo(b.getStartDate()));
        }
    }

}