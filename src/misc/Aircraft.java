package misc;

import flights.*;

public class Aircraft {
    private Boolean inTransit;

    public Aircraft() {
        this.inTransit = true;
    }

    public Aircraft(Boolean inTransit) {
        this.inTransit = inTransit;
    }

    public Boolean getInTransit() {
        return inTransit;
    }

    public void setInTransit(Boolean inTransit) {
        this.inTransit = inTransit;
    }

    @Override
    public String toString() {
        return "Aircraft [inTransit=" + inTransit + "]";
    }
}
