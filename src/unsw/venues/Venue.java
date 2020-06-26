package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;



public class Venue {
    private String name;
    private int small;
    private int medium;
    private int large;
    private ArrayList<Room> roomList;

    /**
     * @param name     Name of venue
     * @param roomList List of rooms inside the venue
     */
    public Venue(String name) {
        this.name = name;
        this.roomList = new ArrayList<>();
        this.small = 0;
        this.medium = 0;
        this.large = 0;
    }

    public String getVenueName() {
        return name;
    }
    
    public int getSmallMax() {
        return small;
    }

    public int getMediumMax() {
        return medium;
    }

    public int getLargeMax() {
        return large;
    }


    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    // This function is accessed by the room command in
    // VenueHireSystem.java and adds the parsed room into venue
    public void addRoomList(Room r) {
        if (r.getSize().equals("small") == true) {
            this.small++;
        }
        if (r.getSize().equals("medium") == true) {
            this.medium++;
        }
        if (r.getSize().equals("large") == true) {
            this.large++;
        }
        roomList.add(r);
    }


    public ArrayList<Reservation> checkVenue(LocalDate start, LocalDate end, int small, int medium, int large, ReservationRequest resRequest) {

        int tempSmall = small;
        int tempMed = medium;
        int tempLarge = large;
        ArrayList<Reservation> bufferReservationList = new ArrayList<>();

        for (Room roomCheck : this.getRoomList()) {
            Reservation newRes = new Reservation(resRequest, roomCheck);
            // Small
            if (roomCheck.getSize().equals("small") && tempSmall > 0) {
                if (roomCheck.getReservedList().isEmpty() == true) {
                    bufferReservationList.add(newRes);
                    tempSmall--;
                } else {
                    bufferReservationList = roomCheck.checkAvailRes(start, end, resRequest, bufferReservationList, newRes);
                }
            } /* Medium */else if (roomCheck.getSize().equals("medium") && tempMed > 0) {
                if (roomCheck.getReservedList().isEmpty() == true) {
                    bufferReservationList.add(newRes);
                    tempMed--;
                } else {
                    bufferReservationList = roomCheck.checkAvailRes(start, end, resRequest, bufferReservationList, newRes);
                }
            } /* Large */ else if (roomCheck.getSize().equals("large") && tempLarge > 0) {
                if (roomCheck.getReservedList().isEmpty() == true) {
                    bufferReservationList.add(newRes);
                    tempLarge--;
                } else {
                    bufferReservationList = roomCheck.checkAvailRes(start, end, resRequest, bufferReservationList, newRes);
                }   
            } 
            
            // Check if all rooms assigned 
            if (tempSmall == 0 && tempMed == 0 && tempLarge == 0) {
                return bufferReservationList;
            }  
            
        }

        emptyOutVenue(resRequest.getID());

        return new ArrayList<>();
    }

    public void emptyOutVenue(String id) {
        for (Room r :this.roomList) {
            r.emptyOutRooms(id);
        }
    }
    
    

}