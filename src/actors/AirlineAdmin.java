package actors;

public class AirlineAdmin extends Actor{

    private String email;
    private String password;
    // private Airline airline;


    public AirlineAdmin(Auth auth, Boolean registered, Boolean read, Boolean write, String email, String password) {
        super(auth, registered, read, write);
        this.email = email;
        this.password = password;
    }

    public AirlineAdmin(String email, String password) {
        super(Auth.AIRLINE_ADMIN, true, true, true);
        this.email = email;
        this.password = password;
    }

    public AirlineAdmin() {
        super(Auth.AIRLINE_ADMIN, true, true, true);
        this.email = "";
        this.password = "";
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
}
