package actors;

public class Admin extends Actor{
    public Admin() {
        super(actors.Auth.ADMIN, true, true, true);
    }
}
