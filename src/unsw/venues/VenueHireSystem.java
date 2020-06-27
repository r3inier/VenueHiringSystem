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
            String id2 = json.getString("id");
            LocalDate start2 = LocalDate.parse(json.getString("start"));
            LocalDate end2 = LocalDate.parse(json.getString("end"));
            int small2 = json.getInt("small");
            int medium2 = json.getInt("medium");
            int large2 = json.getInt("large");
            JSONObject result2 = change(id2, start2, end2, small2, medium2, large2);
            // 1. loop through each venue then check room's
            // reservedList and check each reservation with
            // same id then delete that from reservedList
            // (have a counter for s, m, l depending on prev
            // reservation and when count is met, stop searching)
            // 2. then use request command to add new reservation
            // (if possible)
            // 3. do System.out.println(blahblah)
            System.out.println(result2.toString(2));
            break;
        case "cancel":
            String id3 = json.getString("id");
            cancel(id3);

            // 1. same as step 1 in "change" command
            // 2. then step 3 of "change" command
            break;
        case "list":
            String venue2 = json.getString("venue");
            
            // loop through venue's roomList and output
            // reservedList for each and an empty ArrayList
            Venue venueFound = findVenue(venue2);
            venueFound.loadRooms();
            break;
        }
    }

    private void addRoom(String venue, String room, String size) {
        // 1. Create a room in room class with size given
        // 2. Check if list of venues is empty
        // 3. If not empty, find venue in list (if not  in list then create new venue)
        // 4. Go into venue object then append room to roomList
        Room r = new Room(room, size);

        int counter = 0;
        // empty venueList case
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
            
            // Checks if all rooms were successfully assigned
            if (bufferReservationList.isEmpty() == false) {
                result.put("venue", vCheck.getVenueName());
                result.put("status", "success");
                JSONArray rooms = new JSONArray();
                // Adds rooms into the JSONArray
                for (Reservation rsve: bufferReservationList) {
                    rooms.put(rsve.getRoomName());
                }
                // Adds the reservations in respective rooms
                for (Reservation r: bufferReservationList) {
                    System.out.println(r.getID());
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

    public void cancel(String id) {
        for (Venue v : this.venueList) {
            v.emptyOutVenue(id);
        }
    }

    public JSONObject change(String id, LocalDate start, LocalDate end,
    int small, int medium, int large) {
        
        cancel(id);
        JSONObject result = request(id, start, end, small, medium, large);

        return result;
    }

    public Venue findVenue(String venueName) {
        Venue venResult = new Venue("lol");
        for (Venue v: this.venueList) {
            if (v.getVenueName().equals(venueName)) {
                venResult = v;
                break;
            }
        }
        return venResult; 
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

