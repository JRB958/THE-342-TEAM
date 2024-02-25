package misc;

import flights.*;

public class Aircraft {
    private Boolean inTransit;
    private String aircraftName;

    public Aircraft() {
        this.inTransit = true;
    }

    public Aircraft(Boolean inTransit, String aircraftName) {
        this.inTransit = inTransit;
        this.aircraftName = aircraftName;
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
        return "Aircraft [inTransit=" + inTransit + "]";
    }
}
