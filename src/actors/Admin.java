package actors;

public class Admin extends RegisteredClient{
    public Admin() {
        super(actors.Auth.ADMIN, true, true, true, "", "");
    }
}
