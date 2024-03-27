package misc;

import airports.Airport;

public class Aircraft {
    private int aircraftID;
    private Boolean inTransit;
    private String aircraftName;
    private int airlineID;
    private Airline airline;
    
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

    private int hostingAirportID;
    private Airport hostingAirport;

    public Aircraft() {
        this.inTransit = true;
    }

    public Aircraft(Boolean inTransit, String aircraftName) {
        this.inTransit = inTransit;
        this.aircraftName = aircraftName;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(int aircraftID) {
        this.aircraftID = aircraftID;
    }

    public Boolean getInTransit() {
        return inTransit;
    }

    public void setInTransit(Boolean inTransit) {
        this.inTransit = inTransit;
    }

    public String getAircraftName() {
        return this.aircraftName;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public int getHostingAirportID() {
        return hostingAirportID;
    }

    public void setHostingAirportID(int hostingAirportID) {
        this.hostingAirportID = hostingAirportID;
    }

    public Airport getHostingAirport() {
        return hostingAirport;
    }

    public void setHostingAirport(Airport airport){
        this.hostingAirport = airport;
    }

    @Override
    public String toString() {
        return "Aircraft [inTransit=" + inTransit + ", aircraftName=" + aircraftName + "]";
    }
}