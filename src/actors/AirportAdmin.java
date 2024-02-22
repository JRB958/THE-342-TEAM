package actors;
import airports.*;

public class AirportAdmin extends Actor{
    private Airport airport;
    private String email;
    private String password;

    public AirportAdmin(Airport airport, String email, String password) {

        super(Auth.AIRPORT_ADMIN, true, true, true);
        this.airport = airport;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }
}
