package airports;
import misc.City;
import java.util.List;
import misc.Aircraft;

public class Airport{
    private String airportName;
    private String airportCode;
    private City airportCity;
    private List<Aircraft> availableCrafts;
    
    public String getAirportName() {
        return airportName;
    }
    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    public String getAirportCode() {
        return airportCode;
    }
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    public City getAirportCity() {
        return airportCity;
    }
    public void setAirportCity(City airportCity) {
        this.airportCity = airportCity;
    }
    
}
