package actors;

public class PublicClient extends Actor{

    public PublicClient() {
        super();
    }

    PublicClient(Auth auth, Boolean registered, Boolean read, Boolean write) {
        super(auth, registered, read, write);
    }
}