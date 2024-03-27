package actors;

import misc.*;

public class AirlineAdmin extends RegisteredClient{

    private int airlineAdminID;
    private Airline airline;


    public int getAirlineAdminID() {
        return airlineAdminID;
    }

    public void setAirlineAdminID(int airlineAdminID) {
        this.airlineAdminID = airlineAdminID;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public AirlineAdmin(Auth auth, Boolean registered, Boolean read, Boolean write, String email, String password) {
        super(auth, registered, read, write, email, password);
    }

    public AirlineAdmin(Airline airline, String email, String password) {
        super(Auth.AIRLINE_ADMIN, true, true, true, email, password);
        this.airline = airline;
    }

    public AirlineAdmin() {
        super(Auth.AIRLINE_ADMIN, true, true, true, "", "");
    }
}
