package misc;
import java.util.ArrayList;
import java.util.List;
public class Airline {
    private List<Aircraft> fleet;

    private String airlineName;

    // instantiate an airline with at least one aircaft
    public Airline(Aircraft aircraft, String airlineName){
        this.airlineName = airlineName;
        fleet = new ArrayList<>();
        fleet.add(aircraft);
    }

    // instatiate an airline with a non-empty fleet 
    public Airline(List<Aircraft> aircraft, String airlineName){
        this.airlineName = airlineName;
        if (aircraft.size()==0){
            System.out.println("An airline must at least have one aircraft");
        } else{
            fleet = aircraft;
        }
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }


    public String getAirlineName() {
        return this.airlineName;
    }

    // method to add an aircraft to an airline
    public void buyAnAircraft(Aircraft aircraft){
        fleet.add(aircraft);
    }
}
