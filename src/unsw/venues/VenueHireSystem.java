/**
 *
 */
package unsw.venues;

import java.time.LocalDate;
import java.util.Scanner;

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
    public VenueHireSystem() {
        // TODO Auto-generated constructor stub
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
            String id3 = json.getString("id");
            // 1. same as step 1 in "change" command
            // 2. then step 3 of "change" command
            break;
        case "list":
            String venue2 = json.getString("venue");
            // loop through venue's roomList and output
            // reservedList for each and an empty ArrayList
            // if nothing in it
        }
    }

    private void addRoom(String venue, String room, String size) {
        // TODO Process the room command
        // 1. create a room in room class with size given
        // 2. create a venue in venue class
        // 2. go into venue object then append room to roomList
    }

    public JSONObject request(String id, LocalDate start, LocalDate end,
            int small, int medium, int large) {
        JSONObject result = new JSONObject();

        // TODO Process the request commmand
        // go into venue then into room reservedList
        // then compare dates and
        // sizes of every room and see if there is space
        // if there is result.put success with venue etc
        // but status reject if found clashing times

        // FIXME Shouldn't always produce the same answer
        result.put("status", "success");
        result.put("venue", "Zoo");

        JSONArray rooms = new JSONArray();
        rooms.put("Penguin");
        rooms.put("Hippo");

        result.put("rooms", rooms);
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
