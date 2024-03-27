package misc;
public class City {
    private int cityID;
    private String name;
    private String country;
    private double temp;

    public City(int cityID, String name, String country, double temp) {
        this.cityID = cityID;
        this.name = name;
        this.country = country;
        this.temp = temp;
    }
    public int getCityID() {
        return cityID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
}
