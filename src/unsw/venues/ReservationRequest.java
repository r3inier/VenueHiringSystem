package unsw.venues;

import java.time.LocalDate;

public class ReservationRequest {
    private String id;
    private LocalDate start;
    private LocalDate end;
    private int small;
    private int medium;
    private int large;

    /**
     * 
     * @param id ID name for reservation
     * @param start The start date of reservation request
     * @param end The end date of reservation request
     * @param small Amount of small rooms required for reservation
     * @param medium Amount of medium rooms required for reservation
     * @param large Amount of large rooms required for reservation
     */
    public ReservationRequest(String id, LocalDate start, LocalDate end, int small, int medium, int large) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getID() {
        return id;
    }

    public LocalDate getStartDate() {
        return start;
    }

    public LocalDate getEndDate() {
        return end;
    }

    public int getSmall() {
        return small;
    }

    public int getMedium() {
        return medium;
    }

    public int getLarge() {
        return large;
    }
    
}