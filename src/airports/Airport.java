package airports;
import misc.City;

import java.util.ArrayList;
import java.util.List;
import misc.Aircraft;

public class Airport{
    private int airportID;
    private String airportName;
    private String airportCode;
    private City airportCity;
    public void setAirportID(int airportID) {
        this.airportID = airportID;
    }

    public List<Aircraft> getFleet() {
        return fleet;
    }

    public void setFleet(List<Aircraft> fleet) {
        this.fleet = fleet;
    }
    private List<Aircraft> fleet;

    public Airport(int airportID, String airportName, String airportCode, City airportCity) {
        this.airportID = airportID;
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.airportCity = airportCity;
        fleet = new ArrayList<>();
    }

    public Airport() {
        fleet = new ArrayList<>();
    }
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
    public int getAirportID() {
        return airportID;
    }
    public Boolean aircraftExist(String aircraftName){
        for (Aircraft aircraft : this.fleet){
            if (aircraft.getAircraftName().equalsIgnoreCase(aircraftName)){
                return true;
            }
        }
        return false;
    }
    public void addAircraft(Aircraft aircraft){
        fleet.add(aircraft);
    }
    @Override
    public String toString() {
        return "Airport [airportName=" + airportName + ", airportCode=" + airportCode + ", airportCity=" + airportCity
                + ", fleet=" + fleet + "]";
    }

    
    
}
