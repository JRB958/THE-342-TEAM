package misc;

public class Fleet {
    private int fleetID;
    private String fleetName;
    private int airlineID;
    private Airline airline;

    public Fleet() {
    }

    public Fleet(String fleetName, Airline airline) {
        this.fleetName = fleetName;
        this.airline = airline;
        this.airlineID = airline.getAirlineID();
    }

    public int getFleetID() {
        return fleetID;
    }

    public void setFleetID(int fleetID) {
        this.fleetID = fleetID;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    
}
