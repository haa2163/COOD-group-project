package data.parking;

public class ParkingViolation {

    private final String ticketNumber;
    private final String plateId;
    private final String state;
    private final String dateTime;
    private final String violation;
    private final int fine;
    private final String zipCode;

    public ParkingViolation(String ticketNumber, String plateId, String state,
                            String dateTime, String violation, int fine,
                            String zipCode) {

        this.ticketNumber = ticketNumber;
        this.plateId = plateId;
        this.state = state;
        this.dateTime = dateTime;
        this.violation = violation;
        this.fine = fine;
        this.zipCode = zipCode;
    }

    public String getTicketNumber() { return ticketNumber; }
    public String getPlateId() { return plateId; }
    public String getState() { return state; }
    public String getDateTime() { return dateTime; }
    public String getViolation() { return violation; }
    public int getFine() { return fine; }
    public String getZipCode() { return zipCode; }
}
