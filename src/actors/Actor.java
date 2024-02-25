package actors;

public abstract class Actor {

    public Auth auth;
    private Boolean registered;
    private Boolean read;
    private Boolean write;

    public Actor() {
        this.auth = Auth.UNREGISTERED;
        this.registered = false;
        this.read = true;
        this.write = false;
    }

    public Actor(Auth auth, Boolean registered, Boolean read, Boolean write) {
        this.auth = auth;
        this.registered = registered;
        this.read = read;
        this.write = write;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }
}
