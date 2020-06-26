/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Venue Hire System for COMP2511.
 *
 * A basic prototype to serve as the "back-end" of a venue hire system. Input
 * and output is in JSON format.
 *
 * @author Robert Clifton-Everest
 *
 */
public class VenueHireSystem {
    /**
     * Constructs a venue hire system. Initially, the system contains no venues,
     * rooms, or bookings.
     */
    private ArrayList<Venue> venueList;

    
    public VenueHireSystem() {
        this.venueList = new ArrayList<>();
    }

    public ArrayList<Venue> getVenueList() {
        return venueList;
    }

    public void addRoomReservation(Room room, Reservation res) {
        room.addReservation(res);
    }

    private void processCommand(JSONObject json) {
        switch (json.getString("command")) {

        case "room":
            String venue = json.getString("venue");
            String room = json.getString("room");
            String size = json.getString("size");
            addRoom(venue, room, size);
            break;

        case "request":
            String id = json.getString("id");
            LocalDate start = LocalDate.parse(json.getString("start"));
            LocalDate end = LocalDate.parse(json.getString("end"));
            int small = json.getInt("small");
            int medium = json.getInt("medium");
            int large = json.getInt("large");

            JSONObject result = request(id, start, end, small, medium, large);

            System.out.println(result.toString(2));
            break;

        case "change":
            /* String id2 = json.getString("id");
            LocalDate start2 = LocalDate.parse(json.getString("start"));
            LocalDate end2 = LocalDate.parse(json.getString("end"));
            int small2 = json.getInt("small");
            int medium2 = json.getInt("medium");
            int large2 = json.getInt("large"); */
        
            // 1. loop through each venue then check room's
            // reservedList and check each reservation with
            // same id then delete that from reservedList
            // (have a counter for s, m, l depending on prev
            // reservation and when count is met, stop searching)
            // 2. then use request command to add new reservation
            // (if possible)
            // 3. do System.out.println(blahblah)
            break;
        case "cancel":
            // String id3 = json.getString("id");

            // 1. same as step 1 in "change" command
            // 2. then step 3 of "change" command
            break;
        case "list":
            // String venue2 = json.getString("venue");
            
            // loop through venue's roomList and output
            // reservedList for each and an empty ArrayList
            // if nothing in it
        }
    }

    private void addRoom(String venue, String room, String size) {
        // TODO Process the room command
        // 1. create a room in room class with size given
        // 2. find venue in list (if not then create new venue)
        // 2. go into venue object then append room to roomList
        Room r = new Room(room, size);

        int counter = 0;

        if (this.venueList.isEmpty() == true) {
            Venue newV = new Venue(venue);
            this.venueList.add(newV);
            newV.addRoomList(r);
        } else {
            for (Venue vCheck : this.venueList) {
                if (vCheck.getVenueName().equals(venue) == true) {
                    vCheck.addRoomList(r);
                    break;
                } else if (counter == this.venueList.size() - 1) {
                    if (vCheck.getVenueName().equals(venue) == false) {
                        Venue newVenue = new Venue(venue);
                        this.venueList.add(newVenue);
                        newVenue.addRoomList(r);
                        break;
                    }
                }
                counter++;
            }
        }
        
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        JSONObject result = new JSONObject();
        ReservationRequest resRequest = new ReservationRequest(id, start, end, small, medium, large);

        for (Venue vCheck : this.venueList) {
            ArrayList<Reservation> bufferReservationList = new ArrayList<>();

            if (vCheck.getSmallMax() >= small && vCheck.getMediumMax() >= medium && vCheck.getLargeMax() >= large) {
                // Checks every room in the venue to see if we can fit all rooms inside
                bufferReservationList = vCheck.checkVenue(start, end, small, medium, large, resRequest);
            }
            
            // checks if all rooms were successfully assigned
            // CASE OF IF SUCCESSFUL (change up)
            if (bufferReservationList.isEmpty() == false) {
                result.put("venue", vCheck.getVenueName());
                result.put("status", "success");
                JSONArray rooms = new JSONArray();
                for (Reservation rsve: bufferReservationList) {
                    rooms.put(rsve.getRoomName());
                }
                for (Reservation r: bufferReservationList) {
                    r.getRoom().addReservation(r);
                }
                result.put("rooms", rooms);
                return result;
            }
        }

        // if it reaches here, it is rejected
        result.put("status", "rejected");
        return result;
    }

    public static void main(String[] args) {
        VenueHireSystem system = new VenueHireSystem();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.trim().equals("")) {
                JSONObject command = new JSONObject(line);
                system.processCommand(command);
            }
        }
        sc.close();
    }

}

