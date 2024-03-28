package actors;
import airports.*;

public class AirportAdmin extends RegisteredClient{

    private int airportAdminID;
    private int airportID;
    public int getAirportID() {
        return airportID;
    }

    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    private Airport airport;

    public AirportAdmin(Airport airport, String email, String password) {
        super(Auth.AIRPORT_ADMIN, true, true, true, email, password);
        this.airport = airport;
    }

    public AirportAdmin() {
        
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public int getAirportAdminID() {
        return airportAdminID;
    }

    public void setAirportAdminID(int airportAdminID) {
        this.airportAdminID = airportAdminID;
    }
}
