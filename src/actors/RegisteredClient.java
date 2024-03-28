package actors;

public class RegisteredClient extends Actor{
    private int clientID;
    private String email;
    private String password;

    public RegisteredClient() {
        super(Auth.REGISTERED, true, true, false);
        this.email = "";
        this.password = "";
    }

    public RegisteredClient(String email, String password) {
        super(Auth.REGISTERED, true, true, false);
        this.email = email;
        this.password = password;
    }

    public RegisteredClient(Auth auth, Boolean registered, Boolean read, Boolean write, String email, String password) {
        super(auth, registered, read, write);
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

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}