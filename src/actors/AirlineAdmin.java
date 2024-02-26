package actors;

import misc.*;

public class AirlineAdmin extends RegisteredClient{

    private Airline airline;


    public AirlineAdmin(Auth auth, Boolean registered, Boolean read, Boolean write, String email, String password) {
        super(auth, registered, read, write, email, password);
    }

    public AirlineAdmin(String email, String password) {
        super(Auth.AIRLINE_ADMIN, true, true, true, email, password);
    }

    public AirlineAdmin() {
        super(Auth.AIRLINE_ADMIN, true, true, true, "", "");
    }
}
