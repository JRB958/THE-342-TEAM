package misc;

import airports.Airport;
import flights.*;

public class Aircraft {
    private Boolean inTransit;
    private String aircraftName;
    private Airport hostingAirport;

    public Aircraft() {
        this.inTransit = true;
    }

    public Aircraft(Boolean inTransit, String aircraftName) {
        this.inTransit = inTransit;
        this.aircraftName = aircraftName;
    }

    public void setHostingAirport(Airport airport){
        this.hostingAirport = airport;
    }

    public Boolean getInTransit() {
        return inTransit;
    }

    public void setInTransit(Boolean inTransit) {
        this.inTransit = inTransit;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public String getAircraftName() {
        return this.aircraftName;
    }

    @Override
    public String toString() {
        return "Aircraft [inTransit=" + inTransit + ", aircraftName=" + aircraftName + "]";
    }
}
