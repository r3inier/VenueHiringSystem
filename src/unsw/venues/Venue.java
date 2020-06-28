package unsw.venues;

import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;



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
                    int comparison = tempSmall;
                    tempSmall = roomCheck.checkAvailRes(start, end, resRequest, tempSmall, newRes);
                    if (tempSmall != comparison) {
                        bufferReservationList.add(newRes);
                    }
                }
            } /* Medium */else if (roomCheck.getSize().equals("medium") && tempMed > 0) {
                if (roomCheck.getReservedList().isEmpty() == true) {
                    bufferReservationList.add(newRes);
                    tempMed--;
                } else {
                    int comparison1 = tempMed;
                    tempMed = roomCheck.checkAvailRes(start, end, resRequest, tempMed, newRes);
                    if (tempMed != comparison1) {
                        bufferReservationList.add(newRes);
                    }
                }
            } /* Large */ else if (roomCheck.getSize().equals("large") && tempLarge > 0) {
                if (roomCheck.getReservedList().isEmpty() == true) {
                    bufferReservationList.add(newRes);
                    tempLarge--;
                } else {
                    int comparison = tempLarge;
                    tempLarge = roomCheck.checkAvailRes(start, end, resRequest, tempLarge, newRes);
                    if (tempLarge != comparison) {
                        bufferReservationList.add(newRes);
                    }
                }   
            } 
            
            // Check if all rooms assigned 
            if (tempSmall == 0 && tempMed == 0 && tempLarge == 0) {
                return bufferReservationList;
            }  
            
        }

        return new ArrayList<>();
    }

    public void emptyOutVenue(String id) {
        for (Room r :this.roomList) {
            r.emptyOutRooms(id);
        }
    }

    public void loadRooms() {
        JSONArray result = new JSONArray();
        for (Room r : this.getRoomList()) {
            JSONObject roomObj = new JSONObject();
            roomObj.put("reservations", r.getJSONReservedList());
            roomObj.put("room", r.getRoomName());
            result.put(roomObj);
        }
        System.out.println(result.toString(2));
    }
    
    

}